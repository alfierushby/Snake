package com.game.controllers;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.Timer;
import com.almasb.fxgl.time.TimerAction;
import com.game.enums.DIRECTION;
import com.game.models.SnakeModel;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

public class SnakeBodyController extends Component {
    long m_milli_time;
    public SnakeBodyController(long milli_time) {
       m_milli_time = milli_time;
    }

    @Override
    public void onUpdate(double tpf){
        despawnWithDelay(entity,new Duration(m_milli_time));
    }
}
