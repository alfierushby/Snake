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

public class SnakeView {

    public Entity getSnake() {
        return m_snake;
    }

    public boolean setSnake(Entity m_snake) {
        this.m_snake = m_snake;
        return true;
    }

    public SnakeFactory getSnakeFactory() {
        return m_snakeFactory;
    }
    private void setModel(SnakeModel snakeModel) {
        m_model = snakeModel;
    }

    public boolean setSnakeFactory(SnakeFactory m_snakeFactory) {
        this.m_snakeFactory = m_snakeFactory;
        return true;
    }
    public List<Entity> getBodyParts() {
        return m_bodyParts;
    }

    public SnakeModel getModel() {
        return m_model;
    }


    Entity m_snake;
    SnakeFactory m_snakeFactory;

    SnakeModel m_model;


    private final List<Entity> m_bodyParts = new LinkedList<>();

    public SnakeView(SnakeFactory factory){
        setSnakeFactory(factory);
        setupSnakeHead();

        onEvent(SnakeEvent.MAKE_SNAKE_BODY, this::createSnakeBody);

        getGameTimer().runAtInterval(this::drawBody,
                Duration.millis(getModel().getFrameTime()));
        // Test code
        getModel().setLength(30);
        // Test code
    }

    public boolean drawBody()
    {
        getModel().draw();
        getSnake().setPosition(getModel().getPosition());
        int length = getModel().getBodyPoints().size() - getModel().getNum();
        List<Point2D> bodyPoints = getModel().getBodyPoints();
        //System.out.println(length+" "+bodyPoints.size()+" "+num);
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
        setModel(new SnakeModel(DEFAULT_SNAKE_WIDTH,
                DEFAULT_SNAKE_HEIGHT,
                new Rectangle(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT)));

        setSnake(getSnakeFactory().newSnake(new SpawnData(100,100),getModel()));
        return true;
    }


    public boolean createSnakeBody(SnakeEvent event){
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

