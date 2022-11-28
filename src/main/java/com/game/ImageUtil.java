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
		images.put("snake-head-right",
				GameUtil.getImage("/src/main/resources/assets/textures/snake-head-right.png"));
		images.put("snake-body", GameUtil.getImage("/src/main/resources/assets/textures/snake-body.png"));
		// obstacles
		images.put("0", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-kiwi.png"));
		images.put("1", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-lemon.png"));
		images.put("2", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-litchi.png"));
		images.put("3", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-mango.png"));
		images.put("4", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-apple.png"));
		images.put("5", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-banana.png"));
		images.put("6", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-blueberry.png"));
		images.put("7", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-cherry.png"));
		images.put("8", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-durian.png"));
		images.put("9", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-grape.png"));
		images.put("10",
				GameUtil.getImage("/src/main/resources/assets/textures/Food/food-grapefruit.png"));
		images.put("11", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-peach.png"));
		images.put("12", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-pear.png"));
		images.put("13", GameUtil.getImage("/src/main/resources/assets/textures/Food/food-orange.png"));
		images.put("14",
				GameUtil.getImage("/src/main/resources/assets/textures/Food/food-pineapple.png"));
		images.put("15",
				GameUtil.getImage("/src/main/resources/assets/textures/Food/food-strawberry.png"));
		images.put("16",
				GameUtil.getImage("/src/main/resources/assets/textures/Food/food-watermelon.png"));
		images.put("UI-background",
				GameUtil.getImage("/src/main/resources/assets/textures/UI-background.png"));
		images.put("game-scene-01",
				GameUtil.getImage("/src/main/resources/assets/textures/game-scene-01.jpg"));
	}
}
