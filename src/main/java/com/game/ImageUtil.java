package com.game;


import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a class containing only static items.
 * This should be remade. Its general job is to contain a hash map of
 * all the images to be used.
 * @author Alfie Rushby-modified
 */
public class ImageUtil
{
	/**
	 * This hash map is set in an unnamed static function. It is executed at
	 * run, and gets all the images in the resources folder in succession.
	 * It does not have any error checking.
	 */
	public static Map<String, Image> images = new HashMap<>();

	static
	{
		// snake
		images.put("snake-head-right", GameUtil.getImage("/snake-head-right.png"));
		images.put("snake-body", GameUtil.getImage("/snake-body.png"));
		// obstacles
		images.put("0", GameUtil.getImage("/Food/food-kiwi.png"));
		images.put("1", GameUtil.getImage("/Food/food-lemon.png"));
		images.put("2", GameUtil.getImage("/Food/food-litchi.png"));
		images.put("3", GameUtil.getImage("/Food/food-mango.png"));
		images.put("4", GameUtil.getImage("/Food/food-apple.png"));
		images.put("5", GameUtil.getImage("/Food/food-banana.png"));
		images.put("6", GameUtil.getImage("/Food/food-blueberry.png"));
		images.put("7", GameUtil.getImage("/Food/food-cherry.png"));
		images.put("8", GameUtil.getImage("/Food/food-durian.png"));
		images.put("9", GameUtil.getImage("/Food/food-grape.png"));
		images.put("10", GameUtil.getImage("/Food/food-grapefruit.png"));
		images.put("11", GameUtil.getImage("/Food/food-peach.png"));
		images.put("12", GameUtil.getImage("/Food/food-pear.png"));
		images.put("13", GameUtil.getImage("/Food/food-orange.png"));
		images.put("14", GameUtil.getImage("/Food/food-pineapple.png"));
		images.put("15", GameUtil.getImage("/Food/food-strawberry.png"));
		images.put("16", GameUtil.getImage("/Food/food-watermelon.png"));
		images.put("UI-background", GameUtil.getImage("/UI-background.png"));
		images.put("game-scene-01", GameUtil.getImage("/game-scene-01.jpg"));
	}
}
