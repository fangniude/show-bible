module org.nhc {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.nhc to javafx.fxml;
    exports org.nhc;
}