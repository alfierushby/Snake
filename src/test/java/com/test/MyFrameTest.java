package com.test;

import com.game.MyFrame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class MyFrameTest {
    MyFrame testPanel= new MyFrame();;

    @Test
    void loadFrame() {
        testPanel.loadFrame();
        JFrame frame = testPanel.jFrame;
        assertEquals("Snake Game",frame.getTitle());
        // Test size
        //assertEquals(testPanel.X,frame.getX());
        //assertEquals(testPanel.Y,frame.getY());
        // Test visibility
        assertTrue(frame.isVisible());
        // Test panel is in frame
        assertEquals(testPanel,frame.getComponent(0));
        // Test key listener is applied
        assertEquals(testPanel,frame.getKeyListeners()[0]);
        // Make sure it is double buffered
        assertTrue(testPanel.isDoubleBuffered());
    }

}