package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		TemperatureSensor sn = new TemperatureSensor();
		Client client = new Client("TemperatureDevice", Common.BROKERHOST, Common.BROKERPORT);
		client.connect();
		for (int i = 0; i < COUNT; i++) {
			int temp = sn.read();
			client.publish(Common.TEMPTOPIC, Integer.toString(temp));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		client.disconnect();

		System.out.println("Temperature device stopping ... ");

	}

}
