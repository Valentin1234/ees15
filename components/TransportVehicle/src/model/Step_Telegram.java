package model;

public class Step_Telegram extends Telegramm {
	
	private ProductionStep data;
	
	public Step_Telegram(int destination, int source, ProductionStep data){
		super(destination, source, 2);
		
		this.data = data;
		
	}
	
	@Override
	public ProductionStep getDataStep(){
		
		return data;
		
	}
	
	@Override
	public String transform(){
		
		String message = null;
		
		message = "" + this.getDestination() + "" +this.getSource() + "" + this.getType();
		
		switch(this.data.getType()){
		case NONE: {
			message = message + 0;
			break;
		}
		case LATHE: {
			message = message + 1;
			break;
		}
		case DRILL: {
			message = message + 2;
			break;
		}
		default: message = message + 7; 
		}
		
		message = message + "" + (this.data.getWorkTimeSeconds()-1) + "" + this.data.getMinQualityLevel();
		
		return message;
		
	}

}