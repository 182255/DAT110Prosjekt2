package no.hvl.dat110.messages;

import java.util.HashSet;
import java.util.Set;

public class UnsubscribeMsg extends Message {

	// TODO:
	// Implement objectvariables, constructor, get/set-methods, and toString method

	private Set<String> users;
	private String topic;

	public UnsubscribeMsg(String user, String topic) {
		super(MessageType.UNSUBSCRIBE, user);
		users = new HashSet<String>();
		users.remove(user);
		this.topic = topic;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "UnsubscribeMsg [users=" + users + ", topic=" + topic + "]";
	}

}
