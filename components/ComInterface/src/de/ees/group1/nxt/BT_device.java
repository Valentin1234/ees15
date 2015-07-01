package de.ees.group1.nxt;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import de.ees.group1.model.ProductionOrder;
import de.ees.group1.model.Telegramm;

public class BT_device {

	private NXTConnection connection = null;
	private OutputStream dos = null;
	private InputStream dis = null;
	private ObjectInput in = null;
	private ObjectOutput out = null;
	
	public BT_device(){
		
	}
	
	public boolean connect(){
		
		if(this.connection == null){
			
			LCD.drawString("Verbinde...", 0, 0);
			this.connection = Bluetooth.waitForConnection();
			this.dis = this.connection.openInputStream();
			this.dos = this.connection.openOutputStream();
			
		}else{
			
			LCD.drawString("Fehler: ", 0, 0);
			LCD.drawString("Es existiert bereits eine Verbindung", 0, 1);
			
		}
		try{
			
			this.in = new ObjectInputStream(this.dis);
			this.out = new ObjectOutputStream(this.dos);
			return true;
			
		}catch(IOException e){
			
			System.out.println(e.getMessage());
			LCD.drawString("Fehler: ", 0, 0);
			LCD.drawString("Stream konnte nicht geöffnet werden", 0, 1);
			return false;
			
		}
		
	}
	
	public void close(){
		
		try{
			
			this.dos.close();
			this.dis.close();
			this.out.close();
			this.in.close();
			this.connection.close();
			
		}catch(IOException e){
			
			System.out.println(e);
			
		}
		
	}
	
	public boolean sendMessage(Telegramm message){
		
		try {
			
			this.out.writeObject(message);
			this.dos.flush();
			return true;
			
		}catch(IOException e){
			
			e.printStackTrace();
			
		}
		
		return false;
		
	}
	
	public Serializable receiveMessage() throws ClassNotFoundException{
		
		Object obj;
		
		try{
			
			obj = this.in.readObject();
			Telegramm t = (Telegramm)obj;
			if(t.getData() instanceof ProductionOrder) {
				return (ProductionOrder) t.getData();
			}
			
		}catch(IOException e){
			
			System.out.println(e.getMessage());
			
		}
		
		return null;
		
	}
		
	public Telegramm createMessage(int destination, int source, Serializable data){
		
		return new Telegramm(destination, source, data);
				
	}
	
}
