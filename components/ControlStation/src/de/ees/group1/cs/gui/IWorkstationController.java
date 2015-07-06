package de.ees.group1.cs.gui;

import de.ees.group1.model.WorkstationType;

public interface IWorkstationController {
	
	public void workstationTypeUpdatedAction(int id, WorkstationType type);
	
	public void workstationQualityUpdatedAction(int id, int quality);
}
