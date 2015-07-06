import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

import de.ees.group1.model.*;

public class Client {

	static Kalibrierungswerte werte = new Kalibrierungswerte();
	static LightSensor lightSensor = new LightSensor(SensorPort.S1);
	static LightSensor lightSensor2 = new LightSensor(SensorPort.S2);
	static LightSensor lightSensor3 = new LightSensor(SensorPort.S3);
	static int statemachine = 0;
	public static State state = new Kalibrierend();
	
	//public BT_device localDev; //Objekt der BT-Schnittstelle
	
	public Client(){
		
	}
	
	/*private int getThreshold() {
		Lichtwert_lesen calib = new Lichtwert_lesen();
		int value = calib.getAvgLightValue();
		LCD.drawInt(value, 4, 0, 3);
		return value;
	}*/
	
	public void setState(State s){
		this.state = s;
	}
		
	
	public static void main(String[] args) throws InterruptedException {
		//LocalBT localbt = new LocalBT();
		
		//state = new Kalibrierend();
		
		BT_device localDev = new BT_device();
		
		if(localDev.connect()){
			try{
				localDev.receiveMessage();
				localDev.sendMessage(localDev.createMessage(0, 16, true));
				localDev.sendMessage(localDev.createMessage(1, 16, new ProductionStep()));
				localDev.receiveMessage();
				localDev.sendMessage(localDev.createMessage(0, 16, 1));
				localDev.sendMessage(localDev.createMessage(1, 16, true));
				localDev.receiveMessage();
				localDev.sendMessage(localDev.createMessage(0, 16, 255));
			}catch(ClassNotFoundException e){
				
			}
		}
		
		while (!Button.ESCAPE.isPressed()){
		state.step();	
		}
	}
}
