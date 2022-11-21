package com.game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Random;
import java.awt.Graphics2D;

import javazoom.jl.player.Player;

/**
 * This class has the role of playing music, inputted via its constructor.
 * Not entirely sure why it extends Thread.
 * @author Alfie Rushby-modified
 */
public class MusicPlayer extends Thread
{
	private String filename;
	private Player player;

	/**
	 * Sets the music to be played on game start.
	 * @param filename the file name of the music
	 */
	public MusicPlayer(String filename)
	{
		this.filename = filename;
	}

	/**
	 * When this is called, it starts a thread that loops forever in a cycle
	 * of playing the music file. Ie, when it ends, it restarts playing.
	 */
	public void play()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					//BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
					player = new Player(Objects.requireNonNull(this.getClass().
							getResourceAsStream(filename)));
					player.play();

				} catch (Exception e)
				{
					System.out.println(e);
				}
			}
		}.start();
	}


	/**
	 * This is a static methoc call that creates and then plays the music
	 * file.
	 * This is poor design. Use a factory or something.
	 * @param filename Name of the music file.
	 */
	public static void getMusicPlay(String filename)
	{
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		musicPlayer.play();
	}
}
