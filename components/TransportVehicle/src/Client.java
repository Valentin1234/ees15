import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import model.ProductionOrder;


public class Client {
	
	static ProductionOrder order;
	static Kalibrierungswerte werte = new Kalibrierungswerte();
	static LightSensor lightSensor = new LightSensor(SensorPort.S1);
	static LightSensor lightSensor2 = new LightSensor(SensorPort.S2);
	static LightSensor lightSensor3 = new LightSensor(SensorPort.S3);
	static int statemachine = 0;
	public static State state = new Kalibrierend();
	static int OrderStep = 0;
	static int NXT_Status = 0;
	
	
	public Client(){
		
	}
	
	/*private int getThreshold() {
		Lichtwert_lesen calib = new Lichtwert_lesen();
		int value = calib.getAvgLightValue();
		LCD.drawInt(value, 4, 0, 3);
		return value;
	}*/
	
	public void setState(State s){
		this.state = s;
	}
		
	
	public static void main(String[] args) throws InterruptedException {
		//LocalBT localbt = new LocalBT();
		
		//state = new Kalibrierend();
		
		while (!Button.ESCAPE.isPressed()){
		state.step();	
		}
	}
}
