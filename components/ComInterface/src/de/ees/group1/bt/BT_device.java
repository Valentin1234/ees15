package de.ees.group1.bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import lejos.nxt.LCD;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import de.ees.group1.model.Telegramm;

/**
 * 
 * @author sven
 *
 * BT_device ist das lokale Bluetooth-Gerät. Es funktioniert als Master.
 * 
 */
public class BT_device /*implements DiscoveryListener*/ {

	private NXTInfo nxtInfo = null;
	private NXTComm nxtComm = null;
	private InputStream dis = null;
	private OutputStream dos = null;
	private String mac = "00:16:53:05:65:FD";
	
	/**
	 * 
	 * Konstruktor der Klasse BT_device.
	 * 
	 */
		
	public BT_device(){
		
		try {
			this.nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Stellt eine Verbindung zu dem Fahrzeug her.
	 * 
	 * @return true, wenn Verbindung hergestellt wurde, andernfalls false
	 * @throws IOException
	 */
	public boolean searchRemoteDevice() throws IOException{
		
		this.nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", this.mac);
		
		try {
			
			this.nxtComm.open(this.nxtInfo);
			return true;
			
		} catch (NXTCommException e) {
			System.out.println(e.getMessage());
			return false;
			
		}
		
	}
	
	/**
	 * Definiert die zu Suchende MAC-Adresse
	 * @param mac MAC-Adresse
	 * 
	 */
	public void setSearchMAC(String mac){
		
		this.mac = mac; 
		
	}
	
	/**
	 * Stellt Streams über die Verbindung her.
	 * @return 
	 */
	public boolean startService(){
		
		this.dos = nxtComm.getOutputStream();
		this.dis = nxtComm.getInputStream();
		
		return true;
		
	}
	
	public Telegramm receiveMessage(){
		
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
			System.out.println(length_1+"/n");
			System.out.println(length_2+"/n");
			System.out.println(length+"/n");
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
	
	/**
	 * Überträgt ein Telegramm an den NXT.
	 * @param message Zu übertragende Nachricht.
	 * @return true, wenn Übertragung erfolgreich.
	 */
	public boolean sendMessage(Telegramm message){

		double quot = 0;
		int bytes = 0;
		int length = 0;
		
		String transformed = message.transform();
		length = transformed.length();
		byte[] data = message.concat(ByteBuffer.allocate(4).putInt(length).array(), transformed.getBytes());
		data = message.concat(data, ByteBuffer.allocate(4).putInt(length).array());
		
		try{
			
			dos.write(data);
			return true;
			
		}catch(IOException e){
			
			return false;	
			
		}
			
	}
	
	/**
	 * Beendet die Verbindung mit dem Fahrzeug
	 * @throws IOException
	 */
	public void close() throws IOException{
		
		this.dis.close();
		this.dos.close();
		
	}
	
	/**
	 * Wandelt ein Byte-Array in einen Hex-String um
	 * @param data Byte-Array
	 * @return Hex-String
	 */
	public String byteToHex(byte[] data){
		
		StringBuffer sb = new StringBuffer();
		
		for(int y = 0; y < data.length; y++){
			
			if(y > 0) sb.append(':');
			sb.append(Integer.toString((data[y] & 0xff) + 0x100, 16).substring(1));
			
		}
		
		return sb.toString();
		
	}
	
//	public byte[] toByte(Telegramm tele){
//		
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		ObjectOutput out = null;
//		
//		try {
//			out = new ObjectOutputStream(bos);
//			out.writeObject(tele);
//			return bos.toByteArray();
//		}catch(IOException e){
//			e.printStackTrace();
//		}finally {
//			try{
//				if( out != null ){
//					out.close();
//				}
//			} catch (IOException e) {
//				System.out.println(e.getMessage());
//			}
//			try{
//				bos.close();
//			}catch(IOException e) {
//				System.out.println(e.getMessage());	
//			}
//		
//		}
//		return null;
//		
//	}
	
}
