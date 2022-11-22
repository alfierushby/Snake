package com.game;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * This class currently manages the Swing frame that holds the game elements.
 * <p>
 *     This method is not instantiated directly.
 *     It holds core game logic, including move events, and eating itself/going out of bounds.
 * </p>
 * @author Alfie Rushby-modified
 * @version 1.0
 */
public class MyFrame extends JPanel
{
	private static final long serialVersionUID = -3149926831770554380L;
	final int X = 870, Y=560;

	/**
	 * @return returns frame time of game
	 */
	public int getFrame_time() {
		return m_frame_time;
	}
	public JFrame getjFrame() {
		return m_jFrame;
	}

	private final int m_frame_time = 30;
	/**
	 *
	 */
	//private static final long serialVersionUID = -3149926831770554380L;


	/**
	 * This is the highest level container, and contains the JPanel {@link MyFrame}
	 */
	private final JFrame m_jFrame = new JFrame();

	/**
	 * The default constructor.
	 * It sets the icon of the game window, reference {@link #m_jFrame}.
	 */
	public MyFrame()
	{
		m_jFrame.setIconImage(Toolkit.getDefaultToolkit().
				getImage(MyFrame.class.getResource("/src/main/resources/assets/textures/snake-logo.png"))); // Fix whatever the reason this needs a '/'.
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
	 * JFrame {@link #m_jFrame}, and sets the functions implemented by {@link KeyListener} to
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
		m_jFrame.add(this);

		m_jFrame.setTitle("Snakee Yipee");
		m_jFrame.setSize(870, 560);
		m_jFrame.setLocationRelativeTo(null);
		m_jFrame.addWindowListener(new WindowAdapter()// loka
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				System.exit(0);
			}
		});
		m_jFrame.setVisible(true);

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
					sleep(m_frame_time);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
