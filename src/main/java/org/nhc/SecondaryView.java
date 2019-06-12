package org.nhc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryView  implements FxmlView<ShowViewModel>, Initializable {
    @FXML
    public Label showLabel;

    @InjectViewModel
    private ShowViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLabel.textProperty().bind(viewModel.getShowVerses());
    }

}
