import nxt.BT_device;
// ist kein echtes singleton, da das Objekt extern erzeugt wird aber anders geht das nicht
public class BT_device_singleton {

	public static BT_device device;
	
	private BT_device_singleton(){}
	
	public static BT_device getInstance(){
	/*if(device.equals(null)){
		device = new BT_device();
		return device;
	}*/
		return device;
	}

}
