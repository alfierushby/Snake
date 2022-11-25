package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.game.enums.DIRECTION;

import java.awt.*;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.cpuNanoTime;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.game.data.Config.*;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

public class SnakeModel extends Component {

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
    public boolean setMoveAmount(int[] moveAmount) {
        m_moveAmount = moveAmount;
        return true;
    }
    public boolean setDirectionMap(Map<DIRECTION, Integer> direction_map) {
        m_direction_map = direction_map;
        return true;
    }
    public boolean setHeight(int h) {
        m_h = h;
        return true;
    }
    public boolean setWidth(int w) {
        m_w = w;
        return true;
    }
    public boolean setLength(int length) {
        m_length = length;
        return true;
    }

    public boolean setState(boolean l) {
        m_l = l;
        return true;
    }

    public Vec2 getVelocity() {return m_velocity;}
    public DIRECTION getDirection() {return m_direction;}
    public float getSpeed() {return m_speed;}
    public long getSavedtime() {return m_saved_time;}
    public int[] getMoveAmount() {return m_moveAmount;}
    public int getMin() {return m_min;}
    public int getMax() {return m_max;}
    public int getNum() {return m_num;}
    public double getSpeedPixels() {return m_speed_pixels;}
    public int getWidth() {return m_w;}
    public int getHeight() {return m_h;}
    public int getLength() {return m_length;}
    public boolean getState() {
        return m_l;
    }
    public Map<DIRECTION, Integer> getDirectionMap() {return m_direction_map;}

    //Custom Variables
    private Map<DIRECTION, Integer> m_direction_map
            = Map.of(UP, -90, DOWN, 90, LEFT, -180, RIGHT, 0);
    private int m_speed = DEFAULT_SPEED; // In metres/second, converted to pixels later



    private final double m_speed_pixels;
    private final int m_num;
    private Vec2 m_velocity = new Vec2();
    private DIRECTION m_direction= DEFAULT_DIRECTION;
    private long m_saved_time = cpuNanoTime();
    private int[] m_moveAmount = {0,0};
    private final int m_min;
    private final int m_max;
    private int m_w;
    private int m_h;
    private boolean m_l;
    private int m_length;

    public SnakeModel(Rectangle container) {
        getVelocity().set(0,getSpeed()); // Default movement
        m_min = container.x;
        m_max = getMin() + container.width;
        // Pixels per default frame
        m_speed_pixels =
                getPhysicsWorld().toPixels(getSpeed())/DEFAULT_FRAME_RATE;
        // How many frames to move a width (it won't be exact)
        // But it will approach near-perfectness as frame rate increases
        m_num = (int) (getWidth() / getSpeedPixels());
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
