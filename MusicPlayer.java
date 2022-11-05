package example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;
import java.awt.Graphics2D;

import javazoom.jl.player.Player;

public class MusicPlayer extends Thread
{
	private String filename;
	public Player player;

	public MusicPlayer(String filename)
	{
		this.filename = filename;
	}

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
					player = new Player(new BufferedInputStream(new FileInputStream(filename)));
					player.play();

				} catch (Exception e)
				{
					System.out.println(e);
				}
			}
		}.start();
	}



	public static void getMusicPlay(String filename)
	{
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		musicPlayer.play();
	}
}
