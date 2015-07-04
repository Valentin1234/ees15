package de.ees.group1.model;

import java.io.*;

public class Telegramm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int destination;
	private int source;
	private int type;
	
	public Telegramm(int dest, int source, int type){
		
		this.destination = dest;
		this.source = source;
		this.type = type;
		
	}
	
	public int getDestination(){
		
		return this.destination;
		
	}
	
	public int getSource(){
		
		return this.source;
		
	}
	
	public int getType(){
		
		return this.type;
		
	}
	
}
