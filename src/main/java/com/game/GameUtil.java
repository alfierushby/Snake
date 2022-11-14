package com.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * This class should not contain static functions.
 * This class contains the image functions uses to get images from the resources
 * folder, and to rotate them when the snake changes direction.
 * @author Alfie Rushby-modified
 */
public class GameUtil
{
	/**
	 * This gets the resource dependent on the image path. This has proper error
	 * checking with exceptions, and catches the exception within itself.
	 * @param imagePath The path of the image relative to the resources folder
	 * @return returns the Image object that can be drawn
	 */
	public static Image getImage(String imagePath)
	{
		URL u = GameUtil.class.getClassLoader().getResource(imagePath);
		BufferedImage i = null;
		try
		{
			i = ImageIO.read(u);
		} catch (Exception e)
		{
			System.err.println("VILLA : FINN EKKI TILTEKNA MYNDIN !\n");
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * This rotates the buffered image to the set degree.
	 * It does this by first creating a new graphic, setting its height, width
	 * and transparency to the same as the image we are rotating.
	 * It does this with a new buffered image i that hasn't had anything
	 * drawn on it.
	 * It is then rotated, and then has the buffered image, i, set to the image
	 * we are rotating, bufferedImage, via drawImage which draws the input image
	 * onto the image in the graphic.
	 * @param bufferedImage An image object that can be edited
	 * @param degree The degree at which it rotates
	 * @return The rotated image
	 */
	public static Image rotateImage(final BufferedImage bufferedImage, final int degree)
	{
	int w = bufferedImage.getWidth();
	int h = bufferedImage.getHeight();
	int t = bufferedImage.getColorModel().getTransparency();

	BufferedImage i;
	Graphics2D graphics2d;

	(graphics2d = (i = new BufferedImage(w, h, t)).createGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

	graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
	graphics2d.drawImage(bufferedImage, 0, 0, null);
	graphics2d.dispose();

	return i;

	}
}
