package com.game.controllers;
import com.almasb.fxgl.core.collection.Array;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.almasb.fxgl.ui.UI;
import com.game.data.Score;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;

/**
 * Controls the Main Menu screen, ran at the game start. Works from
 * main_menu.fxml.
 */
public class MainMenuController extends MenuController {

    /**
     * @return New Game button to start the snake game
     */
    public Button getNewgame_btn() {
        return newgame_btn;
    }

    /**
     * @return High Score button to show the highscores menu
     */
    public Button getHighscores_btn() {
        return highscores_btn;
    }

    /**
     * @return Text Title of the main menu
     */
    public Text getTitle() {
        return title;
    }

    /**
     * @return Root that contains the menu options
     */
    @Override
    public Pane getRoot() {return vbox;}

    /**
     * @return Top-most root below AnchorPane.
     */
    public Pane getTopRoot() {
        return holder;
    }

    /**
     * @return Image background of the main menu
     */
    public ImageView getBackground() {return background;}
    @FXML
    private Button highscores_btn;
    @FXML
    private ImageView background;
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

    /**
     * Called when the user wants to start the game, asks for a difficulty
     * and begins the game. Asks for player name if not saved.
     * @param event Mouse event
     */
    @FXML
    void startGame(MouseEvent event) {
        // Ask for name if they haven't given one.
        Consumer<Object> result = new Consumer<Object>() {
            @Override
            public void accept(Object choice) {
                getModel().setDifficulty((String) choice);
                if (Objects.equals(getModel().getPlayerName(), "")) {
                    getView().playerNameInput(true);
                } else{
                    getGameController().startNewGame();
                }
            }
        };

        getDialogService().showChoiceBox("Please pick a difficulty.",result
               ,DEFAULT_DIFFICULTY_EASY, DEFAULT_DIFFICULTY_EXTRA_ORDER);



    }

    /**
     * Switches screen to the Option's Menu.
     * @param event Mouse event
     */
    @FXML
    void startOptions(MouseEvent event) {
        OptionsController controller = getView().getScreens().getOptions();
        getView().switchScreen(controller, DEFAULT_TRANSITION_LENGTH);
    }

    /**
     * Switches screen to the HighScores menu, and populates the Scroll Pane
     * that contains the high scores. Gives effects depending on position.
     * @param event Mouse Event
     */
    @FXML
    void startHighScore(MouseEvent event){
        HighScoreController controller = getView().getScreens().getHighScores();
        LinkedList<Score> scores =
                getModel().getHighScores().get(DEFAULT_SAVE_BUNDLE_LIST);
        int i = 0;
        controller.getScrollPaneHolder().getChildren().clear();

        for ( Score score : scores){
            i++;
            Text txt = new Text();
            txt.setFill(Color.BLACK);
            txt.setText(String.format("%d | %s | %s : %05d", i,
                    score.difficulty(),
                    score.name()
                    ,score.score()));
            txt.setFont(Font.font("System", FontWeight.BOLD,28));
            switch (i) {
                case 1 -> {
                    getView().animateGradient(txt,Color.rgb(183, 134, 40),
                            Color.rgb(252, 194, 1));
                    getView().setInfiniteBobble(txt, 2);
                }
                case 2 -> {
                    getView().animateGradient(txt,Color.rgb(168, 169, 173),
                            Color.rgb(227, 227, 227));
                }
                case 3 -> {
                    getView().animateGradient(txt,Color.rgb(128, 74, 0),
                            Color.rgb(176, 141, 87));
                }
            }
            controller.getScrollPaneHolder().getChildren().add(txt);
        }
        getView().switchScreen(controller, DEFAULT_TRANSITION_LENGTH);
    }

    /**
     * Sets up the two main variables, Menu View and Snake Model.
     * @param view Main Menu View that manages the appearance of the Main Menu
     * @param model Snake Model of the game
     */
    public MainMenuController(MainMenuView view, SnakeModel model){
        super(view,model);
    }

}
