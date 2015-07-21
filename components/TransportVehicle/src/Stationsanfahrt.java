import java.io.IOException;

import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import model.Ack_Telegram;
import model.State_Telegram;
import model.Step_Telegram;

public class Stationsanfahrt extends State {

	public void step() {

		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B,
				Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit
		
		
		if(Client.order.size() > Client.OrderStep){
			
		BT_device_singleton.getInstance().sendMessage(
				new Step_Telegram(Client.statemachine, 16, Client.order
						.getStep(Client.OrderStep)));
		
		boolean drivethru = false;
		try {
			drivethru = BT_device_singleton.getInstance().receiveMessage()
					.getDataBool();
		} catch (IOException e) {
			Sound.buzz();
		}

		if (drivethru) {

			Sound.beepSequenceUp();
			
			// wenn die Bearbeitungsstation sagt in die Station einfahren
			// teile Leitstation Einfahrt mit
			BT_device_singleton.getInstance().sendMessage(
					new State_Telegram(0, 16, Client.statemachine));
			// fahre ein
			pilot.rotate(-93);
			pilot.travel(150);
			// teile Leitstation Beginn der Arbeit mit
			try {
			BT_device_singleton.getInstance().sendMessage(
					new State_Telegram(0, 16, Client.statemachine + 8));
			} catch(Exception e) {
				Sound.buzz();
			}

			// warte die von der Leitstation vorgeschreibene Zeit
			BT_device_singleton.getInstance().sendMessage(
					new Ack_Telegram(Client.statemachine, 16, true));
			try {
				BT_device_singleton.getInstance().receiveMessage()
						.getDataBool();
			} catch (IOException e) {
				e.printStackTrace();
				Sound.buzz();
			}
			// teile Leitstation mit, dass Arbeit erfolgreich beendet wurde
			BT_device_singleton.getInstance().sendMessage(
					new State_Telegram(0, 16, Client.statemachine + 12));
			// fahre aus der bearbeitungsstation aus
			Client.anzahlEingefahrenerStationen++;
			pilot.travel(-150);
			pilot.rotate(93);
			pilot.travel(120);
			Client.OrderStep++;
			Client.state = new LinieFolgen();
		}
		} Sound.beepSequence();
		// wenn die Bearbeitungsstation sagt weiterfahren
		// teile Leitstation die Vorbeifahrt mit
		BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, Client.statemachine + 4));
		pilot.travel(120);
		Sound.twoBeeps();
		Client.state = new LinieFolgen();
		
		
	}

}
