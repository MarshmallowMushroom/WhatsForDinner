package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yitong on 2/17/18.
 */

public class NewDishActivity extends Activity {

    String ingredientFile = "new_dish_activity_ingredients.txt";
    String recipeFile = "new_dish_activity_recipes.txt";
    Map<String, Recipe> recipes = new HashMap<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdish);
        final ArrayList<String> ingredients = readIngredientsFromFile();
        recipes = readRecipesFromFile();
//        autoComplete1 = findViewById(R.id.autoCompleteTextView1);
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.recipeGroup);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, ingredients);
//        autoComplete1.setAdapter(adapter);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            AutoCompleteTextView childView = (AutoCompleteTextView) viewGroup.getChildAt(i);
            childView.setAdapter(adapter);
        }
        final EditText recipeName = findViewById(R.id.recipeName);
        final EditText cookingDirection = findViewById(R.id.directions);

        Button button = findViewById(R.id.save_new_dish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> ingredientList = new ArrayList<>();
                //save ingredients
                // for loop

                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    AutoCompleteTextView childView = (AutoCompleteTextView) viewGroup.getChildAt(i);
                    String ingredient = childView.getText().toString();
                    if (!ingredients.contains(ingredient)) {
                        ingredients.add(ingredient);
                        saveIngredientsToFile(ingredients);
                    }
                    if (!ingredientList.contains(ingredient)) {
                        ingredientList.add(ingredient);
                    }
                }

                //save recipe object
                String recipeText = recipeName.getText().toString();
                String directionText = cookingDirection.getText().toString();
                Recipe newRecipe = new Recipe(recipeText, ingredientList, directionText);
                if (!recipes.containsKey(recipeText)) {
                    recipes.put(recipeText, newRecipe);
                    saveRecipeToFile(recipes);
                }
            }
        });
    }

    private Map<String, Recipe> readRecipesFromFile() {
        Map<String, Recipe> savedRecipe = new HashMap<>();
        try {
            FileInputStream inputStream = openFileInput(recipeFile);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedRecipe = (Map<String, Recipe>) in.readObject();
            in.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedRecipe;
    }

    private void saveRecipeToFile(Map<String, Recipe> recipes) {
        try {
            FileOutputStream s = openFileOutput(recipeFile, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(s);
            out.writeObject(recipes);
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveIngredientsToFile(ArrayList<String> ingredients) {
        try {
            FileOutputStream s = openFileOutput(ingredientFile, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(s);
            out.writeObject(ingredients);
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<String> readIngredientsFromFile() {
        ArrayList<String> savedArrayList = new ArrayList<>();

        try {
            FileInputStream inputStream = openFileInput(ingredientFile);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedArrayList = (ArrayList<String>) in.readObject();
            in.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedArrayList;
    }
}
