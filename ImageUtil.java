package example;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil
{
	public static Map<String, Image> images = new HashMap<>();

	static
	{
		// snake
		images.put("snake-head-right", GameUtil.getImage("example/snake-head-right.png"));
		images.put("snake-body", GameUtil.getImage("example/snake-body.png"));
		// obstacles
		images.put("0", GameUtil.getImage("example/food-kiwi.png"));
		images.put("1", GameUtil.getImage("example/food-lemon.png"));
		images.put("2", GameUtil.getImage("example/food-litchi.png"));
		images.put("3", GameUtil.getImage("example/food-mango.png"));
		images.put("4", GameUtil.getImage("example/food-apple.png"));
		images.put("5", GameUtil.getImage("example/food-banana.png"));
		images.put("6", GameUtil.getImage("example/food-blueberry.png"));
		images.put("7", GameUtil.getImage("example/food-cherry.png"));
		images.put("8", GameUtil.getImage("example/food-durian.png"));
		images.put("9", GameUtil.getImage("example/food-grape.png"));
		images.put("10", GameUtil.getImage("example/food-grapefruit.png"));
		images.put("11", GameUtil.getImage("example/food-peach.png"));
		images.put("12", GameUtil.getImage("example/food-pear.png"));
		images.put("13", GameUtil.getImage("example/food-orange.png"));
		images.put("14", GameUtil.getImage("example/food-pineapple.png"));
		images.put("15", GameUtil.getImage("example/food-strawberry.png"));
		images.put("16", GameUtil.getImage("example/food-watermelon.png"));
		images.put("UI-background", GameUtil.getImage("example/UI-background.png"));
		images.put("game-scene-01", GameUtil.getImage("example/game-scene-01.jpg"));
	}
}
