package com.game;

/**
 * This is used for any object that moves. Moreso, the snake object.
 * Not needed unless there are more movable classes.
 */
public interface movable
{
	/**
	 * Used by {@link MyFrame} for the {@link com.game.MyFrame.MySnake} object.
	 * It has little point, as there is only one class using this.
	 */
	void move();
}
