import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

public class LinieFolgen extends State {
	
	DifferentialPilot pilot;
	
	public LinieFolgen() {
		pilot = new DifferentialPilot(56, 110, Motor.B,	Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(150); // Rotationsgeschwindigkeit
	}

	public void step() throws InterruptedException {
		

		// Variablendeklaration
		int winkel = 9; // Drehwinkel
		int dir = 1; // Drehrichtung
		int anz = 10;
		int wert;

		wert = Client.lightSensor.getLightValue(); // lies Farbwert ein
		LCD.drawInt(wert, 0, 1); // gibt Wert auf Display aus
		
		if (!Client.isBlack()) // Wenn wert größer als
			// schwarz, dann...
		{

			pilot.stop(); // Halte Roboter an
			
			// wenn alle 3 Lichtsensoren grau erkennen...
			if (Client.isGray()) {
	
				Client.statemachine++;
				System.out.println(Client.statemachine);
				// wenn das 5. Graufeld erkannt wurde parke ein
				if (Client.statemachine > 4) {
					Client.state = new Einparken();
					Sound.beepSequenceUp();
					return;
	
					// wenn weniger als 5 Felder erkannt wurden wechsle in den
					// Status Stationsanfahrt
					// dort wird entschiden, ob die Station angefahren wird oder
					// nicht
				} else {
					Client.state = new Stationsanfahrt();
					//Sound.beepSequence();
					return;
				}
			}
			
			pilot.rotate(-10, true);
			if(waitForBlack()) {
				pilot.stop();
				pilot.forward();
				return;
			}
			pilot.rotate(100, true);
			if(waitForBlack()) {
				pilot.stop();
				pilot.forward();
				return;
			}
			pilot.rotate(-180, true);
			if(waitForBlack()) {
				pilot.stop();
				pilot.forward();
				return;
			} else {
				pilot.rotate(90);
			}
			
		} else {
			pilot.forward(); // ...ansonsten fahre vorwärts
			dir = 1; // Setze Richtung auf ausgangswert
			anz = 1; // Setze Anzahl auf Ausgangswert
		}
	}
	
	private boolean waitForBlack() {
		while(pilot.isMoving()) {
			if (Button.ESCAPE.isPressed() || Client.isBlack() || Client.isGray()) // Solange der Sensor kein schwarz erkennt
			{
				pilot.stop();
				return true;
			}
		}
		return false;
	}
}
