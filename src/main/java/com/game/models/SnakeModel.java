package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.game.enums.DIRECTION;
import com.game.events.SnakeEvent;
import javafx.geometry.Point2D;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.game.data.Config.*;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

public class SnakeModel {

    public boolean setDirection(DIRECTION direction) {
        boolean error = false;
        switch (direction)
        {
            case UP:
                if (getDirection()==DOWN) {
                    error = true;
                }
                break;
            case DOWN:
                if (getDirection()==UP) {
                    error = true;
                }
                break;
            case LEFT:
                if (getDirection()==RIGHT) {
                    error=true;
                }
                break;
            case RIGHT:
                if (getDirection()==LEFT) {
                    error = true;
                }
            default:
                break;
        }
        if (error){
            System.out.println("Tried to assign " + direction
                    + " when direction is " + getDirection());
            return false;
        }
        m_direction = direction;
        return true;
    }
    public boolean setSpeed(int speed) {
        if(speed < 0 || speed > 100){
            System.out.println("Trying to set negative or absurd speed!");
            return false;
        }
        m_speed = speed;
        return true;
    }
    public boolean setVelocity(Vec2 velocity) {
        if(velocity.x > 100 || velocity.y > 100){
            System.out.println("Trying to set absurd velocity!");
            return false;
        }
        m_velocity = velocity;
        return true;
    }
    public boolean setCurrentTime(long currentTime) {
        m_saved_time = currentTime;
        return true;
    }
    public boolean setDirectionMap(Map<DIRECTION, Integer> direction_map) {
        m_direction_map = direction_map;
        return true;
    }
    public boolean setSnakeHeight(double h) {
        if(h < 0){
            System.out.println("Trying to set negative snake height!");
            return false;
        }
        m_s_h = h;
        return true;
    }
    public boolean setSnakeWidth(double w) {
        if(w < 0){
            System.out.println("Trying to set negative snake width!");
            return false;
        }
        m_s_w = w;
        return true;
    }
    public boolean setLength(int length) {
        if (length < getLength()){
            System.out.println("Cannot set a lesser length!");
            return false;
        }
        m_length = length;
        fire(new SnakeEvent(SnakeEvent.MAKE_SNAKE_BODY));
        return true;
    }

    public boolean setState(boolean l) {
        m_l = l;
        return true;
    }
    public boolean setPosition(Point2D pos){
        m_position = pos;
        return true;
    }
    public boolean setFoodCount(int food_count) {
        if(food_count < 0){
            System.out.println("Trying to set negative food count!");
            return false;
        }
        m_food_count = food_count;
        return true;
    }
    public boolean setScore(int score) {
        if(score < 0){
            System.out.println("Trying to set negative score!");
            return false;
        }
        m_score = score;
        return true;
    }
    public int getScore() {return m_score;}
    public int getFoodCount() {return m_food_count;}
    public Vec2 getVelocity() {return m_velocity;}
    public DIRECTION getDirection() {return m_direction;}
    public float getSpeed() {return m_speed;}
    public long getSavedtime() {return m_saved_time;}
    public double getHeight() {return m_h;}
    public double getWidth() {return m_w;}
    public int getNum() {return m_num;}
    public double getSpeedPixels() {return m_speed_pixels;}
    public double getSnakeWidth() {return m_s_w;}
    public double getSnakeHeight() {return m_s_h;}
    public int getLength() {return m_length;}
    public boolean getState() {return m_l;}
    public Point2D getPosition() {return m_position;}
    public List<Point2D> getBodyPoints() {return m_bodyPoints;}
    public int getFrameTime() {
        return (int) (1000/DEFAULT_FRAME_RATE);
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
    final private double m_w;
    final private double m_h;
    private double m_s_w;
    private double m_s_h;
    private boolean m_l;
    private int m_length;
    private Point2D m_position;
    private final List<Point2D> m_bodyPoints = new LinkedList<>();
    private int m_food_count = 0;
    private int m_score = 0;

    public boolean addScore(){
        return setScore(getScore() + DEFAULT_SCORE_INCREMENT);
    }
    public boolean addLength(){
        return setLength(getLength() + DEFAULT_LENGTH_INCREMENT);
    }

    public SnakeModel(double width, double height,Rectangle container) {
        getVelocity().set(0,getSpeed()); // Default movement
        m_w = container.getWidth();
        m_h = container.getHeight();
        setSnakeWidth(width);
        setSnakeHeight(height);
        // Pixels per default frame
        m_speed_pixels =
                getPhysicsWorld().toPixels(getSpeed())/DEFAULT_FRAME_RATE;
        // How many frames to move a width (it won't be exact)
        // But it will approach near-perfectness as frame rate increases
        m_num = (int) Math.round(getSnakeWidth() / getSpeedPixels());
        setPosition(DEFAULT_START_POSITION);
        System.out.println("speed: " + getSnakeWidth() + " " + getSpeedPixels() +
                " " + getSnakeWidth() / getSpeedPixels());

    }

    public void draw()
    {
        outofBounds(getPosition().getX(), getPosition().getY());
        getBodyPoints().add(new Point2D(getPosition().getX(),
                getPosition().getY()));
        if ( getBodyPoints().size() == (getLength() + 1) * getNum())
        {
            getBodyPoints().remove(0);
        }
        //move();
        setPosition(new Point2D(
                getPosition().getX()+getSpeedPixels()*getVelocity().x,
                getPosition().getY()-getSpeedPixels()*getVelocity().y));
        //System.out.println("uh?" + getPosition().getX());
    }
    public boolean outofBounds(double x , double y)
    {
        boolean xOut = (x <= 0 || y >= (870 - getWidth()));
        boolean yOut = (y <= 40 || y >= (560 - getHeight()));
        if (xOut || yOut)
        {
            setState(false);
        }
        return true;
    }


    public void move()
    {
        if (getDirection() == UP)
        {
            getVelocity().set(0,1);
        } else if (getDirection() == DOWN)
        {
            getVelocity().set(0,-1);
        } else if (getDirection() == LEFT)
        {
            getVelocity().set(-1,0);
        } else if (getDirection() == RIGHT)
        {
            getVelocity().set(1,0);
        }
    }


}
