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
import model.WorkstationType;

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
	
	public Telegramm receiveMessage() throws IOException{
		
		String message;
		byte[] data; 
		int type;
		int length_1 = 0;
		int length_2 = 0;
		int length = 0;
		
		this.dis = this.connection.openInputStream();
		length_1 = this.dis.read()*16*16;
		length_1 = length_1 + this.dis.read();
		data = new byte[length_1];
		length = this.dis.read(data);
		length_2 = this.dis.read()*16*16;
		length_2 = length_2 + this.dis.read();
		
		if(length_1 != length_2){
			
			System.out.println("Telegramm fehlerhaft");
			return null;
			
		}
		
		this.dis.close();
		
		type = data[2];
		
		switch(type){
		case 0:
			if(data[3]== 0){
				return new Ack_Telegram(data[0], data[1], false);
			}else{
				return new Ack_Telegram(data[0], data[1], true);
			}
		case 1:
			System.out.println("Fehlerhaftes Telegramm");
			return null;
		case 2:
			ProductionStep prodStep = new ProductionStep();
			prodStep.setMinQualityLevel(data[5]);
			prodStep.setWorkTimeSeconds(data[4]);
			switch(data[3]){
			case 0:	prodStep.setType(WorkstationType.NONE);
			case 1: prodStep.setType(WorkstationType.LATHE);
			case 2: prodStep.setType(WorkstationType.DRILL);
			default: prodStep.setType(WorkstationType.NONE);
			}
			return new Step_Telegram(data[0], data[1], prodStep);
		case 3:
			return new State_Telegram(data[0], data[1], data[2]);
		default:
		}
		
		message = new String(data);
		System.out.println("Telegram empfangen: Unbekannter Typ" + message);
		
		return null;
		
	}
				
}
