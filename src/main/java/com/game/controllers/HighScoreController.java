package com.game.controllers;

import com.almasb.fxgl.ui.FXGLScrollPane;
import com.almasb.fxgl.ui.UIController;
import com.almasb.fxgl.ui.UIFactoryService;
import com.game.data.Score;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.LinkedList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;
import static com.game.data.Config.DEFAULT_SAVE_BUNDLE_LIST;
import static com.game.data.Config.DEFAULT_TRANSITION_LENGTH;

/**
 * Controller for the high-score menu.
 * It doesn't contain a separate View class, instead uses effects from
 * MainMenuView.
 * Works from high_scores.fxml.
 * @author Alfie Rushby
 */
public class HighScoreController extends MenuController {
    /**
     * @return Root of highscore entries
     */
    @Override
    public Pane getRoot() {return vbox;}

    /**
     * @return Top-most root below AnchorPane.
     */
    @Override
    public Pane getTopRoot() {return holder;}

    /**
     * @return Back button to return to main menu
     */
    public Button getBack_btn() {
        return back_btn;
    }

    /**
     * @return Title at top of menu
     */
    public Text getTitle() {
        return title;
    }

    /**
     * @return Scroll Pane that holds the high scores
     */
    public VBox getScrollPaneHolder() {return scrollpane_hold;}

    /**
     * @return Image Background of the menu
     */
    public ImageView getBackground() {return background;}
    @FXML
    private Button back_btn;
    @FXML
    private ImageView background;
    @FXML
    private Pane holder;

    @FXML
    private VBox scrollpane_hold;

    @FXML
    private Text title;
    @FXML
    private VBox vbox;

    @FXML
    private AnchorPane root;

    /**
     * Called when the back button is clicked, and returns them to main menu.
     * @param event Mouse Event
     */
    @FXML
    void backMainMenu(MouseEvent event) {
        getView().switchScreen(getView().getScreens().getMainMenu(),
                DEFAULT_TRANSITION_LENGTH);
    }

    /**
     * Sets up the basic variables, Menu View and Snake Model.
     * @param view View of Main Menu
     * @param model Snake Model for the game
     */
    public HighScoreController(MainMenuView view, SnakeModel model) {
        super(view,model);
    }

    /**
     * Called when FXGL has initialised the GUI, and it sets the styling of the
     * elements beyond the CSS.
     */
    @Override
    public void init() {
        super.init();
        getBack_btn().setStyle("-fx-text-fill: #2a353d;");
        getView().setInfiniteBobble(getTitle(),1);
    }
}
