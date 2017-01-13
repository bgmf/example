package eu.dzim.example.model;

import eu.dzim.example.util.Utils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ApplicationModel {
	
	private static ApplicationModel instance;
	
	private final IntegerProperty textSize = new SimpleIntegerProperty(this, "textSize", Utils.TEXT_SIZE_DEFAULT);
	
	private ApplicationModel() {
		// sonar
	}
	
	public static ApplicationModel getInstance() {
		if (instance == null)
			instance = new ApplicationModel();
		return instance;
	}
	
	public final IntegerProperty textSizeProperty() {
		return this.textSize;
	}
	
	public final int getTextSize() {
		return this.textSizeProperty().get();
	}
	
	public final void setTextSize(final int textSize) {
		this.textSizeProperty().set(textSize);
	}
}
