package com.game.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.CollisionResult;
import com.game.Snake;
import com.game.enums.TYPES;
import com.game.events.SnakeEvent;
import com.game.models.SnakeModel;

import static com.almasb.fxgl.dsl.FXGLForKtKt.fire;

/**
 * Handles the collisions of Food Entities and the Snake player.
 */
public class SnakeBodyHandler extends CollisionHandler {
    /**
     * The order of types determines the order of entities in callbacks.
     */

    private final SnakeModel m_model;

    public SnakeBodyHandler(SnakeModel model) {
        super( TYPES.SNAKE, TYPES.SNAKE_BODY);
        m_model = model;
    }

    /**
     * When a collision occurs with itself, end the game.
     * @param body  first entity
     * @param snake second entity
     */
    @Override
    protected void onCollisionBegin(Entity snake, Entity body) {
        m_model.setState(true);

    }
}