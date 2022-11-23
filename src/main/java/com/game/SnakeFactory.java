package com.game;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.game.models.SnakeBodyModel;
import com.game.models.SnakeModel;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.game.enums.TYPES.*;

public class SnakeFactory implements EntityFactory {

    @Spawns("snake")
    public Entity newSnake(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return entityBuilder(data)
                .type(SNAKE)
                .bbox(new HitBox(BoundingShape.box(25, 25)))
                .collidable()
                .with(physics)
                .with(new SnakeModel(new Rectangle(600,600)))
                .viewWithBBox(texture("snake-head-right.png",
                        25, 25))
                .buildAndAttach();
    }

    @Spawns("snakebody")
    public Entity newSnakeBody(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return entityBuilder(data)
                .type(SNAKE_BODY)
                .bbox(new HitBox(BoundingShape.box(25, 25)))
                .collidable()
                .with(physics)
                .with(new SnakeBodyModel())
                .viewWithBBox(texture("snake-body.png",
                        25, 25))
                .buildAndAttach();
    }
}
