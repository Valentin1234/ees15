package de.ees.group1.cs.gui;

import de.ees.group1.model.ProductionOrder;

public interface IGUIListener {
	
	public void orderCreatedAction(ProductionOrder order);
	
	public void orderRemovedAction(int orderID);
	
	public int getNextOrderId();

}