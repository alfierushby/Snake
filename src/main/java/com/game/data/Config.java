package com.game.data;

import com.game.enums.DIRECTION;
import javafx.geometry.Point2D;

import java.util.Map;

import static com.game.enums.DIRECTION.*;
import static java.util.Map.entry;

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
public static final Point2D DEFAULT_START_POSITION = new Point2D(100,100);
public static final String DEFAULT_GAME_UI = "snake_ui.fxml";
public static final String DEFAULT_MAIN_UI = "main_menu.fxml";


}
