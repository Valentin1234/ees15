/**
 * 
 */
package de.ees.group1.com;

import de.ees.group1.model.ProductionOrder;

/**
 * @author alex
 *
 */
public interface IControlStationComProvider {
	
	void Register(IControlStation cs);
	
	void TransmitProductionOder(ProductionOrder order);
	
	/*
	 * TODO: more still to be specified
	 */
	
}
