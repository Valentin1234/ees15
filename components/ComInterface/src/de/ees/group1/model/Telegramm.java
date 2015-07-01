package de.ees.group1.model;

import java.io.*;

public class Telegramm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int destination;
	private int source;
	private Serializable data;
	
	public Telegramm(int dest, int source, Serializable data){
		
		this.destination = dest;
		this.source = source;
		this.data = data;
		
	}
	
	public int getDestination(){
		
		return this.destination;
		
	}
	
	public int getSource(){
		
		return this.source;
		
	}
	
	public Serializable getData(){
		
		return this.data;
		
	}
	
}
