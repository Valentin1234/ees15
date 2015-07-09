import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;


public class Stationsanfahrt extends State{

	public void step(){
		
		DifferentialPilot pilot = new DifferentialPilot(56, 110, Motor.B, Motor.C); // Pilot zur gleichzeitigen Steuerung von zwei Motoren
		pilot.setTravelSpeed(250); // Geschwindigkeit
		pilot.setRotateSpeed(250); // Rotationsgeschwindigkeit
		
		
		//wenn die Leitstation sagt weiterfahren
		//if(){}
		pilot.travel(150); Sound.twoBeeps(); 
		
		//wenn die Leitstation sagt in die Station einfahren
		//else{
		//pilot.rotate(-93);
		//pilot.travel(150);
	
			//warte die von der Leitstation vorgeschreibene Zeit
	
		//pilot.travel(-150);
		//pilot.rotate(93);
		//}
		
		Client.state = new LinieFolgen();
	}

}
