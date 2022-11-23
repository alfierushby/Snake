package com.game;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.game.enums.DIRECTION;
import com.game.models.SnakeModel;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.enums.DIRECTION.*;

public class SnakeGameApplication extends GameApplication {

    Entity m_snake;
    SnakeFactory m_snakeFactory = new SnakeFactory();
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        m_snake = m_snakeFactory.newSnake(new SpawnData(100,100));
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                m_snake.getComponent(SnakeModel.class).keyPressed(RIGHT);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                m_snake.getComponent(SnakeModel.class).keyPressed(LEFT);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                m_snake.getComponent(SnakeModel.class).keyPressed(DOWN);;
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                m_snake.getComponent(SnakeModel.class).keyPressed(UP);
            }
        }, KeyCode.W);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
