package com.game.data;

import com.almasb.fxgl.core.collection.Array;
import com.game.enums.DIRECTION;
import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.game.enums.DIRECTION.*;

/**
 * Global Config for game
 */
public class Config {
public static final DIRECTION DEFAULT_DIRECTION = DOWN;
public static final int DEFAULT_SPEED = 5;
public static final double DEFAULT_FRAME_RATE = 120;
public static final int DEFAULT_GAME_WIDTH = 800;
public static final int DEFAULT_GAME_HEIGHT = 600;
public static final int DEFAULT_SNAKE_WIDTH = 25;
public static final int DEFAULT_SNAKE_HEIGHT = 25;
public static final int DEFAULT_SCORE_INCREMENT = 1;
public static final int DEFAULT_LENGTH_INCREMENT = 1;
public static final Point2D DEFAULT_START_POSITION = new Point2D(150,150);
public static final String DEFAULT_GAME_UI = "snake_ui.fxml";
public static final String DEFAULT_MAIN_UI = "main_menu.fxml";
public static final String DEFAULT_HIGH_SCORES_UI = "high_scores.fxml";
public static final String DEFAULT_OPTIONS_UI = "options.fxml";
public static final String DEFAULT_SAVE_BUNDLE_LIST = "scores";
public static final String DEFAULT_SAVE_BUNDLE_NAME = "highscores";
public static final String DEFAULT_SAVE_BUNDLE_FILE_NAME = "scores.sav";
public static final double DEFAULT_TRANSITION_LENGTH = .5;
public static final int DEFAULT_MAX_SAVED_SCORES = 100;
    public static final Map<String, String> DEFAULT_BACKGROUND_OPTIONS
            = Map.of("Jovial","UI-background.png",
            "Snake's Happiness", "back3.jpg",
            "Green Hell","wp3906260.jpg");
    public static final Map<String, String> DEFAULT_SNAKE_HEAD_OPTIONS
            = Map.of("Level Headed","snake-head-right.png",
            "Angry", "angry-snake-head.png");
    public static final Map<String, String> DEFAULT_SNAKE_BODY_OPTIONS
            = Map.of("Ball","snake-body.png");
    public static final Map<String, String> DEFAULT_DIFFICULTY_LEVELS
            = Map.of("Easy","level0",
            "Challenging", "level1",
            "Hard", "level2");

    public static final String DEFAULT_DIFFICULTY_EASY = "Easy";
    public static final String[] DEFAULT_DIFFICULTY_EXTRA_ORDER = {
            "Challenging","Hard"};

}
