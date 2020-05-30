package org.nhc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.nhc.domain.BookNames;
import org.nhc.domain.Verses;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author lewis
 */
public class PrimaryView implements FxmlView<ShowViewModel>, Initializable {
    @FXML
    public Label showLabel;

    @FXML
    public TextField text;

    @FXML
    public ScrollPane scrollPane;
    public ComboBox<String> fontName;
    public ComboBox<Integer> fontSize;
    public ColorPicker fontColor;
    public ComboBox<Double> lineSpacing;
    public ColorPicker backColor;
    public VBox showPane;

    @InjectViewModel
    private ShowViewModel viewModel;

    private Bible bible = new Bible();
    private SimpleObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private Stage dialog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLabel.textProperty().bind(viewModel.getShowVerses());
        dialog = fullScreenDailog();

        scrollPane.vvalueProperty().addListener(observable -> showPaneChanged());

        List<String> fontNames = Font.getFamilies();
        fontName.setItems(FXCollections.observableList(fontNames));
        fontName.getSelectionModel().select(1);

        fontSize.setItems(FXCollections.observableArrayList(30, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100));
        fontSize.getSelectionModel().select(5);

        lineSpacing.setItems(FXCollections.observableArrayList(0.5, 0.8, 1.0, 1.2, 1.3, 1.5, 1.8, 2.0, 2.5));
        lineSpacing.getSelectionModel().select(5);

        fontColor.setValue(Color.BLACK);
    }

    public void search() {
        String text = this.text.getText();
        String[] split = text.split("\\s");

        String s = split[0];
        BookNames bookName = BookNames.valueOf(s);

        switch (split.length) {
            case 1:
                List<Verses> chapter1Verses = bible.fetch(bookName, 1);
                this.viewModel.setVerses(chapter1Verses.stream().map(Verses::verseContent).collect(Collectors.joining("\n")));
                break;
            case 2:
                List<Verses> chapterVerses = bible.fetch(bookName, Integer.parseInt(split[1]));
                this.viewModel.setVerses(chapterVerses.stream().map(Verses::verseContent).collect(Collectors.joining("\n")));
                break;
            case 3:
                Verses fetch = bible.fetch(bookName, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                this.viewModel.setVerses(fetch.verseContent());
                break;
            case 4:
                List<Verses> between = bible.between(bookName, Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                this.viewModel.setVerses(between.stream().map(Verses::verseContent).collect(Collectors.joining("\n")));
                break;
            default:
                // do nothing
        }
        formatChanged();
    }

    public void touping() {
        showPaneChanged();
        dialog.show();
    }

    private Stage fullScreenDailog() {
        ImageView imageView = new ImageView();
        imageView.imageProperty().bind(imageProperty);

        Stage dialog = new Stage();
        dialog.setFullScreen(true);
        dialog.setScene(new Scene(new Pane(imageView)));

        return dialog;
    }

    private Rectangle2D secondScreenBounds() {
        ObservableList<Screen> screens = Screen.getScreens();
        Screen primary = Screen.getPrimary();
        Optional<Screen> touying = screens.stream().filter(s -> !s.equals(primary)).findFirst();

        Screen screen = touying.orElse(primary);
        return screen.getBounds();
    }

    private WritableImage image() {
        final Bounds bounds = scrollPane.getViewportBounds();
        Rectangle2D d = secondScreenBounds();

        final WritableImage image = new WritableImage(
                (int) Math.round(d.getWidth()),
                (int) Math.round(d.getHeight()));

        final SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(javafx.scene.transform.Transform.scale(d.getWidth() / bounds.getWidth(), d.getHeight() / bounds.getHeight()));

        WritableImage writableImage = scrollPane.snapshot(spa, image);
        imageProperty.set(writableImage);
        return writableImage;
    }

    public void showPaneChanged() {
        Rectangle2D d = secondScreenBounds();
        scrollPane.setPrefHeight(scrollPane.getViewportBounds().getWidth() * d.getHeight() / d.getWidth());
        showPane.setPrefWidth(scrollPane.getViewportBounds().getWidth());

        dialog.setX(d.getMinX());
        dialog.setY(d.getMinY());

        imageProperty.set(image());
    }

    public void formatChanged() {
        Font font = Font.font(String.valueOf(fontName.getSelectionModel().getSelectedItem()), Double.parseDouble(String.valueOf(fontSize.getSelectionModel().getSelectedItem())));
        showLabel.setFont(font);

        showLabel.setTextFill(fontColor.getValue());

        showLabel.setLineSpacing(Double.parseDouble(String.valueOf(lineSpacing.getSelectionModel().getSelectedItem())));

        showLabel.setBackground(new Background(new BackgroundFill(backColor.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));

        showPaneChanged();
    }

    public void checkTextEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            search();
        }
    }
}
