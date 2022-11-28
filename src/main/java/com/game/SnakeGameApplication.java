package com.game;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.game.controllers.SnakeController;
import com.game.views.SnakeView;
import javafx.scene.input.KeyCode;

import static com.game.enums.DIRECTION.*;

public class SnakeGameApplication extends GameApplication {

    public SnakeFactory getSnakeFactory() {
        return m_snakeFactory;
    }
    public SnakeView getSnakeView() {
        return m_snakeview;
    }
    public boolean setSnakeView(SnakeView m_snakeview) {
        this.m_snakeview = m_snakeview;
        return true;
    }
    Entity m_snake;
    SnakeFactory m_snakeFactory = new SnakeFactory();



    SnakeView m_snakeview;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        setSnakeView(new SnakeView(getSnakeFactory()));
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getSnakeView().getSnake()
                        .getComponent(SnakeController.class).keyPressed(RIGHT);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getSnakeView().getSnake()
                        .getComponent(SnakeController.class).keyPressed(LEFT);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getSnakeView().getSnake()
                        .getComponent(SnakeController.class).keyPressed(DOWN);;
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getSnakeView().getSnake()
                        .getComponent(SnakeController.class).keyPressed(UP);
            }
        }, KeyCode.W);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
