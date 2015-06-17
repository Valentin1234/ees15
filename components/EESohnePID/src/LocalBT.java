
	/* Bluetooth Modul für den NXT
	 * 
	 */
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;

	import lejos.nxt.comm.BTConnection;
	import lejos.nxt.comm.Bluetooth;

	public class LocalBT {

		private BTConnection btc = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		
		/**
		 * 
		 * Wartet, bis ein Gerät versucht eine Verbindung mit dem NXT herzustellen
		 * 
		 */
		public LocalBT(){
			
			this.btc = Bluetooth.waitForConnection();
			
		}
		
		/**
		 * 
		 * Aktiviert die Kommunikation zwischen NXT und Sender
		 * 
		 */
		public void startStreams(){
			
			this.dis = this.btc.openDataInputStream();
			this.dos = this.btc.openDataOutputStream();
			
		}
		
		/**
		 * 
		 * Beendet die Kommunikation zwischen NXT und Sender
		 * 
		 */
		public void closeStreams(){
			
			try {
				this.dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 
		 * Fragt die erste Nachricht des Empfangsbuffers ab
		 * 
		 * @return Erste Nachricht des Empfangsbuffers
		 */
		/*public String checkForMessage(){

			byte data[];
			
			byte help_data;
			try {
				help_data = this.dis.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    data[1] = help_data;
			return new String(data, "UTF-8");
			
		}*/
		
		/**
		 * 
		 * Übergibt eine Nachricht an den Sendebuffer
		 * 
		 * @param message Die zu sendende Nachricht
		 */
		public void sendMessage(String message){
			
			byte data[] = message.getBytes("iso-8859-1");
			try {
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}



