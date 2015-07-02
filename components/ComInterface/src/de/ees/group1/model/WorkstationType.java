package de.ees.group1.model;

public enum WorkstationType {
		NONE,
		DRILL,
		LATHE;
		
		@Override
		public String toString() {
			switch(this) {
			case NONE:
				return "Bitte wählen...";
			case DRILL:
				return "Bohren";
			case LATHE:
				return "Fräsen";
			}
			return "";
		}
}
