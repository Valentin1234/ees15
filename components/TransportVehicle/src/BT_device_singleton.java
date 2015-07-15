import nxt.BT_device;

public class BT_device_singleton {

	private static BT_device device;
	
	private BT_device_singleton(){}
	
	public static BT_device getInstance(){
		if(device.equals(null)){
			device = new BT_device();
			return device;
		}return device;
	}

}
