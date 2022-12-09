package com.game.controllers;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.ui.UIController;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controls the UI in-game, essentially the score in the top left.
 * @author Alfie Rushby
 */
public class SnakeUIController implements UIController, Modeled {

    /**
     * @return Snake Model of the game
     */
    @Override
    public SnakeModel getModel() {return m_model;}

    /**
     * @return Label that shows the game score and player name
     */
    public Text getLabelScore() {
        return labelScore;
    }

    @FXML
    private Text labelScore;

    private final SnakeModel m_model;

    /**
     * @param model Snake Model of the game
     */
    public SnakeUIController(SnakeModel model){
        m_model = model;
    }

    /**
     * Adds a binding of the game's Score and the Label's text, and includes
     * the player's name in display.
     */
    @Override
    public void init() {
        getLabelScore().textProperty().bind(getModel()
                .getScoreProp().asString(getModel().getPlayerName() +
                        "'s Score: %d"));
    }

}
