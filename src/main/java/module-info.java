module com.game {
    exports com.game; // Used for testing in com.test module
    opens assets.textures;
    opens assets.textures.Food;
    opens assets.ui;
    opens assets.css;
    opens assets.textures.particles;
    opens assets.levels.tmx;
    opens assets.music;
    opens com.game.controllers;
    exports com.game.controllers;
    exports com.game.models;
    exports com.game.enums;
    exports com.game.data;
    exports com.game.views;
    exports com.game.collisions;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires javazoom;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires com.almasb.fxgl.test;
    requires org.junit.jupiter.api;
}
