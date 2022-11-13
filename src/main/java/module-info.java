module com.game {
    exports com.game; // Used for testing in com.test module
    opens Food;
    opens com.game to javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javazoom;
}