package de.ees.group1.cs.controller;

import de.ees.group1.com.IControlStation;
import de.ees.group1.cs.gui.IGUIListener;
import de.ees.group1.model.ProductionOrder;

public class ControlStation implements IGUIListener, IControlStation {

	@Override
	public void orderCreatedAction(ProductionOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderRemovedAction(int orderID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reachedParkingPositionInd(int orderID, int nextWorkingStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNextOrderId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
