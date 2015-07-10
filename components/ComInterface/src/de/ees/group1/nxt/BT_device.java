package de.ees.group1.nxt;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import de.ees.group1.model.Ack_Telegram;
import de.ees.group1.model.ProductionStep;
import de.ees.group1.model.State_Telegram;
import de.ees.group1.model.Step_Telegram;
import de.ees.group1.model.Telegramm;

public class BT_device {

	private NXTConnection connection = null;
	private OutputStream dos = null;
	private InputStream dis = null;
	
	public BT_device(){
		
	}
	
	public boolean connect(){
		
		if(this.connection == null){
			
			LCD.drawString("Verbinde...", 0, 0);
			this.connection = Bluetooth.waitForConnection();
			return true;
			
		}else{
			
			LCD.drawString("Fehler: ", 0, 0);
			LCD.drawString("Es existiert bereits eine Verbindung", 0, 1);
			
			return false;
			
		}
		
	}
	
	public void close(){
		
		this.connection.close();

	}
	
	public boolean sendMessage(Telegramm message){
	
		int length = 0;
		
		String transformed = message.transform();
		length = transformed.length();
		
		byte[] length_data = new byte[2];
		for(int i = 0; i<2; ++i){
			int shift = i << 3;
			length_data[1-i] = (byte)((length & (0xff << shift))>>shift);
		}
		
		byte[] data = message.concat(length_data, message.concat(transformed.getBytes(), length_data));
		
		try{
			
			this.dos = this.connection.openOutputStream();
			this.dos.write(data);
			this.dos.flush();
			this.dos.close();
			return true;
			
		}catch(IOException e){
			
			return false;	
			
		}
		
	}
	
	public Telegramm receiveMessage() throws ClassNotFoundException{
		
		String message;
		byte[] data; 
		int length_1 = 0;
		int length_2 = 0;
		int length = 0;
		
		try{
			this.dis = this.connection.openInputStream();
			length_1 = this.dis.read()*16*16;
			length_1 = this.dis.read();
			data = new byte[length_1];
			length = this.dis.read(data);
			length_2 = this.dis.read()*16*16;
			length_2 = this.dis.read();
			this.dis.close();
			
			message = new String(data);
			
		}catch(IOException e){}
		
		return null;
		
	}
		
	public Ack_Telegram createMessage(int destination, int source, boolean data){
		
		return new Ack_Telegram(destination, source, data);
				
	}
	
	public Step_Telegram createMessage(int destination, int source, ProductionStep data){
		
		return new Step_Telegram(destination, source, data);
				
	}
	
	public State_Telegram createMessage(int destination, int source, int data){
		
		return new State_Telegram(destination, source, data);
				
	}
		
}
