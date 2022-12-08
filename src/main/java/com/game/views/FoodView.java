package com.game.views;

import com.almasb.fxgl.entity.SpawnData;
import com.game.SnakeFactory;
import com.game.events.SnakeEvent;
import com.game.models.SnakeModel;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * View for Food entities that handles updating the 'View' of the Food items,
 * involving destroying and spawning.
 */
public class FoodView extends View{


    /**
     * Creates the first Food entity on load, and listens for eating events,
     * called when the snake eats a food entity.
     * @param factory Entity factory of the game
     * @param model SnakeModel of the game
     */
    public FoodView(SnakeFactory factory, SnakeModel model){
        super(factory,model);
        createFood();
        onEvent(SnakeEvent.EAT_FOOD, this::eatenFood);
    }

    /**
     * Creates a new food entity to make up for the destroyed one, and adds
     * to the player's score.
     * @param event Event calling this function, should be EATEN_FOOD
     * @return true if added score
     */
    public boolean eatenFood(SnakeEvent event){
        createFood();
        return getModel().addScore();
    }

    /**
     * Creates a food entity to spawn.
     * @return true if added length
     */
    public boolean createFood(){
        getSnakeFactory().newFoodItem(new SpawnData(), getModel());
        return getModel().addLength();
    }


}
