package de.ees.group1.model;

public class Step_Telegram extends Telegramm {
	
	private ProductionStep data;
	
	public Step_Telegram(int destination, int source, ProductionStep data){
		super(destination, source, 2);
		
		this.data = data;
		
	}
	
	public ProductionStep getData(){
		
		return data;
		
	}
	
	@Override
	public String transform(){
		
		String message = null;
		
		message = "" + this.getDestination() + "" +this.getSource() + "" + this.getType();
		
		message = message + "" + this.data.getType() + "" + this.data.getWorkTimeSeconds() + "" + this.data.getMinQualityLevel();
		
		return message;
		
	}

}