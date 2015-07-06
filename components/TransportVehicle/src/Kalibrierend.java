import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;


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
