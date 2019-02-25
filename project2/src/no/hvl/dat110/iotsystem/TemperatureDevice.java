package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class TemperatureDevice {
	
	private static final int COUNT = 10;
	
	public static void main(String[] args) {
		
		TemperatureSensor sn = new TemperatureSensor();
		Client client = new Client("TemperatureDevice", Common.BROKERHOST, Common.BROKERPORT); 
		client.connect();
		// TODO - start
		
		for(int i = 0; i < COUNT; i++) {
			int temp = sn.read();
			client.publish(Common.TEMPTOPIC, Integer.toString(temp));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		client.disconnect();
		// TODO - end
		
		System.out.println("Temperature device stopping ... ");
		
//		throw new RuntimeException("not yet implemented");
		
	}
}
