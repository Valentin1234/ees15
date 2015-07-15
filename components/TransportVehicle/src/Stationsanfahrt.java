import java.io.IOException;

import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import model.Ack_Telegram;
import model.State_Telegram;
import model.Step_Telegram;


public class Stationsanfahrt extends State{
	
	

	public void step(){
		
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit
		
		
		
		BT_device_singleton.getInstance().sendMessage(new Step_Telegram(Client.statemachine,16, Client.order.getStep(Client.OrderStep)));
		
		
		boolean drivethru = false;
		try{
			drivethru = BT_device_singleton.getInstance().receiveMessage().getDataBool();
		}catch(IOException e){}
		
		if(drivethru){
			
		//wenn die Leitstation sagt in die Station einfahren
		//teile Leitstation Einfahrt mit
		BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, Client.statemachine));
		pilot.rotate(-93);
		pilot.travel(150);
		//teile Leitstation Beginn der Arbeit mit
		BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, Client.statemachine+8));
		
			//warte die von der Leitstation vorgeschreibene Zeit
			BT_device_singleton.getInstance().sendMessage(new Ack_Telegram(Client.statemachine, 16, true));
			try {
				BT_device_singleton.getInstance().receiveMessage().getDataBool();
			} catch (IOException e) {
				e.printStackTrace();
			}
		//teile Leitstation mit, dass Arbeit erfolgreich beendet wurde
		BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, Client.statemachine+12));
		pilot.travel(-150);
		pilot.rotate(93);
		Client.OrderStep ++;
		Client.state = new LinieFolgen();
		
			
		}else
			
		//wenn die Leitstation sagt weiterfahren
		//teile Leitstation die Vorbeifahrt mit
		BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, Client.statemachine+4));
		pilot.travel(150); Sound.twoBeeps(); 
		
			
		Client.state = new LinieFolgen();
	}

}
