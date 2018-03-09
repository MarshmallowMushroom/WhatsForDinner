package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitong on 2/17/18.
 */

public class GroceriesActivity extends Activity {

    ListView listView;
    // should load the ingredients from all recipes instead of ingredient arraylist
    String ingredientFile = "new_dish_activity_ingredients.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ArrayList<String> ingredients = readIngredientsFromFile();

        listView = (ListView) findViewById(R.id.groceryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, ingredients);
        listView.setAdapter(adapter);
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
