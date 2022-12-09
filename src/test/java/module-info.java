module com.test {
    requires com.almasb.fxgl.all;
    requires com.game;
    requires org.junit.jupiter.api;
    requires com.almasb.fxgl.test;
    requires com.almasb.fxgl.core;
    opens com.test;
    exports com.test;
}