package com.game.data;

import com.game.enums.DIRECTION;
import javafx.geometry.Point2D;

import static com.game.enums.DIRECTION.*;

public class Config {

public static final DIRECTION DEFAULT_DIRECTION = DOWN;
public static final int DEFAULT_SPEED = 5;
public static final double DEFAULT_FRAME_RATE = 120;
public static final int DEFAULT_GAME_WIDTH = 600;
public static final int DEFAULT_GAME_HEIGHT = 800;
public static final int DEFAULT_SNAKE_WIDTH = 25;
public static final int DEFAULT_SNAKE_HEIGHT = 25;

public static final Point2D DEFAULT_START_POSITION = new Point2D(0,0);
}
