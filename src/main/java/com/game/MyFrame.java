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
 * @author Alfie Rushby
 * @version 1.0
 */
public class MyFrame extends JPanel implements KeyListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3149926831770554380L;

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
	 * <li> The size
	 * <li> The title
	 * <li> The KeyListener
	 * <li> The visibility
	 * <li> The close event in a WindowListener
	 * </ul>
	 *
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
	 *
	 * </p>
	 */
	public static class MySnake extends SnakeObject implements movable
	{
		// Leikjabreytan.
		private int speed_XY;
		private int length;
		private int num; // ?
		public int score = 0;

		private static final BufferedImage IMG_SNAKE_HEAD = (BufferedImage) ImageUtil.images.get("snake-head-right");

		public static List<Point> bodyPoints = new LinkedList<>();

		private static BufferedImage newImgSnakeHead;
		boolean up, down, left, right = true;

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

		}

		public int getLength()
		{
			return length;
		}

		public void changeLength(int length)
		{
			this.length = length;
		}

		public void keyPressed(KeyEvent e)
		{
			// athugaðu lykilinn
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

		public void drawBody(Graphics g)
		{
			int length = bodyPoints.size() - 1 - num;

			for (int i = length; i >= num; i -= num)
			{
				Point point = bodyPoints.get(i);
				g.drawImage(this.i, point.x, point.y, null);
			}
		}

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

	public abstract static class SnakeObject
	{
		int x;
		int y;
		Image i;
		int w;
		int h;

		public boolean l;


		public abstract void draw(Graphics g);

		public Rectangle getRectangle()
		{
			return new Rectangle(x, y, w, h);
		}
	}
}
