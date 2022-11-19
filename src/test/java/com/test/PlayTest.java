package com.test;

import com.game.Food;
import com.game.MySnake;
import com.game.Play;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayTest {
    static Play m_play = new Play(); // Static test object used by all tests
    static MySnake m_snake = m_play.getMySnake();
    int[] coord = {0,0};
    int expected_context = KeyEvent.VK_UP; // What direction the snake is moving
    private int[] getCoord(){
        return new int[] {m_snake.getX(),m_snake.getY()};
    }
    private int[] applyKey(int key){
        m_play.keyPressed(new KeyEvent(m_play.getjFrame(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  key,'Z'));
        return getCoord();
    }
    private Boolean returnContext(int context){
        // Returns expected event on direction of movement
        return switch (context) {
            case KeyEvent.VK_UP -> getCoord()[1] < coord[1];
            case KeyEvent.VK_DOWN -> getCoord()[1] > coord[1];
            case KeyEvent.VK_LEFT -> getCoord()[0] < coord[0];
            case KeyEvent.VK_RIGHT -> getCoord()[0] > coord[0];
            default -> false;
        };
    }

    private Boolean conditionMovement(int key, boolean can) throws InterruptedException {
        // Returns based on a movement condition
        // Context is the direction it is moving, and key is the direction we are trying to move
        // Can is whether we expect it to change
        coord = applyKey(key);
        Thread.sleep(100);
        if (can){
            // If we expect it to work, we expect it to move in Key's direction
            expected_context = key;
            return returnContext(key);
        } else{
            // Else, we expect it to move in the context's direction
            return returnContext(expected_context);
        }
    }


    @BeforeAll
    static void setUp() {
        m_play.loadFrame();
    }


    @Test
    void keyPressed() throws InterruptedException {
        // Circular snake movement verification. Tests every scenario.

        JFrame frame = m_play.getjFrame();
        // Test up movement
        assertTrue(conditionMovement(KeyEvent.VK_UP,true)); // Coordinates start from top left, so 0 is top.

        // Test it cannot move back whilst moving up
        assertTrue(conditionMovement(KeyEvent.VK_DOWN,false));

        //Test it can go left
        assertTrue(conditionMovement(KeyEvent.VK_LEFT,true));

        //Test it can't go right
        assertTrue(conditionMovement(KeyEvent.VK_RIGHT,false));

        //Test it can go down
        assertTrue(conditionMovement(KeyEvent.VK_DOWN,true));

        //Test it can't go up
        assertTrue(conditionMovement(KeyEvent.VK_UP,false));

        //Test it can go right
        assertTrue(conditionMovement(KeyEvent.VK_RIGHT,true));

        //Test it can't go left
        assertTrue(conditionMovement(KeyEvent.VK_LEFT,false));

        //Test it can go up
        assertTrue(conditionMovement(KeyEvent.VK_UP,true));

    }

    @Test
    void foodTest() throws InterruptedException {
        // An integration test for moving to a food piece and eating it
        // Made to be extensible with major codebase changes
        // First version won't be split up, so reused code will be prominent

        Food food = m_play.getFood();
        int direction, food_x = food.getX(), food_y = food.getY();
        int speed = m_snake.getSpeed_XY(), frame_time= m_play.getFrame_time();
        int max=500, loop=0;
        coord = new int[]{m_snake.getX() - food_x, m_snake.getY() - food_y};

        // When difference is negative, move right or down, else otherwise
        // Set x direction to move, as we assume we are moving up
        if(coord[0]<0){
            direction = KeyEvent.VK_RIGHT;
        } else {
            direction = KeyEvent.VK_LEFT;
        }
        applyKey(direction);

        // Wait until the snake has reached +-5 of the target X coordinate
        while( ( m_snake.getX()<(food_x-speed) || m_snake.getX()>(food_x+speed) )
        && loop < max){
            Thread.sleep(frame_time);
            loop++;
        }
        if(loop == max) {fail("The snake failed to move to X coordinate in time");}

        // Set y direction to move
        if(coord[1]<0){
            direction = KeyEvent.VK_DOWN;
        } else {
            direction = KeyEvent.VK_UP;
        }
        loop = 0;
        applyKey(direction);

        // Wait until the snake has reached +-5 of the target Y coordinate
        while( ( m_snake.getY()<(food_y-speed) || m_snake.getY()>(food_y+speed) )
                && loop < max){
            Thread.sleep(frame_time);
            loop++;
        }
        if(loop == max) {fail("The snake failed to move to Y coordinate in time");}

        // Check that the food has been eaten.
        if(!food.getState()) {
            assertTrue(true);
        }else {
            fail("The snake failed to eat the food after pass through it");
        }
    }

    @Test
    void paint() {
    }

    @Test
    void drawScore() {
    }

}
