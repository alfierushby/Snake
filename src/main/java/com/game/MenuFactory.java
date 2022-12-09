package com.game;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;

/**
 * Factory used to create the Main Menu on startup.
 */
public class MenuFactory extends SceneFactory implements Modeled {
    /**
     * @return The Snake Model of the game
     */
    public SnakeModel getModel() {
        return m_model;
    }

    private final SnakeModel m_model;

    /**
     * Sets the snake model.
     * @param model Snake model of the game.
     */
    public MenuFactory(SnakeModel model) {
        m_model = model;
    }

    /**
     * Starts the Main Menu UI.
     * @return The FXGLMenu MainMenu used by FXGL at startup to show the Main
     * Menu.
     */
    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenuView(MenuType.MAIN_MENU,getModel());
    }
    
}