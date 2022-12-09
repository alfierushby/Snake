package com.game.controllers;

import com.almasb.fxgl.ui.FXGLChoiceBox;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.game.data.Config.*;

/**
 * The controller for the option's menu, works from high_scores.fxml.
 */
public class OptionsController extends MenuController {

    /**
     * @return Back button to return to the main menu
     */
    public Button getBack_btn() {
        return back_btn;
    }

    /**
     * @return Reset button to reset the player name
     */
    public Button getReset_btn() { return reset_btn;}

    /**
     * @return Text title of the option's menu
     */
    public Text getTitle() {
        return title;
    }

    /**
     * @return Choice box of the background image selected
     */
    public FXGLChoiceBox<String> getBackgroundChoices() {
        return background_choices;
    }

    /**
     * @return Choice box of the snake body image selected
     */
    public FXGLChoiceBox<String> getSnakeBodyChoices() {
        return snake_body_choices;
    }

    /**
     * @return Choice box of the snake head image selected
     */
    public FXGLChoiceBox<String> getSnakeHeadChoices() {
        return snake_head_choices;
    }

    /**
     * @return Image background of the menu
     */
    public ImageView getBackground() {
        return background;
    }

    /**
     * @return Pane that contains all the choice boxes
     */
    @Override
    public Pane getRoot() {
        return vbox;
    }

    /**
     * @return Pane just below the root AnchorPane, contains everything.
     */
    @Override
    public Pane getTopRoot() {
        return holder;
    }

    @FXML
    private Button back_btn;

    @FXML
    private Button reset_btn;

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

    /**
     * @param view Main Menu View for managing effects and switching screens
     * @param model Snake Model for managing game state
     */
    public OptionsController(MainMenuView view, SnakeModel model) {
        super(view, model);
    }

    /**
     * Called when the user clicks the reset button, and prompts a new name.
     * @param event Mouse event
     */
    @FXML
    void resetName(MouseEvent event) {
        getView().playerNameInput(false);
    }


    /**
     * Called when the user clicks the back button, and switches to the main
     * menu.
     * @param event Mouse event
     */
    @FXML
    void backMainMenu(MouseEvent event) {
        getView().switchScreen(getView().getScreens().getMainMenu(),
                DEFAULT_TRANSITION_LENGTH);
    }

    /**
     * Called when the user changes their selected background image, and sets
     * said background image to every menu.
     * @param event Mouse event
     */
    @FXML
    void backgroundAction(ActionEvent event) {
        getModel().setBackground(getBackgroundChoices().getValue());
        for (MenuController control : getView().getScreens().getControllers()){
            control.setBackground(getModel().getBackgroundPath());
        }
    }

    /**
     * Called when the user changes their selected Snake Body image, and sets
     * the Snake Model.
     * @param event Mouse event
     */
    @FXML
    void snakeBodyAction(ActionEvent event) {
        getModel().setSnakeBody(getSnakeBodyChoices().getValue());
    }


    /**
     * Called when the user changes their selected Snake Head image, and sets
     * the Snake Model.
     * @param event Mouse event
     */
    @FXML
    void snakeHeadAction(ActionEvent event) {
        getModel().setSnakeHead(getSnakeHeadChoices().getValue());
    }

    /**
     * Sets up listeners such that whenever the model's selected images
     * change, the choice box shows the correct selected value.
     */
    private void setupSavedListeners(){
        getModel().getBackgroundPathProp().addListener(e->{
            getBackgroundChoices().setValue(getModel().getBackground());
        });
        getModel().getSnakeHeadPathProp().addListener(e->{
            getSnakeHeadChoices().setValue(getModel().getSnakeHead());
        });
        getModel().getSnakeBodyPathProp().addListener(e->{
            getSnakeBodyChoices().setValue(getModel().getSnakeBody());
        });
    }

    /**
     * Called on initiation, and sets up the choice boxes' contents depending
     * on the default options defined in the {@link com.game.data.Config}.
     */
    @Override
    public void init() {
        super.init();
        getBack_btn().setStyle("-fx-text-fill: #2a353d;");
        getView().animateGradient(getReset_btn(), Color.VIOLET,Color.DARKRED);
        getView().setInfiniteBobble(getTitle(), 1);
        setBobble(getReset_btn(),.25);

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

        setupSavedListeners();

    }
}