package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yitong on 2/17/18.
 */

public class GroceriesActivity extends Activity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        TextView title = findViewById(R.id.groceryTitle);
        int numMeals = 0;
        Map<String, Integer> ingre = new HashMap<>();
        Map<String, String> units = new HashMap<>();
        for (String r :SharedRecipes.meals.keySet()) {
            for (Ingredient i :SharedRecipes.recipes.get(r).ingredients) {
                int amount = 0;
                amount += i.amount * SharedRecipes.meals.get(r);
                if (ingre.containsKey(i.name)) {
                    ingre.put(i.name, ingre.get(i.name)+amount);
                } else {
                    ingre.put(i.name, amount);
                    units.put(i.name, i.unit);
                }
            }
            numMeals += SharedRecipes.meals.get(r);
        }
        title.setText("Grocery List for " + numMeals + " Meals");

        listView = findViewById(R.id.groceryList);
        LayoutInflater inflater = getLayoutInflater();
        View list = inflater.inflate(R.layout.item_listview, listView, false);
        SwipeLayout swipeLayout = list.findViewById(R.id.swipe_layout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        List<String> adapterList = new ArrayList<>();
        for (String i: ingre.keySet()) {
            adapterList.add(i + " (" + ingre.get(i) + " " + units.get(i) + ")");
        }

        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.item_listview, adapterList);
        listView.setAdapter(adapter);
    }
}
