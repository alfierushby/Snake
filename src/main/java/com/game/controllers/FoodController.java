package com.game.controllers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;

import java.awt.*;

import java.util.Random;

import static com.game.data.Config.*;

public class FoodController extends Component {
    public boolean setEntity(Entity entity) {
        m_entity = entity;
        return true;
    }

    public boolean setModel(SnakeModel model) {
        m_model = model;
        return true;
    }

    private Entity m_entity;
    public SnakeModel getModel() {return m_model;}
    private SnakeModel m_model;

    public FoodController(SnakeModel model) {
        setModel(model);
    }

    @Override
    public void onAdded() {
        setEntity(entity); // Protected FXGL var, setting to follow conventions
        Random random = new Random();
        Point2D pos = new Point2D(random.nextDouble(getModel().getWidth()),
                random.nextDouble(getModel().getHeight()));
        getEntity().setPosition(pos);


    }
}
