package eu.dzim.example.util;

import javafx.application.Platform;

public class PlatformHelper {
	
	private PlatformHelper() {}
	
	public static void run(Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("The argument cannot be null!");
		}
		if (Platform.isFxApplicationThread()) {
			runnable.run();
		} else {
			Platform.runLater(runnable);
		}
	}
}
