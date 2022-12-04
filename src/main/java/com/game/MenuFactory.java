package com.game;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.game.views.MainMenuView;

public class MenuFactory extends SceneFactory {

    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenuView(MenuType.MAIN_MENU);
    }
    
}