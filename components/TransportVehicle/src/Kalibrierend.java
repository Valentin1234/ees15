import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import model.Ack_Telegram;
import model.ProductionStep;
import model.State_Telegram;
import model.Step_Telegram;
import nxt.BT_device;


public class Kalibrierend extends State {

	

	public void step() throws InterruptedException {
		
		waitForUser("white");
		Client.werte.setWhite(Client.lightSensor.getLightValue());
		LCD.drawInt(Client.werte.getWhite(), 4, 0, 3);
		waitForUser(null);
 
		waitForUser("black");
		Client.werte.setBlack(Client.lightSensor.getLightValue());
		LCD.drawInt(Client.werte.getBlack(), 4, 0, 3);
		waitForUser(null);
		
		waitForUser("grey");
		Client.werte.setGrey(Client.lightSensor.getLightValue());
		LCD.drawInt(Client.werte.getGrey(), 4, 0, 3);
		waitForUser(null);
		
		waitForUser("grey2");
		Client.werte.setGrey2(Client.lightSensor2.getLightValue());
		LCD.drawInt(Client.werte.getGrey2(), 4, 0, 3);
		waitForUser(null);
		
		 BT_device localDev = new BT_device(); //Kallibrierung fertig, starte BT Kommunikation
		  if(localDev.connect()){
			   try{
			    //Auf Nachricht warten, blockiert
			    localDev.receiveMessage();
			    //Acknowledgement an Leitstation �bertragen
			    localDev.sendMessage(new Ack_Telegram(0, 16, true));
			    //Daten des aktuellen Produktionsschrittes �bertragen
			    localDev.sendMessage(new Step_Telegram(1, 16, new ProductionStep()));
			    localDev.receiveMessage();
			    //aktuellen Zustand �bertragen
			    localDev.sendMessage(new State_Telegram(0, 16, 1));
			    //Acknowledgement an Bearbeitungsstation1 �bertragen
			    localDev.sendMessage(new Ack_Telegram(1, 16, true));
			    localDev.receiveMessage();
			    //Erfolgreiche Beendigung des Arbeitsauftrages �bertragen
			    localDev.sendMessage(new State_Telegram(0, 16, 255));
			   }catch(ClassNotFoundException e){
			    
			   }
			  } 
		
		Client.state = new LinieFolgen();
		
		
		
	}
	
	private synchronized void waitForUser(String message)
			throws InterruptedException {
		if (message != null) {
			LCD.drawString(message, 0, 2, false);
		}
		Sound.twoBeeps();
		Button.ESCAPE.waitForPressAndRelease();
	}
	
	
}
