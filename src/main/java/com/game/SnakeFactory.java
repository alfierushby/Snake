package com.game;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.game.collisions.FoodSnakeHandler;
import com.game.controllers.FoodController;
import com.game.controllers.SnakeBodyController;
import com.game.controllers.SnakeController;
import com.game.enums.DIRECTION;
import com.game.models.SnakeModel;

import java.awt.*;
import java.util.Map;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;
import static com.game.enums.TYPES.*;
import static java.util.Map.entry;

public class SnakeFactory implements EntityFactory {


    public Map<Integer, String> getFood() {
        return m_Food;
    }
    public String getFoodindex(int index) {
        if(index < 0 || index > getFood().size()){
            System.out.println("Tried to access non-existent index!");
            index=0;
        }
        return m_Food.get(index);
    }



    private final Map<Integer, String> m_Food
            = Map.ofEntries(
            entry(0,"Food/food-kiwi.png"),
            entry(1, "Food/food-lemon.png"),
            entry(2,"Food/food-litchi.png"),
            entry(3, "Food/food-mango.png"),
            entry(4,"Food/food-apple.png"),
            entry(5, "Food/food-banana.png"),
            entry(6,"Food/food-blueberry.png"),
            entry(7, "Food/food-cherry.png"),
            entry(8,"Food/food-durian.png"),
            entry(9, "Food/food-grape.png"),
            entry(10,"Food/food-grapefruit.png"),
            entry(11, "Food/food-peach.png"),
            entry(12,"Food/food-pear.png"),
            entry(13, "Food/food-orange.png"),
            entry(14, "Food/food-pineapple.png"),
            entry(15, "Food/food-strawberry.png"),
            entry(16, "Food/food-watermelon.png")
    );


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
                .viewWithBBox(texture(getFoodindex(rand),
                        25, 25).getNode())
                .buildAndAttach();
    }

}
