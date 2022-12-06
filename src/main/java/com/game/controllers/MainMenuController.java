package com.game.controllers;
import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.ui.UIController;
import com.game.views.MainMenuView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    public Button getNewgame_btn() {
        return newgame_btn;
    }

    public Button getHighscores_btn() {
        return highscores_btn;
    }

    public Text getTitle() {
        return title;
    }

    public MainMenuView getView() {
        return m_view;
    }

    public Pane getHolder() {
        return holder;
    }

    private final MainMenuView  m_view;
    @FXML
    private Button highscores_btn;

    @FXML
    private Button options_btn;
    @FXML
    private Button newgame_btn;

    @FXML
    private Pane holder;

    @FXML
    private Text title;
    @FXML
    private VBox vbox;

    @FXML
    void startGame(MouseEvent event) {
        getGameController().startNewGame();
    }

    public MainMenuController(MainMenuView view){
        m_view = view;
    }

    @Override
    public void init() {

        for (Node node : getVbox().getChildren()){
            Animation<?> bobble = getView().setInfiniteBobble(node,0.25);
            bobble.pause();
            node.setOnMouseEntered(e->{
                bobble.resume();
            });
            node.setOnMouseExited(e->{
                bobble.pause();
            });
        }
    }
}
