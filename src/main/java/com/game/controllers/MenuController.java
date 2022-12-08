package com.game.controllers;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.ui.UIController;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public abstract class MenuController implements UIController, Modeled {

    public abstract Pane getRoot();
    public abstract Pane getTopRoot();
    public MainMenuView getView() {
        return m_view;
    }
    public SnakeModel getModel() {return m_model;}

    private final MainMenuView  m_view;
    private final SnakeModel m_model;

    public MenuController(MainMenuView view, SnakeModel model){
        m_model = model;
        m_view = view;
    }

    @Override
    public void init(){
        for (Node node : getRoot().getChildren()){
            if(node instanceof Button){
                Animation<?> bobble = getView().setInfiniteBobble(node,0.25);
                bobble.pause();
                node.setOnMouseEntered(e->{
                    bobble.resume();
                });
                node.setOnMouseExited(e->{
                    bobble.pause();
                });
            }
        }
    }


}
