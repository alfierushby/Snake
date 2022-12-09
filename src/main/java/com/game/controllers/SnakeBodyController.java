package com.game.controllers;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.Timer;
import com.almasb.fxgl.time.TimerAction;
import com.game.enums.DIRECTION;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.awt.*;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.animationBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.random;
import static com.game.data.Config.*;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

/**
 * Controller for the Body elements that follow the snake
 */
public class SnakeBodyController extends Component {

    /**
     * @param point The point it will spawn at
     * @return True if set to point
     */

    public boolean setPosition(Point2D point) {
        getEntity().setPosition(point);
        return getEntity().getPosition().equals(point);
    }
}
