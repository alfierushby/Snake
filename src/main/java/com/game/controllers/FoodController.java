package com.game.controllers;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.game.SnakeFactory;
import com.game.data.Modeled;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;
import static com.game.data.Config.DEFAULT_SNAKE_HEIGHT;

/**
 * Controls the Food Entity created in {@link SnakeFactory}.
 * Created for each new Food Entity.
 */
public class FoodController extends Component implements Modeled {
    /**
     * @param model SnakeModel used by {@link com.game.SnakeGameApplication}
     * @return True if the model was set
     */
    public boolean setModel(SnakeModel model) {
        m_model = model;
        return true;
    }

    public boolean setAnim(Animation<?> m_anim) {
        this.m_anim = m_anim;
        return true;
    }

    public Animation<?> getAnim() {
        return m_anim;
    }

    private Entity m_entity;

    /**
     * @return Snake Model of the game
     */
    public SnakeModel getModel() {return m_model;}
    private SnakeModel m_model;
    Animation<?> m_anim;

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
        Animation<?> animation =  animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .delay(Duration.seconds(.5))
                .duration(Duration.seconds(1))
                .scale(getEntity())
                .from(new Point2D(0,0))
                .to(new Point2D(1,1))
                .build();
        animation.start();
        setAnim(animation);
        Point2D pos = new Point2D(
                random(DEFAULT_SNAKE_WIDTH,
                        DEFAULT_GAME_WIDTH-DEFAULT_SNAKE_WIDTH),
                random(DEFAULT_SNAKE_HEIGHT
                        ,DEFAULT_GAME_HEIGHT-DEFAULT_SNAKE_HEIGHT));
        getEntity().setPosition(pos);


    }

    @Override
    public void onUpdate(double tpf) {
        getAnim().onUpdate(tpf);
    }


}
