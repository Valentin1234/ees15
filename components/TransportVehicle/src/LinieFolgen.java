import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;

public class LinieFolgen extends State {

	public void step() throws InterruptedException {
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit

		// Variablendeklaration
		int winkel = 9; // Drehwinkel
		int dir = 1; // Drehrichtung
		int anz = 1;
		int wert;


		//while (!Button.ESCAPE.isPressed())// Solange ESCAPE nicht gedr�ckt...
		//{
			wert = Client.lightSensor.getLightValue(); // lies Farbwert ein
			LCD.drawInt(wert, 0, 1); // gibt Wert auf Display aus

			//wenn alle 3 Lichtsensoren grau erkennen...
			if(Client.werte.getGrey2()+3 > Client.lightSensor2.getLightValue() && (Client.werte.getGrey2()-3 < Client.lightSensor2.getLightValue())
					&& Client.werte.getGrey2()+3 > Client.lightSensor3.getLightValue() && (Client.werte.getGrey2()-3 < Client.lightSensor3.getLightValue())
					&& Client.werte.getGrey()+3 > Client.lightSensor.getLightValue() && (Client.werte.getGrey()-3 < Client.lightSensor.getLightValue()))
			{

				Client.statemachine ++; 
				
				// wenn das 5. Graufeld erkannt wurde parke ein
				if(Client.statemachine > 4){ 
					Client.state = new Einparken();
					Client.main(null);
				  
				//wenn weniger als 5 Felder erkannt wurden wechsle in den Status Stationsanfahrt
				//dort wird entschiden, ob die Station angefahren wird oder nicht	
				}else { 
					Client.state = new Stationsanfahrt();
					Client.main(null);
					} 
			  }
			 

			if (wert > (Client.werte.getBlack() + 4)) // Wenn wert gr��er als
														// schwarz, dann...
			{
				pilot.stop(); // Halte Roboter an
				// pilot.quickStop();
				while (!Button.ESCAPE.isPressed() && Client.lightSensor.getLightValue() > (Client.werte.getBlack() + 4) /* && (winkel*dir*anz)<160 */) // Solange der Sensor kein schwarz erkennt
																					
				{ 
					//wenn vorderen beiden sensoren grau erkennen abbrechen
					if(Client.werte.getGrey2()+3 > Client.lightSensor2.getLightValue() && (Client.werte.getGrey2()-3 < Client.lightSensor2.getLightValue())
							&& Client.werte.getGrey2()+3 > Client.lightSensor3.getLightValue() && (Client.werte.getGrey2()-3 < Client.lightSensor3.getLightValue())	)
					{
						break;
					}
					LCD.drawString("while", 3, 4);
					pilot.rotate(winkel * dir * anz);
					LCD.drawInt((winkel * dir * anz), 1, 2);
					dir = dir * (-1); // ver�ndere die Richtung
					 //if(anz > 2){
					 //anz = anz+10;
					 //}else
					anz = anz + 2; // Verdopple Rotationsradius
				}
				Client.state = new LinieFolgen();

			} else {
				pilot.forward(); // ...ansonsten fahre vorw�rts
				dir = 1; // Setze Richtung auf ausgangswert
				anz = 1; // Setze Anzahl auf Ausgangswert
				Client.state = new LinieFolgen();
			}
		//}
	}
}
