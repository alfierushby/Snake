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
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.FoodView;
import com.game.views.SnakeView;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.LinkedList;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getEventBus;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;
import static com.game.data.Config.DEFAULT_GAME_HEIGHT;
import static com.game.enums.DIRECTION.*;

public class SnakeGameApplication extends GameApplication implements Modeled {

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
        return getSnakeView().resetGame();
    }

    private boolean loadData(){
        if(getSaveLoadService().saveFileExists(DEFAULT_SAVE_BUNDLE_FILE_NAME)){
            getSaveLoadService().readAndLoadTask(DEFAULT_SAVE_BUNDLE_FILE_NAME)
                    .run();
            return true;
        }
        return false;
    }

    private boolean resetGame(){
        //getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        getEventBus().removeAllEventHandlers();
        return resetModel();
    }

    /**
     * Initialises the model. Should only ever be called once.
     * @return true if the model was initialized
     */
    private boolean initModel(){
        if (getModel()==null){
            return setModel(new SnakeModel(DEFAULT_SNAKE_WIDTH,
                    DEFAULT_SNAKE_HEIGHT,
                    new Rectangle(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT)));
        }
        System.out.println("Tried to initialize an existing model");
        return false;
    }

    Entity m_snake;
    SnakeFactory m_snakeFactory = new SnakeFactory();
    SnakeView m_snakeview;
    FoodView m_foodview;
    SnakeModel m_model;
    Bundle saved_data;


     @Override
    protected void initSettings(GameSettings settings) {
         initModel();
        settings.setWidth(DEFAULT_GAME_WIDTH);
        settings.setHeight(DEFAULT_GAME_HEIGHT);
        settings.setTitle("The Snake Game");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        settings.setNative(false);
        settings.setSceneFactory(new MenuFactory(getModel()));
    }
    @Override
    protected void onPreInit() {
         getModel().calcFrameSpeed();
         setupAutoSaving();
         // Save and load the data:

        getSaveLoadService().addHandler(new SaveLoadHandler() {
            @Override
            public void onSave(@NotNull DataFile data) {
                // Get bundle from model
                Bundle bundle = getModel().getHighScores();
                LinkedList<String> images = new LinkedList<>();
                images.add(getModel().getBackground());
                images.add(getModel().getSnakeHead());
                images.add(getModel().getSnakeBody());
                bundle.put("images",images);

                // Save the bundle
                data.putBundle(bundle);
            }

            @Override
            public void onLoad(@NotNull DataFile data) {
                // Get the high_scores
                Bundle bundle = data.getBundle(DEFAULT_SAVE_BUNDLE_NAME);
                getModel().setHighScores(bundle);
                LinkedList<String> paths = bundle.get("images");
                if(paths == null){
                    return;
                }
                // Set it in the model
                if( paths.size()==3){
                    getModel().setBackground(paths.get(0));
                    getModel().setSnakeHead(paths.get(1));
                    getModel().setSnakeBody(paths.get(2));
                }


            }
        });
        loadData();
    }
    @Override
    protected void initUI(){

        SnakeUIController uiController = new SnakeUIController(getGameScene());
        UI ui = getAssetLoader().loadUI(DEFAULT_GAME_UI,uiController);

        uiController.getLabelScore().textProperty().bind(getModel()
                .getScoreProp().asString("Score: %d"));
        getGameScene().addUI(ui);
    }

    private boolean setupAutoSaving(){
         if(getModel()==null){
             System.out.println("Model not initialized for auto saving!");
             return false;
         }
        getModel().getSnakeBodyPathProp().addListener(e->{
            getSaveLoadService().saveAndWriteTask(
                    DEFAULT_SAVE_BUNDLE_FILE_NAME).run();
        });

        getModel().getSnakeHeadPathProp().addListener(e->{
            getSaveLoadService().saveAndWriteTask(
                    DEFAULT_SAVE_BUNDLE_FILE_NAME).run();
        });

        getModel().getBackgroundPathProp().addListener(e->{
            getSaveLoadService().saveAndWriteTask(
                    DEFAULT_SAVE_BUNDLE_FILE_NAME).run();
        });
        return true;
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
                getSaveLoadService().saveAndWriteTask(
                        DEFAULT_SAVE_BUNDLE_FILE_NAME).run();

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
