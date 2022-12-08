package com.game.controllers;

import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FXGLChoiceBox;
import com.game.GameUtil;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.game.data.Config.*;

public class OptionsController extends MenuController {

    public Button getBack_btn() {
        return back_btn;
    }
    public Text getTitle() {
        return title;
    }

    public FXGLChoiceBox<String> getBackgroundChoices() {
        return background_choices;
    }

    public FXGLChoiceBox<String> getSnakeBodyChoices() {
        return snake_body_choices;
    }

    public FXGLChoiceBox<String> getSnakeHeadChoices() {
        return snake_head_choices;
    }
    public ImageView getBackground() {
        return background;
    }

    @FXML
    private Button back_btn;

    @FXML
    private FXGLChoiceBox<String> background_choices;

    @FXML
    private Pane holder;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox scrollpane_hold;

    @FXML
    private FXGLChoiceBox<String> snake_body_choices;

    @FXML
    private FXGLChoiceBox<String> snake_head_choices;

    @FXML
    private Text title;
    @FXML
    private ImageView background;

    @FXML
    private VBox vbox;

    @Override
    public Pane getRoot() {
        return vbox;
    }

    @Override
    public Pane getTopRoot() {
        return holder;
    }


    public OptionsController(MainMenuView view, SnakeModel model) {
        super(view, model);
    }

    @FXML
    void backMainMenu(MouseEvent event) {
        getView().switchScreen(getView().getScreens().getMainMenu(),
                DEFAULT_TRANSITION_LENGTH);
    }

    @FXML
    void backgroundAction(ActionEvent event) {
        getModel().setBackgroundPath(
                getPath(DEFAULT_BACKGROUND_OPTIONS,
                        getBackgroundChoices().getValue()));
        for (MenuController control : getView().getScreens().getControllers()){
            control.setBackground(getModel().getBackgroundPath());
        }
    }

    @FXML
    void snakeBodyAction(ActionEvent event) {
        getModel().setSnakeBodyPath(
                getPath(DEFAULT_SNAKE_BODY_OPTIONS,
                        getSnakeBodyChoices().getValue()));
    }

    @FXML
    void snakeHeadAction(ActionEvent event) {
        getModel().setSnakeHeadPath(
                getPath(DEFAULT_SNAKE_HEAD_OPTIONS,
                        getSnakeHeadChoices().getValue()));
    }

    private String getPath(Map<String,String> map, String entry){
        return map.get(entry);
    }
    @Override
    public void init() {
        super.init();
        getBack_btn().setStyle("-fx-text-fill: #2a353d;");
        getView().setInfiniteBobble(getTitle(), 1);
        // Setup background option
        for(String key : DEFAULT_BACKGROUND_OPTIONS.keySet()){
            getBackgroundChoices().getItems().add(key);
        }
        // Setup snake head option
        for(String key : DEFAULT_SNAKE_HEAD_OPTIONS.keySet()){
            getSnakeHeadChoices().getItems().add(key);
        }

        // Setup snake body option
        for(String key : DEFAULT_SNAKE_BODY_OPTIONS.keySet()){
            getSnakeBodyChoices().getItems().add(key);
        }
    }
}