package com.game.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.game.enums.TYPES;
import com.game.events.SnakeEvent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.fire;

public class FoodSnakeHandler extends CollisionHandler {
    /**
     * The order of types determines the order of entities in callbacks.
     */
    public FoodSnakeHandler() {
        super(TYPES.FOOD, TYPES.SNAKE);
    }

    @Override
    protected void onCollisionBegin(Entity food, Entity snake) {

        food.removeFromWorld(); // Make transition

        fire(new SnakeEvent(SnakeEvent.EAT_FOOD));
    }
}
