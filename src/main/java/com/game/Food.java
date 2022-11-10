package com.game;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * This class handles the food location and status.
 * It extends {@link com.game.MyFrame.SnakeObject}, and uses all of its functionality and states.
 */
public class Food extends MyFrame.SnakeObject
{

	private static final long serialVersionUID = -3641221053272056036L;


	/**
	 * Default constructor. This sets the state, l, to true meaning it is not
	 * yet eaten. It then sets the image the width, height, and its position.
	 * Its position is set to be random, based on hard set numbers.
	 * Change magic numbers to global constants.
	 */
	public Food()	{
		this.l = true;

		this.i = ImageUtil.images.get(String.valueOf(new Random().nextInt(10)));

		this.w = i.getWidth(null);
		this.h = i.getHeight(null);

		this.x = (int) (Math.random() * (870 - w + 10));
		this.y = (int) (Math.random() * (560 - h - 40));
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
	public void eaten(MyFrame.MySnake mySnake)	{

		if (mySnake.getRectangle().intersects(this.getRectangle()) && l && mySnake.l)		{
			this.l = false;
			mySnake.changeLength(mySnake.getLength() + 1);
			mySnake.score += 521;
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
		g.drawImage(i, x, y, null);
	}
}
