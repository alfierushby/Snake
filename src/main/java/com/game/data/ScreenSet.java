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

/**
 * A set of Menu controllers, used by any MenuController.
 */
public class ScreenSet {

    /**
     * @return Instantiated MainMenuController for use
     */
    public MainMenuController getMainMenu(){
        return (MainMenuController) m_controllers.get(0);
    }

    /**
     * @return Instantiated HighScoreController for use
     */
    public HighScoreController getHighScores(){
        return (HighScoreController) m_controllers.get(1);
    }

    /**
     * @return Instantiated OptionsController for use
     */
    public OptionsController getOptions(){
        return (OptionsController) m_controllers.get(2);
    }

    /**
     * @return all Menu controllers in a list
     */
    public List<MenuController> getControllers(){
        return m_controllers;
    }

    private final List<MenuController> m_controllers;
    private final Pane m_parent;


    /**
     * @param main_menu Instantiated MainMenuController
     * @param high_scores Instantiated HighScoreController
     * @param options Instantiated OptionsController
     * @param parent Parent of all the Menus.
     */
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

    /**
     * Loops through the pane that contains the Menu's, and hides them all,
     * setting mouse transparency.
     * @return true
     */
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
