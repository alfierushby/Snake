package com.game.models;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.core.serialization.Bundle;
import com.game.data.Score;
import com.game.enums.DIRECTION;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;


import java.util.*;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.game.data.Config.*;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

/**
 * Snake Model is the game's MVC model of the Snake's movement that handles
 * The positions of its body  and head, direction, and velocity. It also
 * contains game data.
 * @author Alfie Rushby-modified
 */
public class SnakeModel {

    /**
     * @param direction Direction to change to
     * @return True if the direction is plausible
     */
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

    /**
     * Sets speed and re-calulates frame speeds.
     * @param speed Speed of the snake in m/s
     * @return True if speed is not absurd and frame speeds were
     * calculated
     */
    public boolean setSpeed(int speed) {
        if(speed < 0 || speed > 100){
            System.out.println("Trying to set negative or absurd speed!");
            return false;
        }
        m_speed = speed;
        return calcFrameSpeed(); // New speed entails new frame speeds.
    }

    /**
     * @param velocity Unit Vector Velocity of Snake in form (0-1,0-1), where
     * 1 is full-speed
     * @return True if velocity isn't absurd
     */
    public boolean setVelocity(Vec2 velocity) {
        if(velocity.x > 1 || velocity.y > 1 || velocity.x < 0 || velocity.y < 0)
        {
            System.out.println("Trying to set non-binary velocity!");
            return false;
        }
        m_velocity = velocity;
        return true;
    }

    /**
     * @param h Height of snake head
     * @return True if the height isn't negative
     */
    public boolean setSnakeHeight(double h) {
        if(h < 0){
            System.out.println("Trying to set negative snake height!");
            return false;
        }
        m_s_h = h;
        return true;
    }

    /**
     * @param w Width of snake head
     * @return True if the width isn't negative
     */
    public boolean setSnakeWidth(double w) {
        if(w < 0){
            System.out.println("Trying to set negative snake width!");
            return false;
        }
        m_s_w = w;
        return true;
    }

    /**
     * @param length Length of snake, each unit pertaining to 1 body part
     * @return True if the length set was higher than the previous length
     */
    public boolean setLength(int length) {
        if (length < getLength()){
            System.out.println("Cannot set a lesser length!");
            return false;
        }
        m_length.set(length);
        return true;
    }

    /**
     * Resets the length of the snake
     * @return true if there exists a body point list
     */
    public boolean resetLength(){
        if(getBodyPoints() == null){
            System.out.println("No body points!");
            return false;
        }
        m_length.set(0);
        getBodyPoints().clear();
        return true;
    }

    /**
     * @param l State of snake, True for game end
     * @return True
     */
    public boolean setState(boolean l) {
        m_l.set(l);
        return true;
    }

    /**
     * @param pos Point to set the Position of the Snake Head
     * @return False if trying to move in a negative position
     */
    public boolean setPosition(Point2D pos){
        if(pos.getX() < 0 || pos.getY() < 0) {
            System.out.println("Cannot set a negative position!");
            return false;
        }
        m_position = pos;
        return true;
    }

    /**
     * @param food_count Number of food items in game
     * @return True if food_count isn't negative
     */
    public boolean setFoodCount(int food_count) {
        if(food_count < 0){
            System.out.println("Trying to set negative food count!");
            return false;
        }
        m_food_count = food_count;
        return true;
    }

    /**
     * @param score Score of game
     * @return True if score isn't negative
     */
    public boolean setScore(int score) {
        if(score < 0){
            System.out.println("Trying to set negative score!");
            return false;
        }
        m_score.set(score);
        return true;
    }

    /**
     * @param s Speed of snake in pixels/s
     * @return True if speed isn't negative
     */
    public boolean setSpeedPixels(double s) {
        if(s < 0){
            System.out.println("Trying to set negative speed!");
            return false;
        }
        m_speed_pixels = s;
        return true;
    }

    /**
     * @param num How many pixels to travel per frame
     * @return True if num isn't negative
     */
    public boolean setNum(int num) {
        if(m_num < 0){
            System.out.println("Trying to set negative num!");
            return false;
        }
        m_num = num;
        return true;
    }

    /**
     * @param m_high_scores The bundle to set the high_score data to
     * @return True if the bundle is for high scores.
     */
    public boolean setHighScores(Bundle m_high_scores) {
        if(m_high_scores.getName().equals(DEFAULT_SAVE_BUNDLE_NAME)){
            this.m_high_scores = m_high_scores;
            // Set it to an empty list
            if(getHighScores().getData().isEmpty()){
                getHighScores().put(DEFAULT_SAVE_BUNDLE_LIST,
                        new LinkedList<Score>());
            }
            return true;
        }
        System.out.println("Trying to set wrong data bundle!");
        return false;

    }

    /**
     * @param background_path Background Path key, used in conjunction with a
         map, that maps to a path relative to /Assets/Textures.
     * @return true
     */
    public boolean setBackground(String background_path) {
        m_background_path.set(background_path);
        return true;
    }

    /**
     * @param snake_head_path Background Path key, used in conjunction with a
        map, that maps to a path relative to /Assets/Textures.
     * @return true
     */
    public boolean setSnakeHead(String snake_head_path) {
        m_snake_head_path.set(snake_head_path);
        return true;
    }

    /**
     * @param snake_body_path Background Path key, used in conjunction with a
         map, that maps to a path relative to /Assets/Textures.
     * @return true
     */
    public boolean setSnakeBody(String snake_body_path) {
        m_snake_body_path.set(snake_body_path);
        return true;
    }

    public boolean setPlayerName(String player_name) {
        m_player_name.set(player_name);
        return true;
    }

    /**
     * @param difficulty Difficult of game, either "Easy", "Challenging",
     *                     or "Hard"
     * @return true if any of the applicable difficulties.
     */
    public boolean setDifficulty(String difficulty) {
        if(DEFAULT_DIFFICULTY_LEVELS.get(difficulty)==null){
            System.out.println("Trying to set non-applicable difficulty!");
            return false;
        }
        m_difficulty = difficulty;
        return true;
    }

    /**
     * @return String property of snake head path map
     */
    public StringProperty getSnakeHeadPathProp() {
        return m_snake_head_path;
    }

    /**
     * @return String property of snake body path map
     */
    public StringProperty getSnakeBodyPathProp() {
        return m_snake_body_path;
    }

    /**
     * @return String property of background path map
     */
    public StringProperty getBackgroundPathProp() {
        return m_background_path;
    }

    /**
     * @return Score value of game
     */
    public int getScore() {return m_score.get();}
    /**
     * @return Score Property of game
     */
    public IntegerProperty getScoreProp() {return m_score;}


    /**
     * @return Food entities spawned
     */
    public int getFoodCount() {return m_food_count;}

    /**
     * @return Unit vector of Velocity, in range 0 - 1, where 1 is its full
     * speed. Set by {@link #getSpeed()}.
     */
    public Vec2 getVelocity() {return m_velocity;}

    /**
     * @return Direction the snake is moving
     */
    public DIRECTION getDirection() {return m_direction;}

    /**
     * @return Speed of snake in m/s
     */
    public float getSpeed() {return m_speed;}

    /**
     * @return Height of game container in pixels
     */
    public double getHeight() {return m_h;}

    /**
     * @return Width of game container in pixels
     */
    public double getWidth() {return m_w;}

    /**
     * @return Numbers of pixels to travel per frame
     */
    public int getNum() {return m_num;}

    /**
     * @return Number of pixels travelled per second
     */
    public double getSpeedPixels() {return m_speed_pixels;}

    /**
     * @return Width of snake in pixels
     */
    public double getSnakeWidth() {return m_s_w;}

    /**
     * @return Height of snake in pixels
     */
    public double getSnakeHeight() {return m_s_h;}

    /**
     * @return Length of snake, with each unit being 1 new snake body part
     */
    public int getLength() {return m_length.get();}

    /**
     * @return Length property, for listening purposes
     */
    public IntegerProperty getLengthProp() {return m_length;}

    /**
     * @return State of game
     */
    public boolean getState() {return m_l.get();}

    /**
     * @return State of game Property
     */
    public BooleanProperty getStateProp() {return m_l;}

    /**
     * @return Position of snake
     */
    public Point2D getPosition() {return m_position;}

    /**
     * @return All body points used to position body parts in
     * {@link com.game.views.SnakeView}
     */
    public List<Point2D> getBodyPoints() {return m_bodyPoints;}

    /**
     * @return Gets the frame time according to the frame rate
     */
    public int getFrameTime() {
        return (int) (1000/DEFAULT_FRAME_RATE);
    }

    /**
     * @return Gets the mapping of direction to degrees
     */
    public Map<DIRECTION, Integer> getDirectionMap() {return m_direction_map;}

    /**
     * @return High Scores bundle from save
     */
    public Bundle getHighScores() {return m_high_scores;}

    /**
     * @return Non-translated Background Key value for mapping in
     * DEFAULT_BACKGROUND_OPTIONS
     */
    public String getBackground() {
        return m_background_path.get();
    }
    /**
     * @return Non-translated Key value for mapping in
     * DEFAULT_SNAKE_HEAD_OPTIONS
     */
    public String getSnakeHead() {
        return m_snake_head_path.get();
    }

    /**
     * @return Non-translated Key value for mapping in
     * DEFAULT_SNAKE_BODY_OPTIONS
     */
    public String getSnakeBody() {
        return m_snake_body_path.get();
    }

    /**
     * @return Player name specified by user
     */
    public String getPlayerName() {
        return m_player_name.get();
    }

    /**
     * Note that the path it always calculates from /assets/textures.
     * @return Background image path
     */
    public String getBackgroundPath() {
        return DEFAULT_BACKGROUND_OPTIONS.get(m_background_path.get());
    }

    /**
     * Note that the path it always calculates from is /assets/textures.
     * @return  Snake head image path
     */
    public String getSnakeHeadPath() {
        return DEFAULT_SNAKE_HEAD_OPTIONS.get(m_snake_head_path.get());
    }

    /**
     * Note that the path it always calculates from  /assets/textures.
     * @return Snake body image path
     */
    public String getSnakeBodyPath() {
        return DEFAULT_SNAKE_BODY_OPTIONS.get(m_snake_body_path.get());
    }

    /**
     * Is set every new game
     * @return Difficult of game
     */
    public String getDifficulty() { return m_difficulty; }

    /**
     * @return Property of the player's name
     */
    public StringProperty getPlayerNameProp() {
        return m_player_name;
    }

    //Custom Variables
    private final Map<DIRECTION, Integer> m_direction_map
            = Map.of(UP, -90, DOWN, 90, LEFT, -180, RIGHT, 0);
    private int m_speed = DEFAULT_SPEED; // In metres/second, converted to pixels later


    private double m_speed_pixels;
    private int m_num;
    private Vec2 m_velocity = new Vec2();
    private DIRECTION m_direction= DEFAULT_DIRECTION;
    final private double m_w;
    final private double m_h;
    private double m_s_w;
    private double m_s_h;
    private final BooleanProperty m_l;
    private final IntegerProperty m_length;
    private Point2D m_position;
    private final List<Point2D> m_bodyPoints;
    private int m_food_count = 0;
    private final IntegerProperty m_score;
    private Bundle m_high_scores;
    private String m_difficulty = "Easy";
    private final StringProperty m_background_path;
    private final StringProperty m_snake_head_path;
    private final StringProperty m_snake_body_path;
    private final StringProperty m_player_name;


    /**
     * Adds the default score increment.
     * @return True if score was added to successfully
     */
    public boolean addScore(){
        return setScore(getScore() + DEFAULT_SCORE_INCREMENT);
    }

    /**
     * Adds the default length increment.
     * @return True if length was added to successfully
     */
    public boolean addLength(){
        return setLength(getLength() + DEFAULT_LENGTH_INCREMENT);
    }


    /**
     * Sets dimensions of the snake head and game, and then sets up the frame
     * speed of the snake, and instantiates image paths.
     * @param width Width of the Snake Head
     * @param height Height of the Snake Head
     * @param container Container defining game bounds
     */
    public SnakeModel(double width, double height, Rectangle container) {
        // Set final defaults.
        m_length= new SimpleIntegerProperty(0);
        m_score = new SimpleIntegerProperty(0);
        m_l = new SimpleBooleanProperty(false);
        m_background_path = new SimpleStringProperty("");
        m_snake_head_path = new SimpleStringProperty("");
        m_snake_body_path = new SimpleStringProperty("");
        m_player_name = new SimpleStringProperty("");
        m_w = container.getWidth();
        m_h = container.getHeight();
        m_bodyPoints = new LinkedList<>();


        // Set non-final defaults.
        setSnakeHead("Level Headed");
        setSnakeBody("Ball");
        setBackground("Jovial");
        setHighScores(new Bundle(DEFAULT_SAVE_BUNDLE_NAME));
        getVelocity().set(0,getSpeed()); // Default movement
        setSnakeWidth(width);
        setSnakeHeight(height);
        setPosition(DEFAULT_START_POSITION);
    }

    /**
     * Resets the state of the Snake game, used for new games.
     * @return true if the reset was successful
     */
    public boolean reset(){
        boolean cond0 = resetLength();
        boolean cond1 =  setScore(0);
        boolean cond2 =  setState(false);

            getVelocity().set(0,getSpeed()); // Default movement
        boolean cond3  =  setPosition(DEFAULT_START_POSITION);
        return cond0 && cond1 && cond2 && cond3;
    }

    /**
     * Adds the player's score to the linked list of scores if it's within the
     * top DEFAULT_MAX_SAVED_SCORES.
     * @return true if the score was within the top 10
     */
    public boolean addHighScore(String name){

        LinkedList<Score> scores =
                getHighScores().get(DEFAULT_SAVE_BUNDLE_LIST);

        if(scores.size()>=DEFAULT_MAX_SAVED_SCORES){
            int to_check = scores.get(DEFAULT_MAX_SAVED_SCORES-1).score();
            if(to_check>getScore()){
                System.out.println("Score too small to add to high score " +
                        "board!");
                return false;
            }
        }
        for (Score score : scores){
            if(Objects.equals(score.name(), name)
                    && Objects.equals(getDifficulty(), score.difficulty())
                    && getScore()>score.score()){
                scores.remove(score);
                break;
            } else if(score.score() > getScore()
                    && Objects.equals(score.name(), name)
                    && Objects.equals(getDifficulty(), score.difficulty())){
                System.out.println("Score too small to replace current score!");
                return false;
            }
            System.out.println("Name : " + score.name() + " Score : "
                    + score.score());
        }
        scores.add(new Score(name,getScore(),getDifficulty()));
        scores.sort(null);
        return true;
    }


    /**
     * Calculates the frame speed according to the snakes Pixels/s metric and
     * the Default Frame Rate.
     * @return True if the frame speed was calculated
     */
    public boolean calcFrameSpeed(){
        // Pixels per default frame
        boolean case1 = setSpeedPixels(
                getPhysicsWorld().toPixels(getSpeed())/DEFAULT_FRAME_RATE);
        // How many frames to move a width (it won't be exact)
        // But it will approach near-perfectness as frame rate increases
        boolean case2 =
                setNum((int) Math.round(getSnakeWidth() / getSpeedPixels()));
        return case1 && case2;
    }

    /**
     * Sets the BodyPoints array by adding the history of the Snake Head's
     * positions every frame.
     * @return True if not outofBounds, or snake head position not set.
     */
    public boolean draw()
    {
        boolean cond0 = outofBounds();
        getBodyPoints().add(new Point2D(getPosition().getX(),
                getPosition().getY()));
        if ( getBodyPoints().size() == (getLength() + 1) * getNum())
        {
            getBodyPoints().remove(0);
        }
        //move();
        boolean cond1 = setPosition(new Point2D(
                getPosition().getX()+getSpeedPixels()*getVelocity().x,
                getPosition().getY()-getSpeedPixels()*getVelocity().y));
        if (cond1 && cond0){
            return true ;
        }else{
            setState(true);
            System.out.println("GAME OVER: In draw() for SnakeModel: Cond0: "
                    + cond0 + " Cond1: " + cond1);
            return false;
        }
    }


    /**
     * Checks whether the snake is out of bounds.
     * @return True if the snake isn't out of bounds
     */
    public boolean outofBounds()
    {
        boolean xOut = (getPosition().getX() <= 0
                || getPosition().getX() >= (getWidth() - getSnakeWidth()));
        boolean yOut = (getPosition().getY() <= 0
                || getPosition().getY() >= (getHeight() - getSnakeHeight()));
        if (xOut || yOut)
        {
            return false;
        }
        return true;
    }


    /**
     * Sets the unit vector of the Velocity, conforming to the direction.
     * @return True if velocity and direction are set up
     */
    public boolean move()
    {
        if (getVelocity() == null || getDirection() == null){
            return false;
        }
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
        return true;
    }


}
