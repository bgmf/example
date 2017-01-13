package eu.dzim.example.ui;

import java.io.IOException;
import java.util.logging.Logger;

import eu.dzim.example.util.DualAcceptor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CollapsibleItemPane extends BorderPane {
	
	private static final Logger LOG = Logger.getLogger(CollapsibleItemPane.class.getName());
	
	@FXML private CollapsibleItemButton collapsibleButton;
	
	private ObjectProperty<Pane> content = new SimpleObjectProperty<>(this, "content", new Pane());
	private ObjectProperty<Duration> duration = new SimpleObjectProperty<>(this, "duration", Duration.millis(1));
	
	public CollapsibleItemPane() {
		try {
			getLoader().load();
		} catch (IOException e) {
			LOG.severe(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	private FXMLLoader getLoader() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CollapsibleItemPane.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		return loader;
	}
	
	@FXML
	private void initialize() {
		
		centerProperty().bind(content);
		collapsibleButton.contentProperty().bind(content);
		
		collapsibleButton.managedProperty().bind(collapsibleButton.visibleProperty());
	}
	
	/*
	 * getter & setter (if applicable)
	 */
	
	public CollapsibleItemButton getCollapsibleButton() {
		return collapsibleButton;
	}
	
	public DualAcceptor<CollapsibleItemButton, Boolean> getOnActionAcceptor() {
		return collapsibleButton.getOnActionAcceptor();
	}
	
	public void setOnActionAcceptor(DualAcceptor<CollapsibleItemButton, Boolean> onActionAcceptor) {
		collapsibleButton.setOnActionAcceptor(onActionAcceptor);
	}
	
	/*
	 * hide top node
	 */
	
	public final BooleanProperty titleVisbleProperty() {
		return this.collapsibleButton.visibleProperty();
	}
	
	public final boolean getTitleVisible() {
		return this.titleVisbleProperty().get();
	}
	
	public final void setTitleVisible(final boolean visible) {
		this.titleVisbleProperty().set(visible);
	}
	
	/*
	 * glyph 90: name
	 */
	
	public final ObjectProperty<String> glyph90NameProperty() {
		return this.collapsibleButton.glyph90NameProperty();
	}
	
	public final String getGlyph90Name() {
		return this.glyph90NameProperty().get();
	}
	
	public final void setGlyph90Name(final String glyph90Name) {
		this.glyph90NameProperty().set(glyph90Name);
	}
	
	/*
	 * glyph 90: size
	 */
	
	public final ObjectProperty<Number> glyph90SizeProperty() {
		return this.collapsibleButton.glyph90SizeProperty();
	}
	
	public final Number getGlyph90Size() {
		return this.glyph90SizeProperty().get();
	}
	
	public final void setGlyph90Size(final Number glyph90Size) {
		this.glyph90SizeProperty().set(glyph90Size);
	}
	
	/*
	 * glyph 90: visible
	 */
	
	public final BooleanProperty glyph90VisbleProperty() {
		return this.collapsibleButton.glyph90VisbleProperty();
	}
	
	public final boolean getGlyph90Visible() {
		return this.glyph90VisbleProperty().get();
	}
	
	public final void setGlyph90Visible(final boolean glyph90Visible) {
		this.glyph90VisbleProperty().set(glyph90Visible);
	}
	
	/*
	 * glyph 180: name
	 */
	
	public final ObjectProperty<String> glyph180NameProperty() {
		return this.collapsibleButton.glyph180NameProperty();
	}
	
	public final String getGlyph180Name() {
		return this.glyph180NameProperty().get();
	}
	
	public final void setGlyph180Name(final String glyph180Name) {
		this.glyph180NameProperty().set(glyph180Name);
	}
	
	/*
	 * glyph 180: size
	 */
	
	public final ObjectProperty<Number> glyph180SizeProperty() {
		return this.collapsibleButton.glyph180SizeProperty();
	}
	
	public final Number getGlyph180Size() {
		return this.glyph180SizeProperty().get();
	}
	
	public final void setGlyph180Size(final Number glyph180Size) {
		this.glyph180SizeProperty().set(glyph180Size);
	}
	
	/*
	 * glyph 180: visible
	 */
	
	public final BooleanProperty glyph180VisbleProperty() {
		return this.collapsibleButton.glyph180VisbleProperty();
	}
	
	public final boolean getGlyph180Visible() {
		return this.glyph180VisbleProperty().get();
	}
	
	public final void setGlyph180Visible(final boolean glyph180Visible) {
		this.glyph180VisbleProperty().set(glyph180Visible);
	}
	
	/*
	 * Title: text
	 */
	
	public final StringProperty titleTextProperty() {
		return this.collapsibleButton.titleTextProperty();
	}
	
	public final String getTitleText() {
		return this.titleTextProperty().get();
	}
	
	public final void setTitleText(final String text) {
		this.titleTextProperty().set(text);
	}
	
	/*
	 * Title: style
	 */
	
	public final StringProperty titleStyleProperty() {
		return this.collapsibleButton.titleStyleProperty();
	}
	
	public final String getTitleStyle() {
		return this.titleStyleProperty().get();
	}
	
	public final void setTitleStyle(final String style) {
		this.titleStyleProperty().set(style);
	}
	
	/*
	 * Title: userdata
	 */
	
	public final Object getTitleUserData() {
		return this.collapsibleButton.getTitle().getUserData();
	}
	
	public final void setTitleUserData(final Object userData) {
		this.collapsibleButton.getTitle().setUserData(userData);
	}
	
	/*
	 * Button: style
	 */
	
	public final StringProperty buttonStyleProperty() {
		return this.collapsibleButton.buttonStyleProperty();
	}
	
	public final String getButtonStyle() {
		return this.buttonStyleProperty().get();
	}
	
	public final void setButtonStyle(final String style) {
		this.buttonStyleProperty().set(style);
	}
	
	/*
	 * Title: style classes
	 */
	
	public final ObservableList<String> getTitleStyleClass() {
		return collapsibleButton.getTitleStyleClass();
	}
	
	/*
	 * Content: Pane
	 */
	
	public final ObjectProperty<Pane> contentProperty() {
		return this.content;
	}
	
	public final Pane getContent() {
		return this.contentProperty().get();
	}
	
	public final void setContent(final Pane content) {
		this.contentProperty().set(content);
	}
	
	/*
	 * Duration: animation duration
	 */
	
	public final ObjectProperty<Duration> durationProperty() {
		return this.duration;
	}
	
	public final Duration getDuration() {
		return this.durationProperty().get();
	}
	
	public final void setDuration(final Duration duration) {
		this.durationProperty().set(duration);
	}
}
