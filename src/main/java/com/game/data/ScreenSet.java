package com.game.data;

import com.game.controllers.HighScoreController;
import com.game.controllers.MainMenuController;
import com.game.controllers.MenuController;
import com.game.controllers.OptionsController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class ScreenSet {

    private final List<MenuController> m_controllers;
    private final Pane m_parent;

    public MainMenuController getMainMenu(){
        return (MainMenuController) m_controllers.get(0);
    }
    public HighScoreController getHighScores(){
        return (HighScoreController) m_controllers.get(1);
    }
    public OptionsController getOptions(){
        return (OptionsController) m_controllers.get(2);
    }

    public ScreenSet(@NotNull MainMenuController main_menu,
                     @NotNull HighScoreController high_scores,
                     @NotNull OptionsController options,
                     @NotNull Pane parent){
        m_parent = parent;
        m_controllers = new LinkedList<>();
        m_controllers.add(0, main_menu);
        m_controllers.add(1, high_scores);
        m_controllers.add(2, options);
    }

    public boolean hideAll(){
        for(MenuController controller : m_controllers){
            Pane top = controller.getTopRoot();
            top.setOpacity(0);
            top.setMouseTransparent(true);
         //  m_parent.getChildren().remove(top);

        }
        return true;
    }
}
