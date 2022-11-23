package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.game.data.SnakeQueueItem;
import com.game.enums.DIRECTION;
import com.game.events.SnakeEvent;

import java.util.LinkedList;
import java.util.Queue;

import static com.almasb.fxgl.dsl.FXGL.onEvent;

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
    public Vec2 getVelocity() {return m_velocity;}
    public DIRECTION getDirection() {return m_direction;}
    public float getSpeed() {
        return m_speed;
    }


    // FXGL Injected Variables!
    private PhysicsComponent m_physics; // This is from the factory
    private Entity m_entity;

    //Custom Variables
    private int m_speed = 5;
    private Vec2 m_velocity = new Vec2();
    private DIRECTION m_direction;
    private Queue<SnakeQueueItem> m_directions = new LinkedList<> ();
    private void addDirection(SnakeEvent event){
        eve
    }
    @Override
    public void onUpdate(double tpf) {
        m_entity = entity; // Protected FXGL var, setting to follow conventions
        onEvent(SnakeEvent.NEW_DIRECTION, this::addDirection);
    }


}
