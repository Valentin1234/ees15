package de.ees.group1.model;

public class WorkStation {
	
	public enum Type {
		DRILL,
		LATHE,
	}
	/*
	 * Gibt den Status der Arbeitsstation zurück. -1...default, 0...bereit, 1...in Betrieb, 2...defekt 
	 */
	public int status;
	

}
