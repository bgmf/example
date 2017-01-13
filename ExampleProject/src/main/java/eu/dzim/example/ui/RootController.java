package eu.dzim.example.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.dzim.example.model.ApplicationModel;
import eu.dzim.example.util.DualAcceptor;
import eu.dzim.example.util.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RootController {
	
	private static final Logger LOG = Logger.getLogger(RootController.class.getName());
	
	@FXML private StackPane content;
	@FXML private ScrollPane scrollContent;
	@FXML private VBox contentBox;
	@FXML private ProgressIndicator progress;
	@FXML private Button showContent;
	// @FXML private Button showTextSize;
	
	private boolean contentLoaded = false;;
	
	private List<CollapsibleItemPane> panes = new ArrayList<>();
	private List<Node> resizableNodes = new ArrayList<>();
	
	private ChangeListener<Number> onContentTextSizeChange = this::handleContentTextSizeChanged;
	
	private DualAcceptor<CollapsibleItemButton, Boolean> collapsibleItemAction = this::handleCollapsibleItemAction;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	@FXML
	public void initialize() {
		
		ApplicationModel.getInstance().textSizeProperty().addListener(onContentTextSizeChange);
		
		progress.managedProperty().bind(progress.visibleProperty());
		
		showContent.setOnAction(e -> showContent());
		// showTextSize.setOnAction(e -> switchThroughTextSize());
	}
	
	private void showContent() {
		if (contentLoaded)
			return;
		progress.setVisible(true);
		Task<Pane> task = new Task<Pane>() {
			@Override
			protected Pane call() throws Exception {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ObjectRestauration.fxml"));
				return loader.load();
			}
		};
		task.setOnSucceeded(value -> {
			Pane result = (Pane) value.getSource().getValue();
			if (result == null)
				return;
			
			for (CollapsibleItemPane pane : panes) {
				pane.setOnActionAcceptor(null);
			}
			contentBox.getChildren().add(result);
			panes = getCollapsibleItemPanesFromContent();
			for (CollapsibleItemPane pane : panes) {
				pane.setOnActionAcceptor(collapsibleItemAction);
			}
			
			resizableNodes.clear();
			resizableNodes.addAll(Utils.getAllResizableNodes(contentBox));
			handleContentTextSizeChanged(ApplicationModel.getInstance().textSizeProperty(), null, ApplicationModel.getInstance().getTextSize());
			progress.setVisible(false);
			contentLoaded = true;
			executor.shutdownNow();
		});
		task.setOnFailed(value -> {
			Throwable t = value.getSource().getException();
			LOG.log(Level.SEVERE, t.getMessage(), t);
			progress.setVisible(false);
		});
		executor.submit(task);
	}
	
	// private void switchThroughTextSize() {
	// switch (ApplicationModel.getInstance().getTextSize()) {
	// case Utils.TEXT_SIZE_SMALL:
	// ApplicationModel.getInstance().setTextSize(Utils.TEXT_SIZE_DEFAULT);
	// case Utils.TEXT_SIZE_DEFAULT:
	// ApplicationModel.getInstance().setTextSize(Utils.TEXT_SIZE_LARGE);
	// case Utils.TEXT_SIZE_LARGE:
	// ApplicationModel.getInstance().setTextSize(Utils.TEXT_SIZE_SMALL);
	// default:
	// ApplicationModel.getInstance().setTextSize(Utils.TEXT_SIZE_DEFAULT);
	// }
	// }
	
	private List<CollapsibleItemPane> getCollapsibleItemPanesFromContent() {
		List<CollapsibleItemPane> panes = new ArrayList<>();
		// only collect first OR second tier panes
		for (Node node : content.getChildren()) {
			if (node instanceof CollapsibleItemPane)
				panes.add((CollapsibleItemPane) node);
			else if (node instanceof Pane) {
				for (Node child : ((Pane) node).getChildren()) {
					if (child instanceof CollapsibleItemPane)
						panes.add((CollapsibleItemPane) child);
				}
			}
		}
		return panes;
	}
	
	private void handleContentTextSizeChanged(ObservableValue<? extends Number> obs, Number o, Number n) {
		new Thread(() -> {
			Utils.handleTextSizeChange(ApplicationModel.getInstance().getTextSize(), null, resizableNodes.toArray(new Node[0]));
		}).start();
	}
	
	private void handleCollapsibleItemAction(CollapsibleItemButton source, Boolean visible) {
		if (!visible)
			return;
		Node parent = source.getParent();
		for (CollapsibleItemPane pane : panes) {
			if (parent == pane)
				continue;
			pane.getCollapsibleButton().hideContent();
		}
	}
}
