package model;

public class State_Telegram extends Telegramm{

	private int data;
	
	public State_Telegram(int destination, int source, int data){
		super(destination, source, 3);
		
		this.data = data;
		
	}
	
	@Override
	public int getDataInt(){
		
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
