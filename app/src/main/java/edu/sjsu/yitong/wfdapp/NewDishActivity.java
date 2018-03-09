package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Uri imgURI = Uri.parse("android.resource://edu.sjsu.yitong.wfdapp/drawable/default_food");

    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdish);
        final ArrayList<String> ingredients = readIngredientsFromFile();
        recipes = readRecipesFromFile();
        final ViewGroup viewGroup = findViewById(R.id.recipeGroup);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, ingredients);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            AutoCompleteTextView childView = (AutoCompleteTextView) viewGroup.getChildAt(i);
            childView.setAdapter(adapter);
            childView.setThreshold(1);
        }
        final EditText recipeName = findViewById(R.id.recipeName);
        final EditText cookingDirection = findViewById(R.id.directions);
        setRecipeImage(imgURI);

        ImageButton addImagebtn = findViewById(R.id.add_recipe_btn);
        addImagebtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });



        Button button = findViewById(R.id.save_new_dish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeText = recipeName.getText().toString().toLowerCase();
                if (recipes.containsKey(recipeText)) {
                    Toast.makeText(getApplicationContext(), "Recipe name already exists", Toast.LENGTH_LONG).show();
                    return;
                }

                ArrayList<String> ingredientList = new ArrayList<>();
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    AutoCompleteTextView childView = (AutoCompleteTextView) viewGroup.getChildAt(i);
                    String ingredient = childView.getText().toString().toLowerCase();
                    if (!ingredients.contains(ingredient)) {
                        ingredients.add(ingredient);
                    }
                    ingredientList.add(ingredient);
                }
                //save all new ingredients
                saveIngredientsToFile(ingredients);
                //save recipe object
                String directionText = cookingDirection.getText().toString();
                Recipe newRecipe = new Recipe(recipeText, imgURI, ingredientList, directionText);
                if (!recipes.containsKey(recipeText)) {
                    recipes.put(recipeText, newRecipe);
                    saveRecipeToFile(recipes);
                }
                Toast.makeText(getApplicationContext(), "Recipe Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            setRecipeImage(uri);
        }
    }

    private void setRecipeImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // Log.d(TAG, String.valueOf(bitmap));

            ImageView imageView = findViewById(R.id.recipe_img);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
