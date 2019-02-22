package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
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

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		clients.put(user, new ClientSession(user, connection));
//		throw new RuntimeException("not yet implemented");

	}

	public void removeClientSession(String user) {

		// TODO: remove client session for user from the storage
		clients.remove(user);
//		throw new RuntimeException("not yet implemented");

	}

	public void createTopic(String topic) {

		// TODO: create topic in the storage

		Set<String> topicSet = new HashSet<String>();
		topicSet.add(topic);
		subscriptions.put(topic, topicSet);
	}

	public void deleteTopic(String topic) {

		// TODO: delete topic from the storage
		if(subscriptions.containsValue(topic)) {
			subscriptions.entrySet().remove(topic);
		}
		
			
			
		

	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the topic

		if(subscriptions.contains(topic)) {
			Set<String> newUser = subscriptions.get(topic);
			subscriptions.put(user, newUser);
		}

	}

	public void removeSubscriber(String user, String topic) {

		// TODO: remove the user as subscriber to the topic
		

	}
}
