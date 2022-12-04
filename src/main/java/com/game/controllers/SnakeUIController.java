package com.game.controllers;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SnakeUIController implements UIController {

    @FXML
    private Text labelScore;

    public boolean setGameScene(GameScene m_scene) {
        this.m_scene = m_scene;
        return true;
    }

    public GameScene getGameScene() {
        return m_scene;
    }

    public Text getLabelScore() {
        return labelScore;
    }

    private GameScene m_scene;

    public SnakeUIController(GameScene scene){
        setGameScene(scene);
    }

    @Override
    public void init() {

    }

}
