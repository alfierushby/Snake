package com.game;

/**
 * This class is not used anywhere.
 * Implementation or reason for its existence is unknown.
 * @author Alfie Rushby-modified
 */
public class Snake {

    private static final long serialVersionUID = -3641221053272056036L;


    // TODO: it needs renovation ???

    public static int moving;

    /**
     * Sets the move parameter to x.
     * @param x speed to move
     * @return the same x speed
     */
    public static int move(int x) {
        moving = x;
        return moving;
    }

    /**
     * Stops any moving.
     */
    public static void stop() {
        moving = 0;
    }
}
