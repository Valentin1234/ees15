import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;


public class Hauptklasse {
	
	
	static Kalibrierungswerte werte = new Kalibrierungswerte();
	static LightSensor lightSensor = new LightSensor(SensorPort.S1);
	static LightSensor lightSensor2 = new LightSensor(SensorPort.S2);
	int statemachine = 0;
	
	public Hauptklasse(){
		
	}
	
	public void kalibrierung() throws InterruptedException {
		
		waitForUser("white");
		werte.setWhite(lightSensor.getLightValue());
		LCD.drawInt(werte.getWhite(), 4, 0, 3);
		waitForUser(null);
 
		waitForUser("black");
		werte.setBlack(lightSensor.getLightValue());
		LCD.drawInt(werte.getBlack(), 4, 0, 3);
		waitForUser(null);
		
		waitForUser("grey");
		werte.setGrey(lightSensor.getLightValue());
		LCD.drawInt(werte.getGrey(), 4, 0, 3);
		waitForUser(null);
		
		waitForUser("grey2");
		werte.setGrey2(lightSensor2.getLightValue());
		LCD.drawInt(werte.getGrey2(), 4, 0, 3);
		waitForUser(null);
		
		//LocalBT localbt = new LocalBT();
	}
	
	private synchronized void waitForUser(String message)
			throws InterruptedException {
		if (message != null) {
			LCD.drawString(message, 0, 2, false);
		}
		Sound.twoBeeps();
		Button.ESCAPE.waitForPressAndRelease();
	}
 
	/*private int getThreshold() {
		Lichtwert_lesen calib = new Lichtwert_lesen();
		int value = calib.getAvgLightValue();
		LCD.drawInt(value, 4, 0, 3);
		return value;
	}*/
	
	
	public void liniefolgen(){
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); 	//Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250);														//Geschwindigkeit
		pilot.setRotateSpeed(250);														//Rotationsgeschwindigkeit
		
		//Variablendeklaration
		int winkel = 9;			//Drehwinkel
		int dir = 1;			//Drehrichtung
		int anz = 1;
		int wert;
		
		//Programmstart
		
		while(!Button.ESCAPE.isPressed()) 	//Solange ESCAPE nicht gedrückt...
		{
			wert = lightSensor.getLightValue(); 	//lies Farbwert ein
			LCD.drawInt(wert, 0, 1);		//gibt Wert auf Display aus		
			
		/*	if(werte.getGrey2()+2 > lightSensor2.getLightValue() && (werte.getGrey2()-2 < lightSensor2.getLightValue())){
				statemachine ++;
				if(statemachine > 5){
					pilot.rotate(300);
					pilot.travel(300);
					pilot.stop();
					try {
						pilot.wait(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					pilot.travel(100);
					Sound.twoBeeps();
				}
			}*/
			
			if(wert > (werte.getGrey()+4))				//Wenn wert größer als schwarz, dann...
			{
				pilot.stop();	//Halte Roboter an
				//pilot.quickStop();
				while(!Button.ESCAPE.isPressed() && lightSensor.getLightValue() > (werte.getGrey()+4) /*&& (winkel*dir*anz)<160*/ /*&& !(werte.getGrey()+2 > lightSensor.getLightValue() && (werte.getGrey()-2 < lightSensor.getLightValue()*/) //Solange der Sensor kein schwarz erkennt...
				{																							//und der Rotatationswinkel < 160
					LCD.drawString("while", 3, 4);
					pilot.rotate(winkel*dir*anz);	
					LCD.drawInt((winkel*dir*anz), 1, 2);
					dir = dir*(-1);					//verändere die Richtung
				//	if(anz > 1){
				//		anz = anz+10;
				//	}else
					anz = anz+2;					//Verdopple Rotationsradius
				}
				
				
			}
			else
			{
				pilot.forward();			//...ansonsten fahre vorwärts
				dir =1;						//Setze Richtung auf ausgangswert
				anz =1;						//Setze Anzahl auf Ausgangswert
			}
		}

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LocalBT localbt = new LocalBT();
		Hauptklasse go = new Hauptklasse();
		try {
			go.kalibrierung();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		go.liniefolgen();
		}
	}


