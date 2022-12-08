package com.game.controllers;

import com.almasb.fxgl.ui.FXGLScrollPane;
import com.almasb.fxgl.ui.UIController;
import com.almasb.fxgl.ui.UIFactoryService;
import com.game.data.Score;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 */
public class HighScoreController extends MenuController {
    @Override
    public Pane getRoot() {return vbox;}

    @Override
    public Pane getTopRoot() {return holder;}

    public Button getBack_btn() {
        return back_btn;
    }

    public Text getTitle() {
        return title;
    }

    public VBox getScrollPaneHolder() {return scrollpane_hold;}

    @FXML
    private Button back_btn;

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

    @FXML
    void backMainMenu(MouseEvent event) {
        getView().switchScreen(getView().getScreens().getMainMenu(),
                DEFAULT_TRANSITION_LENGTH);
    }

    public HighScoreController(MainMenuView view, SnakeModel model) {
        super(view,model);
    }

    @Override
    public void init() {
        super.init();
        getBack_btn().setStyle("-fx-text-fill: #2a353d;");
        getView().setInfiniteBobble(getTitle(),1);
    }
}
