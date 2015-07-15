package de.ees.group1.cont;
/*
 * Controller for ControlStation, which handles order tracking
 */

import de.ees.group1.model.*;


public class ControlStation {
	private ProductionOrder currentOrder;
	private ProductionStep currentStep;
	private int currentStepNumber;
	
	/*
	 * �bergibt den gerade an den NXT �bermittleten Auftrag an die ControlStation
	 */
	public void setCurrentOrder(ProductionOrder order){
		currentOrder=order;
		currentStepNumber=0;
	}
	/*
	 * Schreibt den n�chsten Step auf currentStep und erh�ht die currentStepNumber um 1
	 */
	public void setCurrentStep(){
		currentStep =currentOrder.getStep(currentStepNumber);
		currentStepNumber++;
	}
	
	public ProductionStep getCurrentStep(){
		return currentStep;
	}
	
	
	
	

}
