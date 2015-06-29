package de.ees.group1.nxt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import de.ees.group1.model.ProductionOrder;
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
			return true;
			
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
		
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(this.dos);
			out.writeObject(message);
			this.dos.flush();
			return true;
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try{
				if( out != null ){
					out.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		return false;
		
	}
	
	public Telegramm receiveMessage(){
		
		Object obj;
		ObjectInput in = null;
		
		try{
			
			in = new ObjectInputStream(this.dis);
			obj = in.readObject();
			Telegramm t = (Telegramm)obj;
			if(t.getData() instanceof ProductionOrder) {
				return (ProductionOrder) t.getData();
			}
			
		}catch(IOException e){
			
			
		}
		
//		if(obj instanceof Telegramm<ProductionStep> )
//			listener.productionStepInd();
		return null;
		
	}
		
	public byte[] createMessage(int destination, int source, ProductionOrder data){
		
		Telegramm<ProductionOrder> message = new Telegramm<ProductionOrder>(destination, source, data);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(message);
			return bos.toByteArray();
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try{
				if( out != null ){
					out.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			try{
				bos.close();
			}catch(IOException e) {
				System.out.println(e.getMessage());	
			}
		
		}
		return null;
		
	}
	
}
