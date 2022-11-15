package com.test;

import com.game.Play;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayTest {
    static Play m_play = new Play(); // Static test object used by all tests
    int[] coord = {0,0};
    int expected_context = KeyEvent.VK_UP; // What direction the snake is moving
    private int[] getCoord(){
        return new int[] {m_play.getX(),m_play.getY()};
    }
    private int[] applyKey(int key){
        m_play.keyPressed(new KeyEvent(m_play.jFrame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  key,'Z'));
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

        JFrame frame = m_play.jFrame;
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
    void paint() {
    }

    @Test
    void drawScore() {
    }

}