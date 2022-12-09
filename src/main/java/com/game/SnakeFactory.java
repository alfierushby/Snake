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
import static com.game.data.Config.*;
import static com.game.enums.TYPES.*;
import static java.util.Map.entry;

/**
 * Snake Factory used to create all the game entities.
 * @author Alfie Rushby
 */
public class SnakeFactory implements EntityFactory {


    /**
     * @return The Food Object.
     */
    public FoodImages getFood() {
        return food;
    }
    private final FoodImages food = new FoodImages();


    /**
     * Creates a Playable Snake Head.
     * @param data SpawnData
     * @param model Snake Model of game
     * @return A Game {@link Entity}
     */
    @Spawns("snake")
    public Entity newSnake(SpawnData data, SnakeModel model){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return entityBuilder(data)
                .type(SNAKE)
                .collidable()
               // .with(physics)
                .bbox(new HitBox(BoundingShape.box(10, 10)))
                .view(texture(model.getSnakeHeadPath()).getNode())
                .with(new SnakeController(model))                .buildAndAttach();
    }

    /**
     * Creates a Snake Body Entity.
     * @param data SpawnData
     * @param model Snake Model of game
     * @return A Game {@link Entity}
     */
    @Spawns("snakebody")
    public Entity newSnakeBody(SpawnData data, SnakeModel model){
        System.out.println(model.getSnakeBodyPath());
        Entity body = entityBuilder(data)
                .type(SNAKE_BODY)
                .collidable()
                .with(new SnakeBodyController())
                .bbox(new HitBox(BoundingShape.box(10, 10)))
                .view(texture(model.getSnakeBodyPath()).getNode())
                .buildAndAttach();
        body.setPosition(-100,-100);
        return body;
    }

    /**
     * Creates a Food item to be eaten.
     * @param data SpawnData
     * @param model Snake Model of game
     * @return A Game {@link Entity}
     */
    @Spawns("food")
    public Entity newFoodItem(SpawnData data,SnakeModel model){
        int rand = new Random().nextInt(16);
        return entityBuilder(data)
                .type(FOOD)
                .bbox(new HitBox(BoundingShape.box(30, 30)))
                .collidable()
                .with(new FoodController(model))
                .view(texture(getFood().getFoodindex(rand)).getNode())
                .buildAndAttach();
    }

    /**
     * Creates an Obstacle that kills the snake, called via .tmx levels.
     * @param data SpawnData
     * @return A Game {@link Entity}
     */
    @Spawns("obstacle")
    public Entity newObstacle(SpawnData data){
        int rand = new Random().nextInt(16);
        return entityBuilder(data)
                .type(OBSTACLE)
                .bbox(new HitBox(BoundingShape.box( data.<Integer>get("width"),
                        data.<Integer>get("height"))))
                .collidable()
                .build();
    }

}
