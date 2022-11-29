package com.game.views;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.game.SnakeFactory;
import com.game.events.SnakeEvent;
import com.game.models.SnakeModel;

import static com.almasb.fxgl.dsl.FXGL.onEvent;
import static com.game.data.Config.DEFAULT_SCORE_INCREMENT;

public class FoodView extends View{



    public FoodView(SnakeFactory factory, SnakeModel model){
        super(factory,model);
        createFood();

        onEvent(SnakeEvent.EAT_FOOD, this::eatenFood);
    }

    public boolean eatenFood(SnakeEvent event){
        createFood();
        getModel().addScore();
        return true;
    }

    public boolean createFood(){
        getSnakeFactory().newFoodItem(new SpawnData(), getModel());
        getModel().addLength();
        return true;
    }


}
