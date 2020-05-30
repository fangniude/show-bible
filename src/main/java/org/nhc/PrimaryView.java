package org.nhc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.ScrollEvent;
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

public class PrimaryView implements FxmlView<ShowViewModel>, Initializable {
    @FXML
    public Label showLabel;

    @FXML
    public TextField text;

    @FXML
    public ScrollPane scrollPane;
    public ComboBox fontName;
    public ComboBox fontSize;
    public ColorPicker fontColor;
    public ComboBox lineSpacing;
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

        scrollPane.vvalueProperty().addListener(observable -> showPaneChanged(null));

        List<String> fontNames = Font.getFamilies();
        fontName.setItems(FXCollections.observableList(fontNames));
        fontName.getSelectionModel().select(1);

        fontSize.setItems(FXCollections.observableArrayList(30, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100));
        fontSize.getSelectionModel().select(5);

        lineSpacing.setItems(FXCollections.observableArrayList(0.5, 0.8, 1.0, 1.2, 1.3, 1.5, 1.8, 2, 2.5));
        lineSpacing.getSelectionModel().select(5);

        fontColor.setValue(Color.BLACK);
    }

    public void buttonClicked(ActionEvent actionEvent) {
        String text = this.text.getText();
        String[] split = text.split("\\s");

        String s = split[0];
        BookNames bookName = BookNames.valueOf(s);

        if (split.length == 3) {
            Verses fetch = bible.fetch(bookName, Integer.valueOf(split[1]), Integer.valueOf(split[2]));
            this.viewModel.setVerses(fetch.getContent());
        } else if (split.length == 4) {
            List<Verses> between = bible.between(bookName, Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3]));
            this.viewModel.setVerses(between.stream().map(Verses::getContent).collect(Collectors.joining("\n")));
        }
        formatChanged(null);
    }

    public void touping(ActionEvent actionEvent) {
        showPaneChanged(null);
        dialog.show();
    }

    private Stage fullScreenDailog() {
        ImageView imageView = new ImageView();
        imageView.imageProperty().bind(imageProperty);

        Rectangle2D bounds = secondScreenBounds();

        Stage dialog = new Stage();
        dialog.setFullScreen(true);

        dialog.setX(bounds.getMinX());
        dialog.setY(bounds.getMinY());

        Scene scene = new Scene(new Pane(imageView));
        dialog.setScene(scene);
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

    public void showPaneChanged(ScrollEvent scrollEvent) {

        Rectangle2D d = secondScreenBounds();
        scrollPane.setPrefHeight(scrollPane.getViewportBounds().getWidth() * d.getHeight() / d.getWidth());
        showPane.setPrefWidth(scrollPane.getViewportBounds().getWidth());

        imageProperty.set(image());
    }

    public void formatChanged(ActionEvent actionEvent) {
        Font font = Font.font(String.valueOf(fontName.getSelectionModel().getSelectedItem()), Double.valueOf(String.valueOf(fontSize.getSelectionModel().getSelectedItem())));
        showLabel.setFont(font);

        showLabel.setTextFill(fontColor.getValue());

        showLabel.setLineSpacing(Double.valueOf(String.valueOf(lineSpacing.getSelectionModel().getSelectedItem())));

        showLabel.setBackground(new Background(new BackgroundFill(backColor.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));

        showPaneChanged(null);
    }
}
