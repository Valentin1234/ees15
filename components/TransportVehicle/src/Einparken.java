import java.io.IOException;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import model.Ack_Telegram;
import model.ProductionStep;
import model.State_Telegram;
import model.Step_Telegram;


public class Einparken extends State{

	public void step(){
		
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit
		
		 pilot.rotate(300);
		  pilot.travel(300);
		  Client.statemachine = 0;
		  Client.NXT_Status = 0;
		  
		  //Bluetoot anfrage
		//BT_device_singleton localDev = new BT_device_singleton(); //Kallibrierung fertig, starte BT Kommunikation
		  if(BT_device_singleton.getInstance().connect()){
			   try{
			    //Auf Nachricht warten, blockiert
				Client.order = BT_device_singleton.getInstance().receiveMessage().getDataOrder();
			    //Acknowledgement an Leitstation übertragen
				BT_device_singleton.getInstance().sendMessage(new Ack_Telegram(0, 16, true));
			    //Daten des aktuellen Produktionsschrittes übertragen
				BT_device_singleton.getInstance().sendMessage(new Step_Telegram(1, 16, new ProductionStep()));
				BT_device_singleton.getInstance().receiveMessage();
			    //aktuellen Zustand übertragen
				BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, 1));
			    //Acknowledgement an Bearbeitungsstation1 übertragen
				BT_device_singleton.getInstance().sendMessage(new Ack_Telegram(1, 16, true));
				BT_device_singleton.getInstance().receiveMessage();
			    //Erfolgreiche Beendigung des Arbeitsauftrages übertragen
				BT_device_singleton.getInstance().sendMessage(new State_Telegram(0, 16, 255));
			   }catch(IOException e){
			    
			   }
		  }
		  
		  
		  Client.state = new LinieFolgen();
	}

}
