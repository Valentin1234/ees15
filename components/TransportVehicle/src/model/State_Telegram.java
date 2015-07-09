package model;

import lejos.nxt.LCD;

public class State_Telegram extends Telegramm{

	private int data;
	
	public State_Telegram(int destination, int source, int data){
		super(destination, source, 3);
		
		LCD.drawInt(42, 0, 8);
		this.data = data;
		
	}
	
	public int getData(){
		
		return data;
		
	}
	
	@Override
	public String transform(){
		
		String message = null;
		
		message = ""+this.getDestination()+""+this.getSource()+""+this.getType();
		
		message = message+""+this.data; 
		
		return message;
		
	}

}
