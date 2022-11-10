module com.game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires javazoom;
    opens Food;
    opens Logo;
    opens com.game to javafx.fxml;
    exports com.game;
}