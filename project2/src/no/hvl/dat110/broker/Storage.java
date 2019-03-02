package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	protected ConcurrentHashMap<String, ClientSession> clients;
	protected ConcurrentHashMap<String, Set<String>> offline;
	protected ConcurrentHashMap<String, Message> buffer;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		offline = new ConcurrentHashMap<>();
		buffer = new ConcurrentHashMap<>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public ConcurrentHashMap<String, Set<String>> getOffline() {
		return offline;
	}

	public void setOffline(ConcurrentHashMap<String, Set<String>> offline) {
		this.offline = offline;
	}

	public ConcurrentHashMap<String, Message> getBuffer() {
		return buffer;
	}

	public void setBuffer(ConcurrentHashMap<String, Message> buffer) {
		this.buffer = buffer;
	}

	public void addClientSession(String user, Connection connection) {

		clients.put(user, new ClientSession(user, connection));

	}

	public void removeClientSession(String user) {

		clients.remove(user);
	}

	/**
	 * adds an offline client to a hashmap that is containing all the offline users
	 * as the key and a set of messages being published while offline
	 * 
	 * @param user
	 */
	public void addToOfflineList(String user) {
		offline.put(user, new HashSet<>());

	}

	/**
	 * adds a message to a topic for the offline client
	 * 
	 * @param user
	 * @param message
	 */
	public void addMessageToBuffer(String user, Message message) {
		String uID = UUID.randomUUID().toString();
		offline.get(user).add(uID);
		buffer.put(uID, message);
		
	}

	public void createTopic(String topic) {

		Set<String> s = new HashSet<String>();
		s.add(topic);

		subscriptions.put(topic, s);

	}

	public void deleteTopic(String topic) {
		subscriptions.remove(topic);

	}

	public void addSubscriber(String user, String topic) {

		subscriptions.get(topic).add(user);

	}

	public void removeSubscriber(String user, String topic) {

		subscriptions.get(topic).remove(user);
	}
}
