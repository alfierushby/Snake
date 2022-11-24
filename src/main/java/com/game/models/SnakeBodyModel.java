package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.game.enums.DIRECTION;
import com.game.events.SnakeEvent;

import java.util.LinkedList;
import java.util.Queue;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

public class SnakeBodyModel extends Component {
    public boolean setDirection(DIRECTION direction) {
        m_direction = direction;
        return true;
    }
    public boolean setSpeed(int speed) {
        m_speed = speed;
        return true;
    }
    public boolean setVelocity(Vec2 velocity) {
        m_velocity = velocity;
        return true;
    }
    public boolean setCurrentTime(long currentTime) {
        m_saved_time = currentTime;
        return true;
    }
    public Vec2 getVelocity() {return m_velocity;}
    public DIRECTION getDirection() {return m_direction;}
    public float getSpeed() {
        return m_speed;
    }
    public long getSavedtime() {return m_saved_time;}


    // FXGL Injected Variables!
    private PhysicsComponent m_physics; // This is from the factory
    private Entity m_entity;

    //Custom Variables
    private int m_speed = 5;
    private Vec2 m_velocity = new Vec2();
    private DIRECTION m_direction;
    private long m_saved_time = cpuNanoTime();
    public SnakeBodyModel(DIRECTION direction) {
        setDirection(direction);
        setCurrentTime(cpuNanoTime());
    }
    public void move()
    {
        if (getDirection() == UP)
        {
            getVelocity().set(0,getSpeed());
        } else if (getDirection() == DOWN)
        {
            getVelocity().set(0,-getSpeed());
        } else if (getDirection() == LEFT)
        {
            getVelocity().set(-getSpeed(),0);
        } else if (getDirection() == RIGHT)
        {
            getVelocity().set(getSpeed(),0);
        }
    }
}
