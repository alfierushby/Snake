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

public class SnakeView extends View {

    public Entity getSnake() {
        return m_snake;
    }

    public boolean setSnake(Entity m_snake) {
        this.m_snake = m_snake;
        return true;
    }
    public List<Entity> getBodyParts() {
        return m_bodyParts;
    }

    Entity m_snake;
    private final List<Entity> m_bodyParts = new LinkedList<>();

    public SnakeView(SnakeFactory factory, SnakeModel model){
        super(factory,model);
        setupSnakeHead();

        onEvent(SnakeEvent.MAKE_SNAKE_BODY, this::fillSnakeBodies);

        getGameTimer().runAtInterval(this::drawBody,
                Duration.millis(getModel().getFrameTime()));
        // Test code
       // getModel().setLength(30);
        // Test code
    }

    public boolean drawBody()
    {
        getModel().draw();
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
        return true;
    }


    public boolean setupSnakeHead(){
        setSnake(getSnakeFactory().newSnake(new SpawnData(100,100)
                ,getModel()));
        return true;
    }

    private boolean fillSnakeBodies(SnakeEvent event){
        if (getBodyParts().size() == getModel().getLength()){
            return false;
        }
        for(int i = getBodyParts().size(); i<getModel().getLength();i++){
            createSnakeBody();
        }

        return true;
    }

    public boolean createSnakeBody(){
        if (getModel().getLength() <= getBodyParts().size()) {
            System.out.println("Tried to add a body part more than length!");
            return false;
        }
        getBodyParts().add(getSnakeFactory().newSnakeBody(
                new SpawnData(getSnake().getX(),getSnake().getY())));
        System.out.println("added new thing!");
        return true;
    }


}

