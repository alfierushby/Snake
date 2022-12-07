package com.game;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import com.almasb.fxgl.ui.UI;
import com.game.collisions.FoodSnakeHandler;
import com.game.collisions.SnakeBodyHandler;
import com.game.controllers.SnakeController;
import com.game.controllers.SnakeUIController;
import com.game.data.Score;
import com.game.models.SnakeModel;
import com.game.views.FoodView;
import com.game.views.SnakeView;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collections;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getEventBus;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
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
    public SnakeModel getModel() {return m_model;}

    public boolean setSnakeView(SnakeView snakeview) {
        m_snakeview = snakeview;
        return true;
    }
    public boolean setFoodView(FoodView foodview) {
        m_foodview = foodview;
        return true;
    }
    public boolean setModel(SnakeModel m_model) {
        this.m_model = m_model;
        return true;
    }

    private boolean resetModel(){
        boolean cond0 = setModel(new SnakeModel(DEFAULT_SNAKE_WIDTH,
                DEFAULT_SNAKE_HEIGHT,
                new Rectangle(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT)));
        if(getSaveLoadService().saveFileExists("scores.sav")){
            getSaveLoadService().readAndLoadTask("scores.sav").run();
        }
        return cond0;
    }

    private boolean resetGame(){
        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        getEventBus().removeAllEventHandlers();
        return resetModel();
    }

    Entity m_snake;
    SnakeFactory m_snakeFactory = new SnakeFactory();
    SnakeView m_snakeview;
    FoodView m_foodview;
    SnakeModel m_model;
    Bundle saved_data;


     @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(DEFAULT_GAME_WIDTH);
        settings.setHeight(DEFAULT_GAME_HEIGHT);
        settings.setTitle("The Snake Game");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        settings.setNative(false);
        settings.setSceneFactory(new MenuFactory());
    }
    @Override
    protected void onPreInit() {
         resetModel();
         getModel().setHighScores(new Bundle("highScores"));

         // Save and load the data:

        getSaveLoadService().addHandler(new SaveLoadHandler() {
            @Override
            public void onSave(@NotNull DataFile data) {
                System.out.println("lol???");
                // Get bundle from model
                Bundle bundle = getModel().getHighScores();

                // Save the bundle
                data.putBundle(bundle);
                System.out.println("uh...");
            }

            @Override
            public void onLoad(@NotNull DataFile data) {
                // Get the high_scores
                Bundle bundle = data.getBundle("highScores");
                // Set it in the model
                getModel().setHighScores(bundle);

            }
        });
    }
    @Override
    protected void initUI(){

        SnakeUIController uiController = new SnakeUIController(getGameScene());

        UI ui = getAssetLoader().loadUI(DEFAULT_GAME_UI,uiController);

        uiController.getLabelScore().textProperty().bind(getModel()
                .getScoreProp().asString("Score: %d"));

        getGameScene().addUI(ui);
    }


    private void showPlayAgain(){
        getDialogService().showConfirmationBox("Play " +
                "Again?", yes -> {
            if (yes) {
                resetGame();
                getGameController().startNewGame();
            } else{
                resetGame();
                getGameController().gotoMainMenu();
            }
        });
}
    private void endGame(){
         Consumer<String> result = new Consumer<String>() {
            @Override
            public void accept(String name) {
                getModel().addHighScore(name);
                getSaveLoadService().saveAndWriteTask("scores" +
                        ".sav").run();

                showPlayAgain();
            }
        };

        getDialogService().showInputBox("Please enter your" +
                " name to save your score to the leaderboard!",result);
    }
    @Override
    protected void initGame() {
        setSnakeView(new SnakeView(getSnakeFactory(),getModel()));
        setFoodView(new FoodView(getSnakeFactory(),getModel()));

         getModel().getStateProp().addListener(e ->{
             if(getModel().getState()){
                 getDialogService().showMessageBox("You scored : "
                         + getModel().getScore() + "!",this::endGame);
         }});
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new FoodSnakeHandler());
        getPhysicsWorld().addCollisionHandler(new SnakeBodyHandler(getModel()));
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
