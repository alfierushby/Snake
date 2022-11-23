package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.game.data.SnakeQueueItem;
import com.game.enums.DIRECTION;
import com.game.events.SnakeEvent;

import java.awt.*;
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
    private Queue<SnakeQueueItem> m_directions = new LinkedList<> ();
    private long m_saved_time = cpuNanoTime();
    public SnakeBodyModel(DIRECTION direction) {
        setDirection(direction);
        setCurrentTime(cpuNanoTime());
    }

    private boolean changeDirection(DIRECTION direction){
        setDirection(direction);
        move();
        return true
    }

    private boolean snakeLoop(){
        SnakeQueueItem wait_item;

        while((wait_item = m_directions.remove()) != null) {
            long adjusted_wait_time;
            if (wait_item.direction() == getDirection()) {
                long time_already_spent = cpuNanoTime() - m_saved_time;
                adjusted_wait_time = wait_item.end_time() - time_already_spent;
                if (adjusted_wait_time < 0) {return false;}
            } else {
                adjusted_wait_time = wait_item.end_time();
                changeDirection(wait_item.direction());
            }
            try {
                getEngineTimer().wait(adjusted_wait_time / 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            changeDirection(wait_item.end_direction());
        }
        return true;
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

    private void addDirection(SnakeEvent event){
        m_directions.add(event.getQueueItem());
        if(m_directions.size()==1) {
            snakeLoop();
        }
    }
    @Override
    public void onUpdate(double tpf) {
        m_entity = entity; // Protected FXGL var, setting to follow conventions
        onEvent(SnakeEvent.NEW_DIRECTION, this::addDirection);
    }


}
