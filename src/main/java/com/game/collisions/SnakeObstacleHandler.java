package com.game.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.game.enums.TYPES;
import com.game.models.SnakeModel;

public class SnakeObstacleHandler extends CollisionHandler {
    private final SnakeModel m_model;

    public SnakeObstacleHandler(SnakeModel model) {
        super( TYPES.SNAKE, TYPES.OBSTACLE);
        m_model = model;
    }

    /**
     * When a collision occurs with itself, end the game.
     * @param snake  first entity
     * @param obstacle second entity
     */
    @Override
    protected void onCollisionBegin(Entity snake, Entity obstacle) {
        m_model.setState(true);
    }
}
