package com.game.controllers;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.ui.UIController;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

/**
 * An abstract class every Menu Controller should extend. Contains basic
 * expected functionality.
 */
public abstract class MenuController implements UIController, Modeled {

    /**
     * @return A Root that should contain the buttons to apply hover effects on.
     */
    public abstract Pane getRoot();

    /**
     * @return A root that contains every node in the menu
     */
    public abstract Pane getTopRoot();

    /**
     * @return A node that acts as the Image Background of the Menu
     */
    public abstract ImageView getBackground();

    /**
     * @return The Main Menu View used by every menu
     */
    public MainMenuView getView() {
        return m_view;
    }

    /**
     * @return The Snake Model for game state modification
     */
    public SnakeModel getModel() {return m_model;}
    private final MainMenuView  m_view;
    private final SnakeModel m_model;

    /**
     * @param path Relative path calculated from /assets/textures
     * @return true if the path was correct
     */
    public Boolean setBackground(String path) {
        Image img = texture(path).getImage();
        if(img.isError()){
            System.out.println("Path set wrong!");
            return false;
        }
        getBackground().setImage(img);
        return true;
    }

    /**
     * @param view MainMenu View for controlling visual effects
     * @param model SnakeModel for game state modifications
     */
    public MenuController(MainMenuView view, SnakeModel model){
        m_model = model;
        m_view = view;
    }

    /**
     * Applys a bobble effect whenever the mouse hovers over the specified node.
     * @param node Node to apply mouse-over bobble on
     * @param speed Speed at which bobble occurs
     * @return true if the speed is non-negative
     */
    public boolean setBobble(Node node, double speed){
        if(speed<0){
            System.out.println("Trying to set negative speed in Bobble!");
            return false;
        }
        Animation<?> bobble = getView().setInfiniteBobble(node,speed);
        bobble.pause();
        node.setOnMouseEntered(e->{
            bobble.resume();
        });
        node.setOnMouseExited(e->{
            bobble.pause();
        });
        return true;
    }

    /**
     * On menu initation, apply the bobble hover effect over every button in
     * the root.
     */
    @Override
    public void init() {
        for (Node node : getRoot().getChildren()) {
            if (node instanceof Button) {
                setBobble(node, 0.25);
            }
        }
    }
}
