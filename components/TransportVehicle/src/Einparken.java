import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import model.Ack_Telegram;
import model.Finished_Telegram;

public class Einparken extends State {

	public void step() {


		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B,
				Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit

		// wenn nirgendwo eingefahren ist
		//Client.statemachine = 1;
		if ((Client.anzahlEingefahrenerStationen > 0) && (Client.order.size() > Client.OrderStep)) {
			
			pilot.travel(300);
			Client.statemachine = 0;
			pilot.rotate(75);
			Client.anzahlEingefahrenerStationen = 0;
			Client.state = new LinieFolgen();
			
			
		} else {
			
			// wenn nirgendwo eingefahren, parke ein
			pilot.travel(100);
			pilot.rotate(279);
			pilot.travel(150);
			pilot.rotate(70);
			Client.statemachine = 0;
			Client.NXT_Status = 0;
			Client.OrderStep = 0;
			Client.anzahlEingefahrenerStationen = 0;

			// Teile Leitstation mit, dass die Runde beendet ist
			// bei erfogreicher Fahrt 21
			if(Client.anzahlEingefahrenerStationen > 0){
				BT_device_singleton.getInstance().sendMessage(new Finished_Telegram(0, 16, 21));
			}
			// wenn etwas nicht erfolgreich bearbeitet werden konnte 22
			else{
			BT_device_singleton.getInstance().sendMessage(new Finished_Telegram(0, 16, 22));
			}
			// Bluetoot anfrage
			// BT_device_singleton localDev = new BT_device_singleton();
			// //Kallibrierung fertig, starte BT Kommunikation
			//if (BT_device_singleton.getInstance().connect()) {
				try {
					// Auf Nachricht warten, blockiert
					Client.order = BT_device_singleton.getInstance()
							.receiveMessage().getDataOrder();
					// Acknowledgement an Leitstation übertragen
					BT_device_singleton.getInstance().sendMessage(
							new Ack_Telegram(0, 16, true));
					// Daten des aktuellen Produktionsschrittes übertragen
					/*
					 * BT_device_singleton.getInstance().sendMessage(new
					 * Step_Telegram(1, 16, new ProductionStep()));
					 * BT_device_singleton.getInstance().receiveMessage();
					 * //aktuellen Zustand übertragen
					 * BT_device_singleton.getInstance().sendMessage(new
					 * State_Telegram(0, 16, 1)); //Acknowledgement an
					 * Bearbeitungsstation1 übertragen
					 * BT_device_singleton.getInstance().sendMessage(new
					 * Ack_Telegram(1, 16, true));
					 * BT_device_singleton.getInstance().receiveMessage();
					 * //Erfolgreiche Beendigung des Arbeitsauftrages übertragen
					 * BT_device_singleton.getInstance().sendMessage(new
					 * State_Telegram(0, 16, 255));
					 */
				} catch (IOException e) {
					LCD.drawString(e.getMessage(), 0, 0);
				//}Client.state = new LinieFolgen();
			}Client.state = new LinieFolgen();
		}
	}

}

