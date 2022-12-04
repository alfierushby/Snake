package com.game;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.game.controllers.FoodController;
import com.game.controllers.SnakeBodyController;
import com.game.controllers.SnakeController;
import com.game.data.FoodImages;
import com.game.models.SnakeModel;

import java.util.Map;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.game.enums.TYPES.*;
import static java.util.Map.entry;

public class SnakeFactory implements EntityFactory {


    public FoodImages getFood() {
        return food;
    }
    private final FoodImages food = new FoodImages();


    @Spawns("snake")
    public Entity newSnake(SpawnData data, SnakeModel model){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return entityBuilder(data)
                .type(SNAKE)
                .bbox(new HitBox(BoundingShape.box(25, 25)))
                .collidable()
               // .with(physics)
                .viewWithBBox(texture("snake-head-right.png",
                        25, 25))
                .with(new SnakeController(model))
                .buildAndAttach();
    }

    @Spawns("snakebody")
    public Entity newSnakeBody(SpawnData data){
        Entity body = entityBuilder(data)
                .type(SNAKE_BODY)
                .bbox(new HitBox(BoundingShape.box(25, 25)))
                .collidable()
                .with(new SnakeBodyController())
                .viewWithBBox(texture("snake-body.png",
                        25, 25).getNode())
                .buildAndAttach();
        body.setPosition(-100,-100);
        return body;
    }
    @Spawns("food")
    public Entity newFoodItem(SpawnData data,SnakeModel model){
        int rand = new Random().nextInt(16);
        return entityBuilder(data)
                .type(FOOD)
                .bbox(new HitBox(BoundingShape.box(25, 25)))
                .collidable()
                .with(new FoodController(model))
                .viewWithBBox(texture(getFood().getFoodindex(rand)).getNode())
                .buildAndAttach();
    }

}
