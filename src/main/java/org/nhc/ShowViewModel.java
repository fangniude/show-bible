package org.nhc;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowViewModel implements ViewModel {
    private StringProperty showVerses = new SimpleStringProperty("");

    public String getVerses() {
        return showVerses.get();
    }

    public void setVerses(String verses) {
        showVerses.set(verses);
    }
}
