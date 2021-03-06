package no.hvl.dat110.messages;

import java.util.HashSet;
import java.util.Set;

public class CreateTopicMsg extends Message {
	private Set<String> users;
	private String topic;

	public CreateTopicMsg(String user, String topic) {
		super(MessageType.CREATETOPIC, user);
		users = new HashSet<String>();
		users.add(user);
//		this.user = user;
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
		return "CreateTopicMsg [users=" + users + ", topic=" + topic + "]";
	}
	
	

}
