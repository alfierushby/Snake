package com.game.controllers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.game.SnakeFactory;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;

import java.util.Random;

/**
 * Controls the Food Entity created in {@link SnakeFactory}.
 * Created for each new Food Entity.
 */
public class FoodController extends Component {
    /**
     * @param model SnakeModel used by {@link com.game.SnakeGameApplication}
     * @return True if the model was set
     */
    public boolean setModel(SnakeModel model) {
        m_model = model;
        return true;
    }

    private Entity m_entity;

    /**
     * @return Snake Model of the game
     */
    public SnakeModel getModel() {return m_model;}
    private SnakeModel m_model;

    /**
     * @param model SnakeModel used by {@link com.game.SnakeGameApplication}
     */
    public FoodController(SnakeModel model) {
        setModel(model);
    }

    /**
     * Called when the Food Entity is created.
     * Sets it to a random position in game bounds.
     */
    @Override
    public void onAdded() {
        Random random = new Random();
        Point2D pos =
                new Point2D(random.nextDouble(getModel().getWidth()
                        - getModel().getSnakeWidth()),
                random.nextDouble(getModel().getHeight()
                        - getModel().getSnakeHeight()));
        getEntity().setPosition(pos);


    }
}
