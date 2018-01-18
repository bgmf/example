package eu.dzim.example;

import java.util.logging.Logger;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.down.plugins.StorageService;

import eu.dzim.example.model.ApplicationModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {
	
	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	
	public Main() {
		ApplicationModel.getInstance();
	}
	
	public static final Pair<String, Class<Integer>> SETTINGS_TEXT_SIZE = new Pair<>("disentisapp.textsize", Integer.class);
	
	@Override
	public void start(Stage stage) throws Exception {
		
		double width = -1;
		double height = -1;
		if (com.gluonhq.charm.down.Platform.isDesktop()) {
			width = 480.0;
			height = 640.0;
			stage.setTitle("Example");
		}
		final Screen primaryScreen = Screen.getPrimary();
		final Rectangle2D visualBounds = primaryScreen.getVisualBounds();
		if (width < 0 || height < 0) {
			width = visualBounds.getWidth();
			height = visualBounds.getHeight();
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Root.fxml"));
		
		final Pane root = loader.load();
		
		final Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
		stage.setScene(scene);
		
		scene.addEventFilter(KeyEvent.ANY, this::handleGlobalKeyEvents);
		
		LOG.info(">>> StorageService: " + Services.get(StorageService.class).orElse(null));
		
		stage.show();
	}
	
	private void handleGlobalKeyEvents(KeyEvent event) {
		// use a more specific key event type like
		// --> KeyEvent.KEY_RELEASED == event.getEventType()
		// --> KeyEvent.KEY_PRESSED == event.getEventType()
		// without it, we would react on both events, thus doing one operation too much
		if (event.getCode().equals(KeyCode.ESCAPE) && KeyEvent.KEY_RELEASED == event.getEventType()) {
			Services.get(LifecycleService.class).ifPresent(service -> service.shutdown());
		}
	}
}
