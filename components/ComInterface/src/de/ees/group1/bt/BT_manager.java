package de.ees.group1.bt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
	
	public boolean sendData(String data){
		
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

		String part;
		String order_telegramm = "NXTLT";
		int size = 0;
		
		size = order.size();
		part = "" + order.getId();
		order_telegramm = order_telegramm + part;
		
		for(int i = 0; i <= size; i++){
			
			part = "" + i;
			part = part + order.get(i).getType();
			part = part + order.get(i).getWorkTimeSeconds();
			part = part + order.get(i).getMinQualityLevel();
			order_telegramm = order_telegramm + part;
			
		}
		
		order_telegramm = order_telegramm + size;
		
		if(this.sendData(order_telegramm)){
			
			System.out.println("Datenübertragung erfolgreich");
			
		} else{
			
			System.out.println("Datenübertragung fehlgeschlagen");
						
		}
		
	}

}
