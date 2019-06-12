package org.nhc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.nhc.domain.BookNames;
import org.nhc.domain.Verses;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PrimaryController {

    public Label versesLabel;
    public TextField text;

    private Bible bible = new Bible();

//    @FXML
//    private void switchToSecondary() throws IOException {
//        App.setRoot("secondary");
//    }

    public void buttonClicked(ActionEvent event) {
        String text = this.text.getText();
        String[] split = text.split("\\s");

        String s = split[0];
        BookNames bookName = BookNames.valueOf(s);

        if (split.length == 3) {
            Verses fetch = bible.fetch(bookName, Integer.valueOf(split[1]), Integer.valueOf(split[2]));
            this.versesLabel.setText(fetch.getContent());
        } else if (split.length == 4) {
            List<Verses> between = bible.between(bookName, Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3]));
            this.versesLabel.setText(between.stream().map(Verses::getContent).collect(Collectors.joining("\n")));
        }
    }

    public void touping(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(loadFXML("secondary"));
        Stage  dialog = new Stage();
        dialog.setScene(scene);

        dialog.show();
    }


    private static DialogPane loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
