package com.test;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.test.RunWithFX;
import com.game.SnakeGameApplication;
import com.game.controllers.FoodController;
import com.game.models.SnakeModel;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static com.game.enums.TYPES.FOOD;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(RunWithFX.class)
class PlayTest {

    @BeforeAll
    static void initGameTests() throws InterruptedException {
        Thread one = new Thread(()->{
            SnakeGameApplication.main(new String[] {});
        });
        one.start();
        Thread.sleep(2000); // Wait for application to start
        Platform.runLater(()->{
            app = (SnakeGameApplication) FXGL.getApp();
            app.getModel().setDifficulty("Test");
            FXGL.getGameController().startNewGame();
        });
    }

    double[] coord = {0,0};
    static SnakeGameApplication app;
    KeyCode expected_context = KeyCode.S; // What direction the snake is moving
    private double[] getCoord(){
        Point2D pos = app.getModel().getPosition();
        return new double[] {pos.getX(),pos.getY()};
    }
    private double[] applyKey(KeyCode key) throws InterruptedException {
        FXGL.getInput().mockKeyPress(key);
        Thread.sleep(20);
        FXGL.getInput().mockKeyRelease(key);
        return getCoord();
    }
    private Boolean returnContext(KeyCode context){
        // Returns expected event on direction of movement
        return switch (context) {
            case W -> getCoord()[1] < coord[1];
            case S -> getCoord()[1] > coord[1];
            case A -> getCoord()[0] < coord[0];
            case D -> getCoord()[0] > coord[0];
            default -> false;
        };
    }

    private Boolean conditionMovement(KeyCode key, boolean can) throws InterruptedException {
        // Returns based on a movement condition
        // Context is the direction it is moving, and key is the direction we are trying to move
        // Can is whether we expect it to change
        coord = applyKey(key);
        Thread.sleep(100);
        if (can) {
                // If we expect it to work, we expect it to move in Key's direction
                expected_context = key;
            }
        return returnContext(expected_context);
    }
    @Test
    void keyPressed() throws InterruptedException {
        // Circular snake movement verification. Tests every scenario.
        Thread.sleep(100);
        // Test up movement
        assertTrue(conditionMovement(KeyCode.S, true)); // Coordinates start
        // from top left, so 0 is top.

        // Test it cannot move back whilst moving down
        assertTrue(conditionMovement(KeyCode.W, false));

        //Test it can go left
        assertTrue(conditionMovement(KeyCode.A, true));

        //Test it can't go right
        assertTrue(conditionMovement(KeyCode.D, false));

        //Test it can go up
        assertTrue(conditionMovement(KeyCode.W, true));

        //Test it can't go up
        assertTrue(conditionMovement(KeyCode.W, false));

        //Test it can go right
        assertTrue(conditionMovement(KeyCode.D, true));

        //Test it can't go left
        assertTrue(conditionMovement(KeyCode.A, false));

        //Test it can go down
        assertTrue(conditionMovement(KeyCode.S, true));
    }

    @Test
    void foodTest() throws InterruptedException {
        // An integration test for moving to a food piece and eating it
        // Made to be extensible with major codebase changes
        // First version won't be split up, so reused code will be prominent
        Thread.sleep(100);
        Entity food = FXGL.getGameScene().getGameWorld().getEntitiesByType(FOOD)
                .get(0);
        SnakeModel model = app.getModel();
        Point2D pos = model.getPosition();

        KeyCode direction;
        double food_x = food.getX(), food_y = food.getY();
        double speed = model.getSpeed();
        int frame_time= model.getFrameTime();
        int max=1000, loop=0;
        coord = new double[]{pos.getX() - food_x, pos.getY() - food_y};

        // When difference is negative, move right or down, else otherwise
        // Set x direction to move, as we assume we are moving up
        if(coord[0]<0){
            direction = KeyCode.D;
        } else {
            direction = KeyCode.A;
        }
        applyKey(direction);

        // Wait until the snake has reached +-5 of the target X coordinate
        while( ( model.getPosition().getX()<(food_x-speed) ||
                model.getPosition().getX()>(food_x+speed) )
                && loop < max){
            Thread.sleep(frame_time);
            loop++;
        }
        if(loop == max) {fail("The snake failed to move to X coordinate in time");}

        // Set y direction to move
        if(coord[1]<0){
            direction = KeyCode.S;
        } else {
            direction = KeyCode.W;
        }
        loop = 0;
        applyKey(direction);

        // Wait until the snake has reached +-5 of the target Y coordinate
        while( ( model.getPosition().getY()<(food_y-speed)
                || model.getPosition().getY()>(food_y+speed) )
                && loop < max){
            Thread.sleep(frame_time);
            loop++;
        }
        if(loop == max) {fail("The snake failed to move to Y coordinate in time");}

        // Check that the food has been eaten.
        if(food.isActive() && model.getScore()==1) {
            fail("The snake failed to eat the food after pass through it");
        }else {
            assertTrue(true);
        }
    }
}
