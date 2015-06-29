package de.ees.group1.nxt;

import java.io.*;

public class Telegramm implements Serializable {

	private int destination;
	private int source;
	private int type;
	private Object data;
	
	public Telegramm(int dest, int source, int type, Object data){
		
		this.destination = dest;
		this.source = source;
		this.type = type;
		this.data = data;
		
	}
	
}
