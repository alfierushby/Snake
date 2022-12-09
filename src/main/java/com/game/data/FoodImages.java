package com.game.data;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Class that contains an index of all the food images.
 * @author Alfie Rushby-modified
 */
public class FoodImages {
    /**
     * @return Gets the mapping of food images
     */
    public Map<Integer, String> getFoodImages() {
        return m_Food;
    }

    /**
     * @param index Index of a specific food image
     * @return The food image, or the 0th food image if the index is wrong
     */
    public String getFoodindex(int index) {
        if(index < 0 || index > getFoodImages().size()){
            System.out.println("Tried to access non-existent index!");
            index=0;
        }
        return getFoodImages().get(index);
    }


    private final Map<Integer, String> m_Food
            = Map.ofEntries(
            entry(0,"Food/food-kiwi.png"),
            entry(1, "Food/food-lemon.png"),
            entry(2,"Food/food-litchi.png"),
            entry(3, "Food/food-mango.png"),
            entry(4,"Food/food-apple.png"),
            entry(5, "Food/food-banana.png"),
            entry(6,"Food/food-blueberry.png"),
            entry(7, "Food/food-cherry.png"),
            entry(8,"Food/food-durian.png"),
            entry(9, "Food/food-grape.png"),
            entry(10,"Food/food-grapefruit.png"),
            entry(11, "Food/food-peach.png"),
            entry(12,"Food/food-pear.png"),
            entry(13, "Food/food-orange.png"),
            entry(14, "Food/food-pineapple.png"),
            entry(15, "Food/food-strawberry.png"),
            entry(16, "Food/food-watermelon.png")
    );
}
