package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * This is the implementation of the {@link MyFrame} class, and
 * overrides some functions from {@link JPanel} and {@link java.awt.event.KeyListener}.
 * This is the class that contains the snake and its food, and draws the game board
 * whilst also receiving key events.
 * It is the essential game class.
 * This class contains the following new state information:
 * <ul>
 * <li> The snake object, mySnake</li>
 * <li> The food object, food</li>
 * <li> The background image, background</li>
 * <li> The game-over image, fail</li>
 * </ul>
 * <p>
 *     The state information is public, which is unwelcome.
 *     The overriding of 'keyPressed' uses a pointless inheritance
 *     from MyFrame, thus forcing MyFrame to implement functions
 *     it won't use.
 *     There is multi use of static functions from other static
 *     classes, and this causes poor code readability.
 *     Change asap!
 * </p>
 */
public class Play extends MyFrame
{

	private static final long serialVersionUID = -3641221053272056036L;

	public MySnake mySnake = new MySnake(100, 100);// x , y

	/**
	 * @return returns Snake object that plays the game
	 */
	public MySnake getMySnake() {
		return mySnake;
	}


	/**
	 * @return returns the Food object that the snake eats
	 */
	public Food getFood() {
		return food;
	}

	public Food food = new Food();

	public Image background = ImageUtil.images.get("UI-background");
	public Image fail = ImageUtil.images.get("game-scene-01");

	/**
	 * This is the only valuable key event needed, and it sends the
	 * key event (what key was pressed) to the snake object to
	 * process what direction it should change to via the keyPressed
	 * implementation in the {@link MySnake} object.
	 * @param e the event to be processed, includes key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		super.keyPressed(e);
		mySnake.keyPressed(e);
	}

	/**
	 * This renders the game by first drawing the background, then it
	 * draws the snake, and draws the food on the condition that, if it
	 * is yet to be eaten, it will draw it and then check that the
	 * snake object has not eaten it.
	 * If it is eaten, then it won't draw the food in the next frame, and
	 * instead create a new food object that will then be drawn in the next
	 * frame in a random location.
	 * If the game has ended, this function will paint the game over image.
	 * After all of this is done, it draws the score.
	 * <p>
	 *     This logic is confusing to debug. Needs to be segmented to clear
	 *     functions, with limited if statements.
	 * </p>
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(background, 0, 0, null);

		// Determine the state of the game.
		if (mySnake.l)
		{
			mySnake.draw(g);
			if (food.l)
			{
				food.draw(g);
				food.eaten(mySnake);
			} else
			{
				food = new Food();
			}
		} else
		{
			g.drawImage(fail, 0, 0, null);
		}
		drawScore(g);
	}

	/**
	 * This draws the text score in the corner of the page every frame from
	 * {@link #paint(Graphics)}.
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void drawScore(Graphics g)
	{
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.setColor(Color.MAGENTA);
		g.drawString("SCORE : " + mySnake.score, 20, 40);
	}

	/**
	 * This first creates and loads the frame, using the {@link MyFrame} function
	 * loadFrame. Afterwards, it plays music.
	 * The loadFrame function will start the thread to begin rendering the game.
	 * @param args stub arguments.
	 */
	public static void main(String[] args)
	{
		new Play().loadFrame();
		MusicPlayer.getMusicPlay("/frogger.mp3");

	}
/*	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		// frame.setSize(400,600);
		frame.setBounds(450, 200, 920, 600);
		// frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnakePanel panel = new SnakePanel();
		frame.add(panel);

		frame.setVisible(true);

		// Play the background music.
		MusicPlayer.getMusicPlay("resource\\music\\background.mp3");
	} 
*/
}
