/**
 * 
 */
package de.ees.group1.model;

/**
 * This class represents a requested step in production.
 * It has several attributes i.e. the type of work or the
 * necessary quality level
 * @author alex
 *
 */
public class ProductionStep {
	
	/**
	 * The type of the step to be preformed
	 */
	private WorkStation.Type _type;
	
	/**
	 * The minimum quality level of the tool performing
	 * this production step
	 */
	private int _minQualityLevel;
	
	/**
	 * The time this step will consume
	 */
	private int _workTimeSeconds;

	public WorkStation.Type getType() {
		return _type;
	}

	public void setType(WorkStation.Type _type) {
		this._type = _type;
	}

	public int getMinQualityLevel() {
		return _minQualityLevel;
	}

	public void setMinQualityLevel(int _minQualityLevel) {
		this._minQualityLevel = _minQualityLevel;
	}

	public int getWorkTimeSeconds() {
		return _workTimeSeconds;
	}

	public void setWorkTimeSeconds(int _workTimeSeconds) {
		this._workTimeSeconds = _workTimeSeconds;
	}
}
