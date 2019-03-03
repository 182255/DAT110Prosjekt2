package no.hvl.dat110.broker.processing.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.PublishMsg;

public class Test8PublishWhenDisconnected extends Test0Base {

	public static String TESTTOPIC = "testtopic";

	@Test
	public void test() {
		broker.setMaxAccept(3);

		Client client = new Client("client", BROKER_TESTHOST, BROKER_TESTPORT);

		Client meClient = new Client("meClient", BROKER_TESTHOST, BROKER_TESTPORT);

		client.connect();

		client.createTopic(TESTTOPIC);
		
		//allow broker timer to create the topic

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		meClient.connect();

		client.subscribe(TESTTOPIC);

		meClient.subscribe(TESTTOPIC);

		//allow broker to process subscriptions
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		meClient.disconnect();

		//publish message to the topic then disconnect
		client.publish(TESTTOPIC, "message from client on topic");
		client.disconnect();

		//connects another client and see the published message when they were offline
		meClient.connect();
		PublishMsg msg = (PublishMsg) meClient.receive();
		meClient.disconnect();
		
		assertEquals("message from client on topic", msg.getMessage());
	}
}
