package de.ees.group1.model;

import java.util.List;

/**
 * This class represents a production order
 * which is transmitted from the control station
 * to the transport vehicle
 * @author alex
 *
 */
public class ProductionOrder {
	
	/**
	 * order id (1-99)
	 */
	private int _id;
	
	/**
	 * ordered list of steps included in this order
	 */
	private List<ProductionStep> _steps;
	/*
	 * TODO: implement data structure for production orders
	 */

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		if(id < 1 || id > 99)
			throw new IllegalArgumentException("The order ID must be a number between 0 and 100");
		this._id = id;
	}

	public List<ProductionStep> getSteps() {
		return _steps;
	}

	public void setSteps(List<ProductionStep> _steps) {
		this._steps = _steps;
	}

}
