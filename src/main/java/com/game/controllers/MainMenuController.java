package com.game.controllers;
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

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.DEFAULT_SAVE_BUNDLE_LIST;
import static com.game.data.Config.DEFAULT_TRANSITION_LENGTH;

public class MainMenuController extends MenuController {

    public Button getNewgame_btn() {
        return newgame_btn;
    }

    public Button getHighscores_btn() {
        return highscores_btn;
    }

    public Text getTitle() {
        return title;
    }

    @Override
    public Pane getRoot() {return vbox;}

    public Pane getTopRoot() {
        return holder;
    }

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

    @FXML
    void startGame(MouseEvent event) {
        // Ask for name if they haven't given one.
        if (Objects.equals(getModel().getPlayerName(), "")) {
            System.out.println("Name is????");
            getView().playerNameInput(true);
        } else{
            getGameController().startNewGame();
        }
    }
    @FXML
    void startOptions(MouseEvent event) {
        OptionsController controller = getView().getScreens().getOptions();
        getView().switchScreen(controller, DEFAULT_TRANSITION_LENGTH);
    }

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
            txt.setText(String.format("%d | %s : %05d", i,score.name()
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

    public MainMenuController(MainMenuView view, SnakeModel model){
        super(view,model);
    }

    @Override
    public void init() {
        super.init();
    }
}
