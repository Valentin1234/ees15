package model;

import java.util.LinkedList;

/**
 * This class represents a production order
 * which is transmitted from the control station
 * to the transport vehicle
 * @author alex
 *
 */
public class ProductionOrder extends LinkedList<ProductionStep> {
	
	/**
	 * version uid for serialization
	 */
	private static final long serialVersionUID = -8252618943092239548L;

	/**
	 * order id (1-99)
	 */
	private int _id;

	public ProductionOrder(int id){
		_id = id;
	}

	public ProductionOrder(ProductionOrder order) {
		_id = order.getId();
		for(ProductionStep step : order) {
			this.add(new ProductionStep(step));
		}
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		if(id < 1 || id > 99)
			throw new IllegalArgumentException("The order ID must be a number between 0 and 100");
		this._id = id;
	}
	
	public ProductionStep getStep(int stepNumber){
		return this.get(stepNumber);
	}
}
