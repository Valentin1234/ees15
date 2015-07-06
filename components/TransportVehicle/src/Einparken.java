import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class Einparken extends State{

	public void step(){
		
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit
		
		 pilot.rotate(300);
		  pilot.travel(300);
		  
		  Client.state = new Kalibrierend();
	}

}
