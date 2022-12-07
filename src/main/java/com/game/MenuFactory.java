package com.game;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.game.models.SnakeModel;
import com.game.views.MainMenuView;

public class MenuFactory extends SceneFactory {
    public SnakeModel getModel() {
        return m_model;
    }

    private final SnakeModel m_model;

    public MenuFactory(SnakeModel model) {
        m_model = model;
    }

    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenuView(MenuType.MAIN_MENU,getModel());
    }
    
}