package com.game.controllers;
import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.ui.UIController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MainMenuController implements UIController {

    public VBox getVbox() {
        return vbox;
    }

    ParticleSystem system;
    @FXML
    private Button highscores_btn;

    @FXML
    private Button options_btn;
    @FXML
    private Button newgame_btn;

    @FXML
    private AnchorPane holder;

    @FXML
    private Text title;
    @FXML
    private VBox vbox;


    @Override
    public void init() {
        //highscores_btn.setStyle("-fx-background-color: transparent");

        LinearGradient grad = new LinearGradient(0.0, 1.0, 1.0, 0.2, true,
                CycleMethod.NO_CYCLE,
                new Stop(0.6, Color.color(0.1, 0.6, .20)),
                new Stop(0.85, Color.color(0, 0.4, 0.0)),
                new Stop(1.0, Color.FORESTGREEN));

        LinearGradient grad_common = new LinearGradient(0.0, 1.0, 1.0, 0.2,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0.6, Color.color(1.0, 0.8, 0.0)),
                new Stop(0.85, Color.color(1.0, 0.8, 0.0 )),
                new Stop(1.0, Color.WHITE));

        newgame_btn.textFillProperty().bind(new SimpleObjectProperty<>(grad_common));





    }
}
