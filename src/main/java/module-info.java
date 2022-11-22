module com.game {
    exports com.game; // Used for testing in com.test module
    opens assets;
    opens assets.textures;
    opens assets.textures.Food;
   // opens Music;
    opens com.game to javafx.fxml;
    exports com.game.enums;
    opens com.game.enums to javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javazoom;
    requires com.almasb.fxgl.all;
}