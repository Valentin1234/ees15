
public class Lichtwert_lesen {

		 
		private static final int NUMBER_OF_SAMPLES = 20;
	 
		public Lichtwert_lesen() {
		}
	 
		public static int getAvgLightValue() {
	 
			int sum = 0;
			for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
				sum += Hauptklasse.lightSensor.getLightValue();
			}
			return sum / NUMBER_OF_SAMPLES;
		
	 
	}

}
