package de.ees.group1.cont;
/*
 * Controller for ControlStation, which handles order tracking
 */

import de.ees.group1.model.*;


public class ControlStation {
	private ProductionOrder currentOrder;
	
	
	/*
	 * übergibt den gerade an den NXT übermittleten Auftrag an die ControlStation
	 */
	public void getCurrentOrder(ProductionOrder order){
		currentOrder=order;
	}

}
