package com.game.views;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.game.SnakeFactory;
import com.game.events.SnakeEvent;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.onEvent;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getEventBus;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.game.data.Config.*;
import static com.game.enums.TYPES.SNAKE;

/**
 * 'View' of the Snake Entity and its BodyParts.
 * Inherits Abstract {@link View} that contains a couple basic members and
 * methods.
 */
public class SnakeView extends View {

    /**
     * @return Snake Head Entity
     */
    public Entity getSnake() {
        return m_snake;
    }

    /**
     * @param m_snake Snake Entity, functions as its head
     * @return true if the Entity set is a SNAKE.
     */
    public boolean setSnake(Entity m_snake) {
        if (m_snake.isType(SNAKE)){
            this.m_snake = m_snake;
            return true;
        }
        return false;
    }

    /**
     * @return List of Snake Body Part Entities
     */
    public List<Entity> getBodyParts() {
        return m_bodyParts;
    }

    Entity m_snake;
    private final List<Entity> m_bodyParts = new LinkedList<>();

    /**
     * Sets the Snake head, and runs the drawBody function at the FrameTime
     * interval calculated by the game's Model.
     * @param factory Factory of game
     * @param model Model of game
     */
    public SnakeView(SnakeFactory factory, SnakeModel model){
        super(factory,model);
        setupSnakeHead();

       // onEvent(SnakeEvent.MAKE_SNAKE_BODY, this::fillSnakeBodies);

        getGameTimer().runAtInterval(this::drawBody,
                Duration.millis(getModel().getFrameTime()));

        // When the length changes. Assume you cannot set lower lengths,
        // enforced by model.
        getModel().getLengthProp().addListener(e->{
            fillSnakeBodies();
        });
        // Test code
       // getModel().setLength(30);
        // Test code
    }

    /**
     *
     * @return True
     */
    public boolean drawBody()
    {
        boolean cond0 = getModel().draw();
        getSnake().setPosition(getModel().getPosition());
        int length = getModel().getBodyPoints().size() - getModel().getNum();
        List<Point2D> bodyPoints = getModel().getBodyPoints();
        int index = 0;
        for (int i = length; i >= getModel().getNum(); i -= getModel().getNum())
        {
            Point2D point = bodyPoints.get(i);
            getBodyParts().get(index).setPosition(point);
            index++;
        }
        if(cond0){
            return true;
        } else{
            System.out.println("ERROR: In drawBody() for SnakeView, unable to" +
                            " draw");
            return false;
        }
    }


    /**
     * Sets snake head to default position
     * @return True if able to set snake head
     */
    public boolean setupSnakeHead(){
        return setSnake(getSnakeFactory().newSnake(new SpawnData(
                DEFAULT_START_POSITION),getModel()));
    }

    /**
     * Creates the new Snake Bodies if there are less than the SnakeModel's
     * length.
     * @return True if able to create all the snake bodies, or if there are
     * no snake bodies to create
     */
    private boolean fillSnakeBodies(){
        if (getBodyParts().size() == getModel().getLength()){
            System.out.println("WARNING: In fillSnakeBodies() for SnakeView, "
                    + "no new snake bodies to make");
            return false;
        }
        boolean check;
        for(int i = getBodyParts().size(); i<getModel().getLength();i++){
            check = createSnakeBody();
            if(!check){
                System.out.println("ERROR: In fillSnakeBodies() for SnakeView, "
                        + "unable to create a new Snake Body");
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a new snake body via the Snake Factory.
     * @return Returns false if the length of the snake is too small
     */
    public boolean createSnakeBody(){
        if (getModel().getLength() <= getBodyParts().size()) {
            System.out.println("Tried to add a body part more than length!");
            return false;
        }
        getBodyParts().add(getSnakeFactory().newSnakeBody(
                new SpawnData(getSnake().getX(),getSnake().getY())));
        return true;
    }


}

