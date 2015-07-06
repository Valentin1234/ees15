import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;


public class Stationsanfahrt extends State{

	public void step(){
		
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit
		
		pilot.travel(150); Sound.twoBeeps(); 
		
		Client.state = new LinieFolgen();
	}

}
