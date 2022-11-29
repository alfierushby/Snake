module com.game {
    exports com.game; // Used for testing in com.test module
    exports com.game.models;
    opens assets;
    opens assets.textures;
    opens assets.textures.Food;
   // opens Music;
    opens com.game to javafx.fxml;
    exports com.game.enums;
    exports com.game.views;
    exports com.game.events;
    opens com.game.controllers;
    opens com.game.enums to javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javazoom;
    requires com.almasb.fxgl.all;
}