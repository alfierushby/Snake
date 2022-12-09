package com.game.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.game.enums.TYPES;
import com.game.models.SnakeModel;

/**
 * Handles the collisions of Food Entities and the Snake player.
 * @author Alfie Rushby
 */
public class SnakeBodyHandler extends CollisionHandler {

    private final SnakeModel m_model;

    /**
     * The order of types determines the order of entities in callbacks.
     */
    public SnakeBodyHandler(SnakeModel model) {
        super( TYPES.SNAKE, TYPES.SNAKE_BODY);
        m_model = model;
    }

    /**
     * When a collision occurs with itself, end the game.
     * @param snake  first entity
     * @param body second entity
     */
    @Override
    protected void onCollisionBegin(Entity snake, Entity body) {
        m_model.setState(true);

    }
}
