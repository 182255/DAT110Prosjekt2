package no.hvl.dat110.messages;

public class DeleteTopicMsg extends Message {
	private String user;
	private String topic;

	// TODO:
	// Implement objectvariables, constructor, get/set-methods, and toString method
	public DeleteTopicMsg(String user, String topic) {
		super(MessageType.DELETETOPIC, user);
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
		return "DeleteTopicMsg [user=" + user + ", topic=" + topic + "]";
	}

}
