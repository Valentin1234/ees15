

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

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
			this.dis = this.connection.openInputStream();
			this.dos = this.connection.openOutputStream();
			
			return true;
			
		}else{
			
			LCD.drawString("Fehler: ", 0, 0);
			LCD.drawString("Es existiert bereits eine Verbindung", 0, 1);
			
			return false;
			
		}
		
	}
	
	public void close(){
		
		try{
			
			this.dos.close();
			this.dis.close();
			this.connection.close();
			
		}catch(IOException e){
			
			System.out.println(e);
			
		}
		
	}
	
	public boolean sendMessage(Telegramm message){
	
		int length = 0;
		
		String transformed = message.transform();
		length = transformed.length();
		byte[] data = message.concat(ByteBuffer.allocate(2).putInt(length).array(), transformed.getBytes());
		data = message.concat(data, ByteBuffer.allocate(2).putInt(length).array());
		
		try{
			
			dos.write(data);
			return true;
			
		}catch(IOException e){
			
			return false;	
			
		}
		
	}
	
	public Telegramm receiveMessage() throws ClassNotFoundException{
		
		byte[] message; 
		int length_1 = 0;
		int length_2 = 0;
		int length = 0;
		
		try{
			length_1 = dis.read()*16*16;
			length_1 = dis.read();
			message = new byte[length_1];
			length = dis.read(message);
			length_2 = dis.read()*16*16;
			length_2 = dis.read();
			LCD.drawInt(length_1, 0, 0);
			LCD.drawInt(length_2, 0, 1);
			LCD.drawInt(length, 0, 2);
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
		
		return new State_Telegram(destination, source);
				
	}
		
}
