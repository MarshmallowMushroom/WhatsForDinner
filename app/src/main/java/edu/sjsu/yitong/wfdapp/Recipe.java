package edu.sjsu.yitong.wfdapp;

import java.io.Serializable;
import android.net.Uri;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yitong on 3/4/18.
 */

public class Recipe implements Serializable{
    public String name;
    public ArrayList<String> ingredients;
    public String cookingDirection;
    public Uri imageURI;
    public URL imageURL;

    public Recipe(String name, Uri imageURI, URL imageURL, ArrayList<String> ingredients, String cookingDirection) {
        this.name = name;
        this.imageURI = imageURI;
        this.ingredients = ingredients;
        this.cookingDirection = cookingDirection;
        this.imageURL = imageURL;
    }
}
