package com.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * This class currently manages the Swing frame that holds the game elements.
 * Listens to key events via {@link #keyTyped(KeyEvent)}, {@link #keyPressed(KeyEvent)}
 * and {@link #keyReleased(KeyEvent)}.
 * <p>
 *     This method is not instantiated directly.
 *     It holds core game logic, including move events, and eating itself/going out of bounds.
 * </p>
 * @author Alfie Rushby-modified
 * @version 1.0
 */
public class MyFrame extends JPanel implements KeyListener
{
	final int X = 870, Y=560;
	/**
	 *
	 */
	//private static final long serialVersionUID = -3149926831770554380L;

	/**
	 * This is the highest level container, and contains the JPanel {@link MyFrame}
	 */
	public JFrame jFrame = new JFrame();

	/**
	 * The default constructor.
	 * It sets the icon of the game window, reference {@link #jFrame}.
	 */
	public MyFrame()
	{
		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(MyFrame.class.getResource("/snake-logo.png"))); // Fix whatever the reason this needs a '/'.
	}

	/**
	 * The general areas of state information set are:
	 * <ul>
	 * <li> The size</li>
	 * <li> The title</li>
	 * <li> The KeyListener</li>
	 * <li> The visibility</li>
	 * <li> The close event in a WindowListener</li>
	 * </ul>
	 * <p>
	 * This sets the {@link MyFrame} to render twice, adds it to the
	 * JFrame {@link #jFrame}, and sets the functions implemented by {@link KeyListener} to
	 * listen to the frame's events.
	 * JPanel will not be focused on, but the frame, thus cannot receive listener events.
	 * </p>
	 * <p>
	 * The title and size are set, and it is set to a null location, thus being centred.
	 * A window listener is then created, and is made to force close the running program, as
	 * closing the window by itself will not close the system in the background.
	 * Also, the visibility, and then the thread {@link MyThread} are set and started.
	 * </p>
	 *
	 *
	 */
	public void loadFrame()
	{
		/*
		 * Prevent the image from flashing.
		 */
		this.setDoubleBuffered(true);
		jFrame.add(this);
		jFrame.addKeyListener(this);

		jFrame.setTitle("Snakee Yipee");
		jFrame.setSize(870, 560);
		jFrame.setLocationRelativeTo(null);
		jFrame.addWindowListener(new WindowAdapter()// loka
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				System.exit(0);
			}
		});
		jFrame.setVisible(true);

		new MyThread().start();
	}

	/**
	 * <p>
	 * 	This maintains the role of updating the game every 30 milliseconds.
	 * 	It does this by running in a seperate thread, that calls the super's run
	 * 	function to begin the runnable task, and then enters an infinite while
	 * loop.
	 * </p>
	 */
	class MyThread extends Thread
	{
		/**
		 * This runs in an infinite loop with an exception handle.
		 * The repainting makes the game have a frame-time of 30ms.
		 */
		@Override
		public void run()
		{
			super.run();
			while (true)
			{
				repaint();
				try
				{
					sleep(30);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This requires re-factoring, as it is a stub that shouldn't exist.
	 * @param e the event to be processed
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * This requires re-factoring, as it is a stub that shouldn't exist.
	 * @param e the event to be processed
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * This requires re-factoring, as it is a stub that shouldn't exist.
	 * @param e the event to be processed
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Static subclass of MyFrame. This entails
	 * that it cannot access any of the object functions
	 * of its frame, as it's an object.
	 * Should call into question the validity of this
	 * implementation.
	 * <p>
	 * The general areas of state information set are:
	 * <ul>
	 * <li> The x,y coordinates of the snake</li>
	 * <li> The image of the snake body, i</li>
	 * <li> The width and height of the frame, w and h</li>
	 * <li> The game end variable, l</li>
	 * <li> The speed of the snake, speed_XY</li>
	 * <li> The default length of the snake, length</li>
	 * <li> The number of draws needed to travel the length of a snake body part, num</li>
	 * <li> The snake head buffer used for rotation, newImgSnakeHead</li>
	 * </ul>
	 * <p>
	 *	Is an extended implementation of {@link SnakeObject} where it adds
	 *	functionality specialisation for the snake playable object.
	 *	The general goal of this class is to draw and move the playable
	 *	snake.
	 * </p>
	 */
	public static class MySnake extends SnakeObject implements movable
	{
		// The game changer.
		private int speed_XY;
		private int length;
		private int num; // ?
		public int score = 0;

		private static final BufferedImage IMG_SNAKE_HEAD = (BufferedImage) ImageUtil.images.get("snake-head-right");

		public static List<Point> bodyPoints = new LinkedList<>();

		private static BufferedImage newImgSnakeHead;
		boolean up, down, left, right = true;

		/**
		 * State information set:
		 * <ul>
		 * <li> The x,y coordinates of the snake</li>
		 * <li> The image of the snake body, i</li>
		 * <li> The width and height of the frame, w and h</li>
		 * <li> The game end variable, l</li>
		 * <li> The speed of the snake, speed_XY</li>
		 * <li> The default length of the snake, length</li>
		 * <li> The number of draws needed to travel the length of a snake body part, num</li>
		 * <li> The snake head buffer used for rotation, newImgSnakeHead</li>
		 * </ul>
		 * <p>
		 *     This constructor provides arbitrary default values.
		 *     This constructor's parameters have no default checking.
		 * </p>
		 * @param x This is the x position of the instantiated snake
		 * @param y This is the y position of the instantiated snake
		 */
		public MySnake(int x, int y)
		{
			this.l = true;
			this.x = x;
			this.y = y;
			this.i = ImageUtil.images.get("snake-body");
			this.w = i.getWidth(null);
			this.h = i.getHeight(null);

			this.speed_XY = 5;
			this.length = 1;

			/*
			 * Attention : ?
			 */
			this.num = w / speed_XY;
			newImgSnakeHead = IMG_SNAKE_HEAD;
			System.out.println(this.w+" "+this.num);

		}

		/**
		 * @return returns the length of the snake
		 */
		public int getLength()
		{
			return length;
		}

		public void changeLength(int length)
		{
			this.length = length;
		}

		/**
		 * This is a large class that uses separate cases to check for various
		 * key events, them being up, down, left and right.
		 * Depending on usage, it sets the qualifying boolean to true, and the
		 * others to false.
		 * <p>
		 *     This implementation is highly inefficient.
		 * </p>
		 * @param e The key event derived from the {@link #jFrame}s {@link KeyListener}
		 */
		public void keyPressed(KeyEvent e)
		{
			// check the key
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				if (!down)
				{
					up = true;
					down = false;
					left = false;
					right = false;

					newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -90);
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!up)
				{
					up = false;
					down = true;
					left = false;
					right = false;

					newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, 90);
				}
				break;

			case KeyEvent.VK_LEFT:
				if (!right)
				{
					up = false;
					down = false;
					left = true;
					right = false;

					newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -180);

				}
				break;

			case KeyEvent.VK_RIGHT:
				if (!left)
				{
					up = false;
					down = false;
					left = false;
					right = true;

					newImgSnakeHead = IMG_SNAKE_HEAD;
				}

			default:
				break;
			}
		}


		public void move()
		{
			// láta kvikindið hreyfa sig
			if (up)
			{
				y -= speed_XY;
			} else if (down)
			{
				y += speed_XY;
			} else if (left)
			{
				x -= speed_XY;
			} else if (right)
			{
				x += speed_XY;
			}

		}

		/**
		 * This adds a point up to the length +1 * num. Essentially, if the snake is
		 * of length 1, then it would add a point up to length 9, and then eternally remain
		 * adding and removing point 10.
		 * It adds a point every draw, thus every position in these points will have moved
		 * m pixels either in x or y, where m is {@link #speed_XY}.
		 * <p>
		 *     Functionality on this is explained in {@link #drawBody(Graphics)}.
		 * </p>
		 * @param g The graphic parameter pertaining to {@link MyFrame}
		 */
		@Override
		public void draw(Graphics g)
		{
			outofBounds();
			eatBody();

			bodyPoints.add(new Point(x, y));

			if (bodyPoints.size() == (this.length + 1) * num)
			{
				bodyPoints.remove(0);
			}
			g.drawImage(newImgSnakeHead, x, y, null);
			drawBody(g);

			move();
		}

		/**
		 * Point to make - this is a nested loop!
		 * This functions on the principle that if there exists two
		 * points that intersect, then the body has touched itself.
		 * <p>
		 * The principle itself is simple, but this implementation
		 * is naive.
		 */
		public void eatBody()
		{
			for (Point point : bodyPoints)
			{
				for (Point point2 : bodyPoints)
				{
					if (point.equals(point2) && point != point2)
					{
						this.l = false;
					}
				}
			}
		}

		/**
		 * There are a couple components integral to this drawing system.
		 * First of all, the 'num' variable is essentially what determines
		 * the unit of drawing. The default snake head is of size 25,
		 * and thus as the speed increases, the points will need to be
		 * drawn faster.
		 * <p>
		 *     Moreso, say the snake has a speed of 5. This means on
		 *     every draw, it will travel 5 pixels. So, I will need
		 *     5 draws to travel the equivalent distance of the entire
		 *     snake.
		 *     Ie, 25/5 = 5.
		 * </p>
		 * Every point in the list will be drawn one after another, ie every
		 * point going up will be travelling a unit of distance, determined by
		 * {@link #speed_XY}, further than the previous element.
		 * This makes the very last element the location of the top of the snake,
		 * and every other element below it its location history of every frame rendered,
		 * determined by {@link MyThread}.
		 * <p>
		 *     Thus, if on the assumption that the speed is 5, I will need to go in
		 *     increments of 5 to travel the equivalent of the snakes head. Ie,
		 *     every 5 draws, I will have travelled 25 pixels.
		 *     This entails that on every 5th element, I can draw a snake body part
		 *     without the body parts touching, because every body part is 25 pixels
		 *     in length.
		 *     If the speed increase, it will take fewer draws to travel he 25 pixels,
		 *     thus the value {@link #num} will decrease on the formula:
		 *     <p>
		 *         {@link #w}/{@link #speed_XY}
		 * </p>
		 *
		 * @param g The graphic parameter pertaining to {@link MyFrame}
		 */
		public void drawBody(Graphics g)
		{
			int length = bodyPoints.size() - 1 - num;
			System.out.println(length+" "+bodyPoints.size()+" "+num);
			for (int i = length; i >= num; i -= num)
			{
				Point point = bodyPoints.get(i);
				g.drawImage(this.i, point.x, point.y, null);
			}
		}

		/**
		 * This uses hard-coded bounds for a non-fixed frame!
		 * <p>
		 * Is a simple 'if' that sees if the snake has moved
		 * itself out of bounds.
		 */
		private void outofBounds()
		{
			boolean xOut = (x <= 0 || x >= (870 - w));
			boolean yOut = (y <= 40 || y >= (560 - h));
			if (xOut || yOut)
			{
				l = false;
			}
		}
	}

	/**
	 * The general areas of state information set are:
	 * <ul>
	 * <li> The x,y coordinates of the snake</li>
	 * <li> The image of the snake body</li>
	 * <li> The width and height of the frame</li>
	 * <li> The game end variable, l</li>
	 * </ul>
	 * An abstract class that is implemented by {@link MySnake} and {@link Food}.
	 */
	public abstract static class SnakeObject
	{
		int x;
		int y;
		Image i;
		int w;
		int h;

		public boolean l;


		/**
		 * The abstract draw class is used as a general function implement
		 * drawing, and in this case it is for the snake body and food.
		 * This is called during the {@link Paint} function call of {@link MyFrame},
		 * used whenever repainting and initialisation.
		 * @param g The graphic parameter pertaining to {@link MyFrame}
		 */
		public abstract void draw(Graphics g);

		/**
		 * This is used solely on {@link Food} that implements this abstract class.
		 * Its role is in collision detection when a snake eats a piece of food, using the
		 * rectangle property intersects.
		 * @return Returns a rectangle to be used in collision detection
		 */
		public Rectangle getRectangle()
		{
			return new Rectangle(x, y, w, h);
		}
	}
}
