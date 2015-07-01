/**
 * 
 */
package de.ees.group1.model;

import java.io.Serializable;

/**
 * This class represents a requested step in production.
 * It has several attributes i.e. the type of work or the
 * necessary quality level
 * @author alex
 *
 */
public class ProductionStep implements Serializable {
	
	/**
	 * version id for serialization
	 */
	private static final long serialVersionUID = 5310500383208577174L;

	/**
	 * The type of the step to be performed
	 */
	private WorkStationType _type;
	
	/**
	 * The minimum quality level of the tool performing
	 * this production step
	 */
	private int _minQualityLevel;
	
	/**
	 * The time this step will consume
	 */
	private int _workTimeSeconds;
	
	public ProductionStep() {
		_type = WorkStationType.NONE;
		_minQualityLevel = -1;
		_workTimeSeconds = -1;
	}
	
	public ProductionStep(WorkStationType type, int minQualLevel, int workTimeSeconds) {
		setType(type);
		setMinQualityLevel(minQualLevel);
		setWorkTimeSeconds(workTimeSeconds);
	}

	public ProductionStep(ProductionStep step) {
		_type = step.getType();
		_minQualityLevel = step.getMinQualityLevel();
		_workTimeSeconds = step.getWorkTimeSeconds();
	}

	public WorkStationType getType() {
		return _type;
	}

	public void setType(WorkStationType _type) {
		this._type = _type;
	}

	public int getMinQualityLevel() {
		return _minQualityLevel;
	}

	public void setMinQualityLevel(int _minQualityLevel) {
		if(_minQualityLevel < 1 || _minQualityLevel > 4)
			throw new IllegalArgumentException("Quality level has to be in range from 1 to 4");
		this._minQualityLevel = _minQualityLevel;
	}

	public int getWorkTimeSeconds() {
		return _workTimeSeconds;
	}

	public void setWorkTimeSeconds(int _workTimeSeconds) {
		if(_workTimeSeconds < 0)
			throw new IllegalArgumentException("Worktime has to be greater than zero");
		this._workTimeSeconds = _workTimeSeconds;
	}
}
