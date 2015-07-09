/**
 * 
 */
package com;

import model.ProductionOrder;

/**
 * @author alex
 *
 */
public interface IComProvider {
	
	void register(IControlStation cs);
	
	void register(IWorkStation ws);
	
	void transmitProductionOrder(ProductionOrder order);
	
	void transmitYes();
	
	void transmitNo();
	
	void transmitFinishedStep();
	
	/*
	 * TODO: more still to be specified
	 */
	
}
