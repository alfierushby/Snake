package com.game.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.game.enums.TYPES;
import com.game.events.SnakeEvent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.fire;

/**
 * Handles the collisions of Food Entities and the Snake player.
 * @author Alfie Rushby
 */
public class FoodSnakeHandler extends CollisionHandler {
    /**
     * The order of types determines the order of entities in callbacks.
     */
    public FoodSnakeHandler() {
        super(TYPES.FOOD, TYPES.SNAKE);
    }

    /**
     * When a collision occurs, removes the Food and calls for a new Food
     * entity to be created.
     * @param food  first entity
     * @param snake second entity
     */
    @Override
    protected void onCollisionBegin(Entity food, Entity snake) {
        food.removeFromWorld(); // Make transition

        fire(new SnakeEvent(SnakeEvent.EAT_FOOD));
    }
}
