package de.ees.group1.model;

public class WorkStation {
	
	public enum Type {
		DRILL,
		LATHE,
	}
	/*
	 * Gibt den Status der Arbeitsstation zurück. -1...default, 0...bereit, 1...in Betrieb, 2...defekt 
	 */
	public int status;
	public boolean work(ProductionStep step) {
		int time=step.getWorkTimeSeconds();
		time=time*1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (status==1){
			return true;
		}
		else{
			return false;
		}
		
	}
	

}
