// Module for testing the com.game module
open module com.test {
    requires com.game;
    requires org.junit.jupiter;
    requires org.junit.jupiter.engine;
    requires org.junit.jupiter.api;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javazoom;
    exports  com.test;
}