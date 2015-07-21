package nxt;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import model.Ack_Telegram;
import model.Order_Telegram;
import model.ProductionOrder;
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
		
		type = data[3]-48;
		
		switch(type){
		case 0:
			if((data[4]-48)== 0){
				return new Ack_Telegram(16, data[2]-48, false);
			}else{
				return new Ack_Telegram(16, data[2]-48, true);
			}
		case 1:
			ProductionOrder order = new ProductionOrder((data[4]-48)*10+data[5]-48);
			int size = data[6]-48;
			for(int i = 0; i<size; i++){
				order.add(this.restoreStep(data[i*5+8], data[i*5+9], data[i*5+10]));
			}
			return new Order_Telegram(16,data[2]-48,order);
		case 2:
			return new Step_Telegram(16, data[2]-48, restoreStep(data[4], data[5], data[6]));
		case 3:
			return new State_Telegram(16, data[2]-48, data[3]-48);
		default:
		}
		
		message = new String(data);
		System.out.println("Telegram empfangen: Unbekannter Typ" + message);
		//*/
		return null;
		
	}
	
	public ProductionStep restoreStep(byte type, byte time, byte qual){
		
		ProductionStep prodStep = new ProductionStep();
		prodStep.setMinQualityLevel(qual-48);
		prodStep.setWorkTimeSeconds((time-47));
		int typ = type-48;
		switch(typ){
		case 0:	{
			prodStep.setType(WorkstationType.NONE);
			break;
		}
		case 1: {
			prodStep.setType(WorkstationType.LATHE);
			break;
		}
		case 2: {
			prodStep.setType(WorkstationType.DRILL);
			break;
		}
		default: {
			prodStep.setType(WorkstationType.NONE);
			break;
		}
		}
		return prodStep;
		
	}
				
}
