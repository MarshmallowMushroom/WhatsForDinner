package edu.sjsu.yitong.wfdapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitong on 3/9/18.
 */

public class SharedRecipes {
    public static String recipeFile = "new_dish_activity_recipes.txt";
    public static String mealsFile = "new_dish_activity_meals.txt";
    public static Map<String, Recipe> recipes = new HashMap<>(); //all recipes
    public static Map<String, Integer> meals = new HashMap<>(); //all selected meals
}
