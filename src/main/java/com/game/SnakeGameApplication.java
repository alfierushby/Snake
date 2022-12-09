package com.game;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import com.almasb.fxgl.ui.UI;
import com.game.collisions.FoodObstacleHandler;
import com.game.collisions.FoodSnakeHandler;
import com.game.collisions.SnakeBodyHandler;
import com.game.collisions.SnakeObstacleHandler;
import com.game.controllers.SnakeController;
import com.game.controllers.SnakeUIController;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import com.game.views.FoodView;
import com.game.views.SnakeView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getEventBus;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;
import static com.game.data.Config.DEFAULT_GAME_HEIGHT;
import static com.game.enums.DIRECTION.*;

/**
 * FXGL Snake Game application, manages the game and user input for the game.
 */
public class SnakeGameApplication extends GameApplication implements Modeled {

    /**
     * @return Snake Factory for creating entities.
     */
    public SnakeFactory getSnakeFactory() {
        return m_snakeFactory;
    }

    /**
     * @return Snake Game View for managing the snake and its body parts.
     */
    public SnakeView getSnakeView() {
        return m_snakeview;
    }

    /**
     * @return Snake Model of the game
     */
    public SnakeModel getModel() {return m_model;}

    /**
     * @param snakeview View of the game
     * @return true
     */
    public boolean setSnakeView(SnakeView snakeview) {
        m_snakeview = snakeview;
        return true;
    }
    /**
     * @param foodview View of the Food.
     * @return true
     */
    public boolean setFoodView(FoodView foodview) {
        m_foodview = foodview;
        return true;
    }

    /**
     * @param model Snake model of the game
     * @return true
     */
    public boolean setModel(SnakeModel model) {
        m_model = model;
        return true;
    }

    /**
     * Loads the save data in the default save location.
     * @return true if the data was able to load.
     */
    private boolean loadData(){
        if(getSaveLoadService().saveFileExists(DEFAULT_SAVE_BUNDLE_FILE_NAME)){
            getSaveLoadService().readAndLoadTask(DEFAULT_SAVE_BUNDLE_FILE_NAME)
                    .run();
            return true;
        }
        return false;
    }

    /**
     * Resets the game by removing all events and resetting the model.
     * @return true if the game was reset
     */
    private boolean resetGame(){
        getEventBus().removeAllEventHandlers();
        return getSnakeView().resetGame();
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


    /**
     * Sets the width, name, and makes it so that the Main Menu is displayed
     * on startup by default.
     * @param settings Settings of the FXGL game
     */
     @Override
    protected void initSettings(GameSettings settings) {
         initModel();
        settings.setWidth(DEFAULT_GAME_WIDTH);
        settings.setHeight(DEFAULT_GAME_HEIGHT);
        settings.setTitle("The Snake Game");
        settings.setVersion("v1.0");
        settings.setMainMenuEnabled(true);
        settings.setNative(false);
        settings.setSceneFactory(new MenuFactory(getModel()));
     }

    /**
     * Sets up the model's data that requires FXGL physics and from save data,
     * and adds the handlers for saving and loading, called whenever a load or
     * save request is made.
     */
    @Override
    protected void onPreInit() {
         getModel().calcFrameSpeed();
         setupAutoSaving();
        getSettings().setGlobalMusicVolume(.25);
         loopBGM("frog.mp3");
         // Save and load the data:

        getSaveLoadService().addHandler(new SaveLoadHandler() {
            /**
             * Saves the highscores, theme data, and the player's name.
             * @param data Data that will be saved
             */
            @Override
            public void onSave(@NotNull DataFile data) {
                // Get bundle from model
                Bundle bundle = getModel().getHighScores();
                LinkedList<String> images = new LinkedList<>();
                images.add(getModel().getBackground());
                images.add(getModel().getSnakeHead());
                images.add(getModel().getSnakeBody());
                bundle.put("images",images);
                bundle.put("name",getModel().getPlayerName());

                // Save the bundle
                data.putBundle(bundle);
            }
            /**
             * Attempts to load the highscores, theme data, and the
             * player's name.
             * @param data Data that will be loaded
             */
            @Override
            public void onLoad(@NotNull DataFile data) {
                // Get the high_scores
                Bundle bundle = data.getBundle(DEFAULT_SAVE_BUNDLE_NAME);
                getModel().setHighScores(bundle);

                //Set Name
                String name = bundle.get("name");
                getModel().setPlayerName(name);

                // Set Theme
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

    /**
     * Intiates the Game UI by loading the {@link SnakeUIController},
     * that being the Snake Score label.
     */
    @Override
    protected void initUI(){

        SnakeUIController uiController = new SnakeUIController(getModel());
        UI ui = getAssetLoader().loadUI(DEFAULT_GAME_UI,uiController);

        getGameScene().addUI(ui);
    }

    /**
     * Sets up auto saving whenever any Option Property (Background, Snake
     * Body and Head images) is changed, by listening to the model.
     * @return true if the Snake Model is initialized
     */
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

    /**
     * Shows an option to play the game again.
     */
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

    /**
     * Called when a game ends, saving the player's score to the high-score
     * leaderboard.
     */
    private void endGame(){
        getModel().addHighScore(getModel().getPlayerName());
        getSaveLoadService().saveAndWriteTask(
                DEFAULT_SAVE_BUNDLE_FILE_NAME).run();

        showPlayAgain();
    }

    /**
     * Called when the Snake Game starts (after clicking new game), and it
     * sets the corresponding level based on selected difficulty, and then adds
     * a listener that wait for the game to end, showing how much they scored.
     */
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(getSnakeFactory());
        FXGL.setLevelFromMap(
                "tmx/"+DEFAULT_DIFFICULTY_LEVELS.get(getModel().getDifficulty())
                        +".tmx");

        setSnakeView(new SnakeView(getSnakeFactory(),getModel()));
        setFoodView(new FoodView(getSnakeFactory(),getModel()));

         getModel().getStateProp().addListener(e ->{
             if(getModel().getState()){
                 getDialogService().showMessageBox("You scored : "
                         + getModel().getScore() + "!",this::endGame);
         }});
    }

    /**
     * Initiates the collision handlers.
     */
    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new FoodSnakeHandler());
        getPhysicsWorld().addCollisionHandler(new SnakeBodyHandler(getModel()));
        getPhysicsWorld().addCollisionHandler(new FoodObstacleHandler());
        getPhysicsWorld().addCollisionHandler(
                new SnakeObstacleHandler(getModel()));
    }

    /**
     * Handles the input to the snake, defaulting to WASD.
     */
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

    /**
     * Launches the JavaFX application.
     * @param args Args of main
     */
    public static void main(String[] args) {
        launch(args);
    }

}
