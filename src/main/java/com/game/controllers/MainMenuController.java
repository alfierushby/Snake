package com.game.controllers;
import com.almasb.fxgl.ui.UI;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.DEFAULT_HIGH_SCORES_UI;

public class MainMenuController extends Menu {

    public boolean setHighScoreMenu(UI m_high_score_menu) {
        this.m_high_score_menu = m_high_score_menu;
        return true;
    }

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

    @Override
    public Pane getRoot() {
        return getVbox();
    }

    public Pane getHolder() {
        return holder;
    }

    public UI getHighScoreMenu() {
        return m_high_score_menu;
    }

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
    private UI m_high_score_menu;

    @FXML
    void startGame(MouseEvent event) {
        getGameController().startNewGame();
    }

    @FXML
    void startHighScore(MouseEvent event){
        getHighScoreMenu().getRoot().setViewOrder(1);
    }

    public MainMenuController(MainMenuView view, SnakeModel model){
        super(view,model);
    }

    @Override
    public void init() {
        super.init();
        System.out.println("My ass: " + getModel().getState());
        HighScoreController uiController = new HighScoreController(getView(),
                getModel());
        UI ui = getAssetLoader().loadUI(DEFAULT_HIGH_SCORES_UI,uiController);
        getHolder().setOpacity(0);
        getView().getContentRoot().getChildren().add(0,ui.getRoot());

        setHighScoreMenu(ui);
    }
}
