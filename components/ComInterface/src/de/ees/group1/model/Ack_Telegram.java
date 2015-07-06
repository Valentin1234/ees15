package de.ees.group1.model;

public class Ack_Telegram extends Telegramm {

	private boolean data;	
	
	public Ack_Telegram(int destination, int source, boolean data){
		super(destination, source, 0);
		
		this.data = data;
		
	}
	
	public boolean getData(){
		
		return this.data;
		
	}
	
	public String transform(){
		
		String message = null;
		
		message = ""+this.getDestination()+""+this.getSource()+""+this.getType();
		
		if(this.data){
			
			message = message + "1";
			
		}else{
			
			message = message + "0";
			
		}
		
		return message;
		
	}
	
}
