package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class SnakeModel extends Component {

    private PhysicsComponent physics; // This is from the factory

    private Vec2 velocity = new Vec2();
    private float speed = 5;
    private static final float BOUNCE_FACTOR = 1.5f;

    @Override
    public void onUpdate(double tpf) {
       // velocity.mulLocal(SPEED_DECAY);

        if (entity.getX() < 0) {
            velocity.set(BOUNCE_FACTOR * (float) -entity.getX(), 0);
        } else if (entity.getRightX() > getAppWidth()) {
            velocity.set(BOUNCE_FACTOR * (float) -(entity.getRightX() - getAppWidth()), 0);
        }

        physics.setBodyLinearVelocity(velocity);
    }



    public void left() {
        velocity.set(-speed, 0);
    }

    public void right() {
        velocity.set(speed, 0);
    }
    public void up() {
        velocity.set(0, speed);
    }
    public void down() {
        velocity.set(0, -speed);
    }

}
