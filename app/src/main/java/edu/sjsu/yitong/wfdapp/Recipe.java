package edu.sjsu.yitong.wfdapp;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yitong on 3/4/18.
 */

public class Recipe implements Serializable{
    public String name;
    public ArrayList<Ingredient> ingredients;
    public String cookingDirection;
    public SerializableBitMap bitmap = new SerializableBitMap();

    public Recipe(String name, Bitmap bitmap, ArrayList<Ingredient> ingredients, String cookingDirection) {
        this.name = name;
        this.ingredients = ingredients;
        this.cookingDirection = cookingDirection;
        this.bitmap.bitmap = bitmap;
    }
}
