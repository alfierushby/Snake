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

public abstract class MenuController implements UIController, Modeled {

    public abstract Pane getRoot();
    public abstract Pane getTopRoot();
    public abstract ImageView getBackground();
    public MainMenuView getView() {
        return m_view;
    }
    public SnakeModel getModel() {return m_model;}
    private final MainMenuView  m_view;
    private final SnakeModel m_model;

    public Boolean setBackground(String path) {
        Image img = texture(path).getImage();
        if(img.isError()){
            System.out.println("Path set wrong!");
            return false;
        }
        getBackground().setImage(img);
        return true;
    }

    public MenuController(MainMenuView view, SnakeModel model){
        m_model = model;
        m_view = view;
    }

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

    @Override
    public void init() {
        for (Node node : getRoot().getChildren()) {
            if (node instanceof Button) {
                setBobble(node, 0.25);
            }
        }
    }
}
