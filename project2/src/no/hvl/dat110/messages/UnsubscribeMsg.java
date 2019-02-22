package no.hvl.dat110.messages;

public class UnsubscribeMsg extends Message {

	// TODO:
	// Implement objectvariables, constructor, get/set-methods, and toString method

	private String user;
	private String topic;

	public UnsubscribeMsg(String user, String topic) {
		super(MessageType.UNSUBSCRIBE, user);
		this.user = user;
		this.topic = topic;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "UnsubscribeMsg [user=" + user + ", topic=" + topic + "]";
	}

}
