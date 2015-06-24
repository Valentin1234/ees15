package de.ees.group1.bt;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import de.ees.group1.com.IComProvider;
import de.ees.group1.com.IControlStation;
import de.ees.group1.com.IWorkStation;
import de.ees.group1.model.ProductionOrder;

public class BT_manager implements IComProvider{
	
	private BT_device localDev = null;
	private RemoteDevice remoteDev = null;
	
	public BT_manager(){
		
		this.localDev = new BT_device();
		
	}
	
	public void connectWithDevice(String mac){
		
		try{
			
			//NXT 0016530565FD
			localDev.setSearchMAC(mac);
			
			if(localDev.searchRemoteDevice()){
				
				System.out.println("Fahrzeug gefunden");
				
				if(localDev.startService()){
				
					System.out.println(" Verbindung hergestellt");
				
				}
				
			}else{
				
				System.out.println("Fahrzeug nicht gefunden");
				
			}
		
		}catch(IOException BluetoothStateException){
			
			System.out.println(BluetoothStateException.getMessage());
			
		};
		
	}
	
	public boolean sendData(byte[] data){
		
		return this.localDev.sendMessage(data);
		
	}
	
	public void disconnect(){
		
		try{
			
			this.localDev.close();
			System.out.println("Verbindung beendet");
			
		}catch(IOException e){
			
			System.out.println(e.getMessage());
			
		}
		
	}
	
	public void register(IControlStation cs){
		
		
		
	}
	
	public void register(IWorkStation ws){
		
		
		
	}
	
	public void transmitProductionOrder(ProductionOrder order){

		Telegramm tele = new Telegramm(16,0,1,order);
		
		if(this.sendData(this.localDev.toByte(tele))){
			
			System.out.println("Datenübertragung erfolgreich");
			
		} else{
			
			System.out.println("Datenübertragung fehlgeschlagen");
						
		}
		
	}

}
