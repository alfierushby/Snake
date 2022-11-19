package com.game;

/**
 * This class is not used anywhere.
 * Implementation or reason for its existence is unknown.
 * @author Alfie Rushby-modified
 */
public class Snake {

    private final long serialVersionUID = -3641221053272056036L;
    private int m_moving;

    /**
     * Sets the move parameter to x.
     * @param x speed to move
     * @return the same x speed
     */
    public int move(int x) {
        m_moving = x;
        return m_moving;
    }

    /**
     * Stops any moving.
     */
    public void stop() {
        m_moving = 0;
    }
}
