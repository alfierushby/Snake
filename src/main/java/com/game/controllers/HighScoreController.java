package com.game.controllers;

import com.almasb.fxgl.ui.FXGLScrollPane;
import com.game.data.Score;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.LinkedList;

import static com.game.data.Config.DEFAULT_SAVE_BUNDLE_LIST;

/**
 * Controller for the high-score menu.
 * It doesn't contain a separate View class, instead uses effects from
 * MainMenuView.
 */
public class HighScoreController extends Menu {
    public VBox getVbox() {
        return vbox;
    }
    @Override
    public Pane getRoot() {
        return getVbox();
    }

    public Button getBack_btn() {
        return back_btn;
    }

    public Text getTitle() {
        return title;
    }

    public FXGLScrollPane getScrollpane() {return scrollpane;}

    @FXML
    private Button back_btn;

    @FXML
    private Pane holder;

    @FXML
    private FXGLScrollPane scrollpane;

    @FXML
    private Text title;

    @FXML
    private VBox vbox;

    @FXML
    private AnchorPane root;

    public HighScoreController(MainMenuView view, SnakeModel model) {
        super(view,model);
    }

    @Override
    public void init() {
        super.init();
        getBack_btn().setStyle("-fx-text-fill: #2a353d;");
        getView().animateGradient(getTitle(), Color.WHITE,
                Color.MEDIUMVIOLETRED);
        getView().setInfiniteBobble(getTitle(),1);

        LinkedList<Score> scores =
                getModel().getHighScores().get(DEFAULT_SAVE_BUNDLE_LIST);

        for ( Score score : scores){

        }
    }
}
