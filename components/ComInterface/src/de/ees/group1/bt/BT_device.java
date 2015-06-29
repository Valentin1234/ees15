package de.ees.group1.bt;

import de.ees.group1.model.*;

import java.io.*;
import java.math.*;

import javax.bluetooth.*;
import javax.microedition.io.*;
import javax.obex.*;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

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
	 * 
	 * Definiert die zu Suchende MAC-Adresse
	 * @param mac MAC-Adresse
	 * 
	 */
	public void setSearchMAC(String mac){
		
		this.mac = mac; 
		
	}
	
	public boolean startService(){
		
		this.dos = nxtComm.getOutputStream();
		this.dis = nxtComm.getInputStream();
		
		return true;
		
	}
	
	public boolean sendMessage(Telegramm<? extends Serializable> message){
	
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
	
	public void close() throws IOException{
		
		this.dis.close();
		this.dos.close();
		
	}
		
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
