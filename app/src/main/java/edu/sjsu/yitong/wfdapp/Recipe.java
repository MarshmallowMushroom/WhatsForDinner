package edu.sjsu.yitong.wfdapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yitong on 3/4/18.
 */

public class Recipe implements Serializable{
    public String name;
    public ArrayList<String> ingredients;
    public String cookingDirection;

    public Recipe(String name, ArrayList<String> ingredients, String cookingDirection) {
        this.name = name;
        this.ingredients = ingredients;
        this.cookingDirection = cookingDirection;
    }



}
