package com.game;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.game.collisions.FoodSnakeHandler;
import com.game.controllers.SnakeController;
import com.game.models.SnakeModel;
import com.game.views.FoodView;
import com.game.views.SnakeView;
import javafx.scene.input.KeyCode;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.game.data.Config.*;
import static com.game.data.Config.DEFAULT_GAME_HEIGHT;
import static com.game.enums.DIRECTION.*;

public class SnakeGameApplication extends GameApplication {

    public SnakeFactory getSnakeFactory() {
        return m_snakeFactory;
    }
    public SnakeView getSnakeView() {
        return m_snakeview;
    }
    public FoodView getFoodView() {return m_foodview;}
    public boolean setSnakeView(SnakeView snakeview) {
        m_snakeview = snakeview;
        return true;
    }
    public boolean setFoodView(FoodView foodview) {
        m_foodview = foodview;
        return true;
    }
    Entity m_snake;
    SnakeFactory m_snakeFactory = new SnakeFactory();
    SnakeView m_snakeview;
    FoodView m_foodview;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(DEFAULT_GAME_WIDTH);
        settings.setHeight(DEFAULT_GAME_HEIGHT);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        SnakeModel model = new SnakeModel(DEFAULT_SNAKE_WIDTH,
                DEFAULT_SNAKE_HEIGHT,
                new Rectangle(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT));
        setSnakeView(new SnakeView(getSnakeFactory(),model));
        setFoodView(new FoodView(getSnakeFactory(),model));
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new FoodSnakeHandler());
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
