package com.game.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.game.enums.TYPES;
import com.game.events.SnakeEvent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.fire;
import static com.almasb.fxgl.dsl.FXGLForKtKt.random;
import static com.game.data.Config.*;

/**
 * Handles the situations where the Food spawns in an obstacle. If so, it
 * moves it to another location.
 */
public class FoodObstacleHandler extends CollisionHandler {

    /**
     * The order of types determines the order of entities in callbacks.
     */
    public FoodObstacleHandler() {
        super(TYPES.FOOD, TYPES.OBSTACLE);
    }

    /**
     * When food is spawned on an obstacle, change its position.
     * @param food  first entity
     * @param obstacle second entity
     */
    @Override
    protected void onCollisionBegin(Entity food, Entity obstacle) {
        food.setPosition(new Point2D(
                random(DEFAULT_SNAKE_WIDTH,
                        DEFAULT_GAME_WIDTH-DEFAULT_SNAKE_WIDTH),
                random(DEFAULT_SNAKE_HEIGHT
                        ,DEFAULT_GAME_HEIGHT-DEFAULT_SNAKE_HEIGHT)));
    }
}
