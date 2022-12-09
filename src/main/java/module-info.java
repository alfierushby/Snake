module com.game {
    exports com.game; // Used for testing in com.test module
    exports com.game.models;
    opens assets.textures;
    opens assets.textures.Food;
    opens assets.ui;
    opens assets.css;
    opens assets.textures.particles;
    opens assets.levels.tmx;
    // opens Music;
    opens com.game to javafx.fxml;
    exports com.game.enums;
    exports com.game.data;
    exports com.game.views;
    exports com.game.events;
    exports com.game.controllers;
    opens com.game.controllers;
    opens com.game.enums to javafx.fxml;
    opens com.game.views to javafx.fxml;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.almasb.fxgl.all;
    requires annotations;
}