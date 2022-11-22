package com.game;

import java.awt.Graphics;
import java.util.Random;

/**
 * This class handles the food location and status.
 * It extends {@link com.game.SnakeObject}, and uses all of its functionality and states.
 * @author Alfie Rushby-modified
 */
public class Food extends SnakeObject
{

	private static final long serialVersionUID = -3641221053272056036L;


	/**
	 * Default constructor. This sets the state, l, to true meaning it is not
	 * yet eaten. It then sets the image the width, height, and its position.
	 * Its position is set to be random, based on hard set numbers.
	 * Change magic numbers to global constants.
	 */
	public Food()	{
		setState(true);

		setImage(ImageUtil.images
				.get(String.valueOf(new Random().nextInt(10))));

		setWidth(getImage().getWidth(null));
		setHeight(getImage().getHeight(null));

		setX((int) (Math.random() * (870 - getWidth() + 10)));
		setY((int) (Math.random() * (560 - getHeight() - 40)));
	}

	/**
	 * As both snake and food are SnakeObjects, we can call getRectangle to
	 * get a rectangle equivalent of their size and position. The intersects
	 * function will then see if they overlap.
	 * If this is the case, we add a magic score number, and set its state to
	 * false (as in eaten).
	 * The magic numbers should be removed.
	 * @param mySnake This is the snake object that is playing the game
	 */
	public void eaten(MySnake mySnake)	{

		if (mySnake.getRectangle()
				.intersects(this.getRectangle()) && getState()
				&& mySnake.getState())
		{
			setState(false);
			mySnake.changeLength(mySnake.getLength() + 1);
			mySnake.addScore(521);
		}
	}

	/**
	 * This draws the food image whenever a new frame is rendered,
	 * called from {@link MyFrame}'s thread, in {@link Play} Paint function.
	 * @param g The graphic parameter pertaining to {@link MyFrame}
	 */
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(getImage(), getX(), getY(), null);
	}
}
