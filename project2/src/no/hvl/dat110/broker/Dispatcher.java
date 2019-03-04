package no.hvl.dat110.broker;

import java.util.Collection;

import no.hvl.dat110.common.Logger;
import no.hvl.dat110.common.Stopable;
import no.hvl.dat110.messages.ConnectMsg;
import no.hvl.dat110.messages.CreateTopicMsg;
import no.hvl.dat110.messages.DeleteTopicMsg;
import no.hvl.dat110.messages.DisconnectMsg;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.MessageType;
import no.hvl.dat110.messages.MessageUtils;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.messages.SubscribeMsg;
import no.hvl.dat110.messages.UnsubscribeMsg;
import no.hvl.dat110.messagetransport.Connection;

public class Dispatcher extends Stopable {

	private Storage storage;

	public Dispatcher(Storage storage) {
		super("Dispatcher");
		this.storage = storage;

	}

	@Override
	public void doProcess() {

		Collection<ClientSession> clients = storage.getSessions();

		Logger.lg(".");
		for (ClientSession client : clients) {

			Message msg = null;

			if (client.hasData()) {
				msg = client.receive();
			}

			if (msg != null) {
				dispatch(client, msg);
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void dispatch(ClientSession client, Message msg) {

		MessageType type = msg.getType();

		switch (type) {

		case DISCONNECT:
			onDisconnect((DisconnectMsg) msg);
			break;

		case CREATETOPIC:
			onCreateTopic((CreateTopicMsg) msg);
			break;

		case DELETETOPIC:
			onDeleteTopic((DeleteTopicMsg) msg);
			break;

		case SUBSCRIBE:
			onSubscribe((SubscribeMsg) msg);
			break;

		case UNSUBSCRIBE:
			onUnsubscribe((UnsubscribeMsg) msg);
			break;

		case PUBLISH:
			onPublish((PublishMsg) msg);
			break;

		default:
			Logger.log("broker dispatch - unhandled message type");
			break;

		}
	}

	// called from Broker after having established the underlying connection
	public void onConnect(ConnectMsg msg, Connection connection) {
		String user = msg.getUser();

		Logger.log("onConnect:" + msg.toString());

		storage.addClientSession(user, connection);
		if (storage.getOffline().containsKey(user)) {
			for (String id : storage.getOffline().get(user)) {
				MessageUtils.send(connection, storage.buffer.get(id));
				Logger.log("sending msg to" + user);
				storage.buffer.remove(id);
			}
		}

	}

	// called by dispatch upon receiving a disconnect message
	public void onDisconnect(DisconnectMsg msg) {

		String user = msg.getUser();

		Logger.log("onDisconnect:" + msg.toString());

		storage.removeClientSession(user);
		storage.addToOfflineList(user);

	}

	public void onCreateTopic(CreateTopicMsg msg) {

		String topic = msg.getTopic();
		Logger.log("onCreateTopic:" + msg.toString());
		storage.createTopic(topic);

	}

	public void onDeleteTopic(DeleteTopicMsg msg) {

		String topic = msg.getTopic();
		Logger.log("onDeleteTopic:" + msg.toString());
		storage.deleteTopic(topic);

	}

	public void onSubscribe(SubscribeMsg msg) {

		String user = msg.getUser();
		String topic = msg.getTopic();
		Logger.log("onSubscribe:" + msg.toString());
		storage.addSubscriber(user, topic);

	}

	public void onUnsubscribe(UnsubscribeMsg msg) {

		String user = msg.getUser();
		String topic = msg.getTopic();
		Logger.log("onUnsubscribe:" + msg.toString());
		storage.removeSubscriber(user, topic);

	}

	public void onPublish(PublishMsg msg) {

		Logger.log("onPublish:" + msg.toString());

		String user = msg.getUser();
		String topic = msg.getTopic();
		Collection<ClientSession> clients = storage.getSessions();

		for (ClientSession c : clients) {
			if (storage.subscriptions.get(topic).contains(user)) {
				c.send(msg);
			}

		}

		for (String subbedU : storage.getSubscribers(topic)) {
			if (storage.getOffline().containsKey(subbedU)) {
				storage.addMessageToBuffer(subbedU, msg);
			}

		}

	}
}
