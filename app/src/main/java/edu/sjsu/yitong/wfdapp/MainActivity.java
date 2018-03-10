package edu.sjsu.yitong.wfdapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView logo = null;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mTableLayout = findViewById(R.id.main_screen);

        logo = findViewById(R.id.wfdlogo);
        logo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                viewAppInfo(view);
            }
        });

        SharedRecipes.recipes = readRecipesFromFile();
        final ImageView mealButton = findViewById(R.id.meal);
        mealButton.setClickable(true);
        mealButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MealsActivity.class));
            }
        });

        final ImageView recipeButton = findViewById(R.id.recipe);
        recipeButton.setClickable(true);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
            }
        });

        final ImageView groceryButton = findViewById(R.id.grocery);
        groceryButton.setClickable(true);
        groceryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GroceriesActivity.class));

            }
        });

        final ImageView newDishButton = findViewById(R.id.newDish);
        newDishButton.setClickable(true);
        newDishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewDishActivity.class));
            }
        });
    }

    @Override
    protected void onPause(){
        saveRecipeToFile(SharedRecipes.recipes);
        super.onPause();
    }

    /** Called when the user taps the Meal button */
    public void viewAppInfo(View view) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.activity_app_info,null);
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);

        mPopupWindow.showAtLocation(mTableLayout, Gravity.CENTER,0,0);
        // Do something in response to button
    }

    private Map<String, Recipe> readRecipesFromFile() {
        Map<String, Recipe> savedRecipe = new HashMap<>();
        try {
            FileInputStream inputStream = openFileInput(SharedRecipes.recipeFile);
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
            FileOutputStream s = openFileOutput(SharedRecipes.recipeFile, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(s);
            out.writeObject(recipes);
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
