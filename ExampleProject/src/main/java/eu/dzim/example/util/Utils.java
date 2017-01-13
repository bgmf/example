package eu.dzim.example.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.dzim.example.model.FontData;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class Utils {
	
	private static final Logger LOG = Logger.getLogger(Utils.class.getName());
	
	private static final boolean DEBUG = false;
	
	public static final String SETTINGS_TEXT_SIZE = "disentisapp.textsize";
	
	public static final int TEXT_SIZE_SMALL = -1;
	public static final int TEXT_SIZE_DEFAULT = 0;
	public static final int TEXT_SIZE_LARGE = 1;
	
	public static final String SC_TEXT_XSMALL = "content-text-xsmall";
	public static final String SC_TEXT_SMALL = "content-text-small";
	public static final String SC_TEXT_DEFAULT = "content-text-default";
	public static final String SC_TEXT_LARGE = "content-text-large";
	public static final String SC_TEXT_XLARGE = "content-text-xlarge";
	public static final String SC_TEXT_XXLARGE = "content-text-xxlarge";
	public static final String SC_TEXT_XXXLARGE = "content-text-xxxlarge";
	public static final String SC_TEXT_XXXXLARGE = "content-text-xxxxlarge";
	
	public static final Pair<String, String> SCPAIR_TEXT_XSMALL = new Pair<>(SC_TEXT_XSMALL, "-fx-font-size: 0.6em");
	public static final Pair<String, String> SCPAIR_TEXT_SMALL = new Pair<>(SC_TEXT_SMALL, "-fx-font-size: 0.8em");
	public static final Pair<String, String> SCPAIR_TEXT_DEFAULT = new Pair<>(SC_TEXT_DEFAULT, "-fx-font-size: 1.0em");
	public static final Pair<String, String> SCPAIR_TEXT_LARGE = new Pair<>(SC_TEXT_LARGE, "-fx-font-size: 1.2em");
	public static final Pair<String, String> SCPAIR_TEXT_XLARGE = new Pair<>(SC_TEXT_XLARGE, "-fx-font-size: 1.4em");
	public static final Pair<String, String> SCPAIR_TEXT_XXLARGE = new Pair<>(SC_TEXT_XXLARGE, "-fx-font-size: 1.6em");
	public static final Pair<String, String> SCPAIR_TEXT_XXXLARGE = new Pair<>(SC_TEXT_XXXLARGE, "-fx-font-size: 1.8em");
	public static final Pair<String, String> SCPAIR_TEXT_XXXXLARGE = new Pair<>(SC_TEXT_XXXLARGE, "-fx-font-size: 2.0em");
	
	public static final Color BG_OVERLAY = Color.web("#000000B2");
	
	public static final Color BG_HEADER = Color.web("#ECEAE5");
	public static final Color BG_CONTENT = Color.web("#F7F7F5");
	public static final Color BG_RED = Color.web("#C31622");
	
	public static final Color HEADER = Color.web("#8A8A8A");
	public static final Color RED = Color.web("#9F121C");
	
	// public static final Color ICON_RED = Color.web("#FF0000");
	public static final Color ICON_RED = Color.web("#C31622");
	
	public static final Color BLACK = Color.BLACK;
	public static final Color WHITE = Color.WHITE;
	
	public static final Color TRANSPARENT = Color.TRANSPARENT;
	
	private static final List<String> SC_TO_REMOVE = Arrays.asList(SC_TEXT_XSMALL, SC_TEXT_SMALL, SC_TEXT_DEFAULT, SC_TEXT_LARGE, SC_TEXT_XLARGE,
			SC_TEXT_XXLARGE, SC_TEXT_XXXLARGE, SC_TEXT_XXXXLARGE);
	
	protected Utils() {
		// hide the constructor
	}
	
	public static boolean isDebug() {
		return DEBUG;
	}
	
	public static ArrayList<Node> getAllResizableNodes(Parent root) {
		return getAllNodes(root, true);
	}
	
	public static ArrayList<Node> getAllNodes(Parent root, boolean resizable) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes, resizable);
		return nodes;
	}
	
	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes, boolean resizable) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			if (resizable && (node.getStyleClass().contains(SC_TEXT_XSMALL) || node.getStyleClass().contains(SC_TEXT_SMALL)
					|| node.getStyleClass().contains(SC_TEXT_DEFAULT) || node.getStyleClass().contains(SC_TEXT_LARGE)
					|| node.getStyleClass().contains(SC_TEXT_XLARGE) || node.getStyleClass().contains(SC_TEXT_XXLARGE)
					|| node.getStyleClass().contains(SC_TEXT_XXXLARGE) || node.getStyleClass().contains(SC_TEXT_XXXXLARGE))) {
				nodes.add(node);
			} else if (resizable && node.getUserData() instanceof FontData) {
				nodes.add(node);
			} else if (resizable && node.getUserData() instanceof Integer) {
				nodes.add(node);
			} else if (!resizable) {
				nodes.add(node);
			}
			if (node instanceof Parent)
				addAllDescendents((Parent) node, nodes, resizable);
		}
	}
	
	public static void handleTextSizeChange(Integer initialTextSize, Integer newValue, Node... items) {
		
		if (initialTextSize != null) {
			if (isDebug())
				LOG.log(Level.INFO, "Initiating text size.");
			for (Node l : items)
				updateTextSize(l, initialTextSize);
			return;
		}
		
		for (Node l : items)
			updateTextSize(l, newValue);
	}
	
	public static void updateTextSize(Node labeled, Integer value) {
		
		if (value == null) {
			LOG.warning("Not updating labeled. The value is 'null'.");
			return;
		}
		
		setTextSizeStyleClass(labeled, value);
	}
	
	private static final void setTextSizeStyleClass(Node labeled, Integer value) {
		
		if (labeled == null)
			return;
		
		if (value == null)
			value = TEXT_SIZE_DEFAULT;
		
		List<String> scCopy = new ArrayList<>(labeled.getStyleClass());
		List<String> styleClasses = new ArrayList<>();
		for (String sc : scCopy) {
			if (SC_TO_REMOVE.contains(sc))
				continue;
			styleClasses.add(sc);
		}
		
		Labeled label = null;
		Text text = null;
		Font font = null;
		if (labeled instanceof Labeled) {
			label = (Labeled) labeled;
			font = label.getFont();
		} else if (labeled instanceof Text) {
			text = (Text) labeled;
			font = text.getFont();
		}
		FontData data = null;
		if (labeled.getUserData() instanceof Integer) {
			data = new FontData();
			data.setSize((Integer) labeled.getUserData());
		} else if (labeled.getUserData() instanceof FontData) {
			data = (FontData) labeled.getUserData();
		} else {
			data = new FontData();
			data.setSize(12);
		}
		
		if (font != null) {
			switch (value) {
			case TEXT_SIZE_SMALL: {
				Font f = Font.font(font.getFamily(), data.getWeight(), data.getPosture(), data.getSize() - 2);
				if (label != null)
					label.setFont(f);
				else if (text != null)
					text.setFont(f);
				break;
			}
			case TEXT_SIZE_DEFAULT: {
				Font f = Font.font(font.getFamily(), data.getWeight(), data.getPosture(), data.getSize());
				if (label != null)
					label.setFont(f);
				else if (text != null)
					text.setFont(f);
				break;
			}
			case TEXT_SIZE_LARGE: {
				Font f = Font.font(font.getFamily(), data.getWeight(), data.getPosture(), data.getSize() + 2);
				if (label != null)
					label.setFont(f);
				else if (text != null)
					text.setFont(f);
				break;
			}
			default:
				break;
			}
			// PlatformHelper.run(() -> labeled.getParent().layout());
			
		} else if (labeled.getUserData() instanceof String) {
			
			if (SC_TEXT_XSMALL.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_XSMALL);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_XSMALL);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_SMALL);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_SMALL.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_XSMALL);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_SMALL);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_DEFAULT);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_DEFAULT.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_SMALL);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_DEFAULT);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_LARGE);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_LARGE.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_DEFAULT);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_LARGE);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_XLARGE);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_XLARGE.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_LARGE);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_XLARGE);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_XXLARGE);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_XXLARGE.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_XLARGE);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_XXLARGE);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_XXXLARGE);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_XXXLARGE.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_XXLARGE);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_XXXLARGE);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_XXXXLARGE);
					break;
				default:
					break;
				}
			} else if (SC_TEXT_XXXXLARGE.equalsIgnoreCase((String) labeled.getUserData())) {
				switch (value) {
				case TEXT_SIZE_SMALL:
					styleClasses.add(SC_TEXT_XXXLARGE);
					break;
				case TEXT_SIZE_DEFAULT:
					styleClasses.add(SC_TEXT_XXXXLARGE);
					break;
				case TEXT_SIZE_LARGE:
					styleClasses.add(SC_TEXT_XXXXLARGE);
					break;
				default:
					break;
				}
			} else {
				if (isDebug())
					LOG.info("Not updating labeled. It seems not to contain one of the stylable classes.");
			}
			// labeled.getStyleClass().setAll(styleClasses);
			
			PlatformHelper.run(() -> {
				labeled.getStyleClass().setAll(styleClasses);
				labeled.getParent().layout();
			});
		}
	}
	
	@SuppressWarnings("unused")
	private static int styleClassIndex(Labeled labeled, String styleClass) {
		int i = 0;
		for (String sc : labeled.getStyleClass()) {
			if (sc.equalsIgnoreCase(styleClass))
				return i;
			i++;
		}
		return -1;
	}
}
