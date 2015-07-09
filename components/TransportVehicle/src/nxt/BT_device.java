package nxt;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import model.Ack_Telegram;
import model.ProductionStep;
import model.State_Telegram;
import model.Step_Telegram;
import model.Telegramm;

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
		byte[] data = new byte[4];
		for(int i = 0; i<4; ++i){
			int shift = i << 3;
			data[3-i] = (byte)((length & (0xff << shift))>>shift);
		}
		
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
		
		byte[] message; 
		int length_1 = 0;
		int length_2 = 0;
		int length = 0;
		
		try{
			this.dis = this.connection.openInputStream();
			length_1 = this.dis.read()*16*16;
			length_1 = this.dis.read();
			message = new byte[length_1];
			length = this.dis.read(message);
			length_2 = this.dis.read()*16*16;
			length_2 = this.dis.read();
			this.dis.close();
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
