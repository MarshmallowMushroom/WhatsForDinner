package edu.sjsu.yitong.wfdapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yitong on 3/6/18.
 */

public class RecipeListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    protected String mCurrentId;
    protected ListView mListView;
    protected Boolean mDualPane;

    List<String> recipeNames = new ArrayList<>();
    int mealCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, parent, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (String key : SharedRecipes.meals.keySet()) {
            mealCount += SharedRecipes.meals.get(key);
        }
        TextView t = getView().findViewById(R.id.recipeTitleWithCount);
        t.setText("Recipe (" + mealCount + " Meal(s))");

        for (String key : SharedRecipes.recipes.keySet()) {
            recipeNames.add(key);
        }
        this.mListView = getActivity().findViewById(R.id.listNames);

        if (this.mListView != null) {
            this.mListView.setOnItemClickListener(this);
            this.mListView.setOnItemLongClickListener(this);
            this.mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, recipeNames);
            this.mListView.setAdapter(adapter);
        }

        // check if we are in dual pane mode
        View detailsFrame = getActivity().findViewById(R.id.recipeDetail);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        // get current id from saved state or extras
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurrentId = savedInstanceState.getString("curID");
        }

        if (this.getActivity().getIntent() != null) {
            String id = this.getActivity().getIntent().getStringExtra("curID");
            if (id != null && !id.isEmpty()) {
                mCurrentId = id;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("curID", mCurrentId);
    }


    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        if (position < recipeNames.size()) {
            String recipeId = recipeNames.get(position);
            showDetails(recipeId);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
        if (position < recipeNames.size()) {
            String recipeId = recipeNames.get(position);
            showEditFragment(recipeId);
        }
        return true;
    }

    public void showEditFragment(String id) {
        Intent intent  = new Intent(getActivity(), EditRecipeActivity.class);
        Bundle b = new Bundle();
        b.putString("recipeID", id);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void showDetails(String id) {
        if (id != null && !id.isEmpty() && SharedRecipes.recipes != null && !SharedRecipes.recipes.isEmpty()) {
            // find Recipe object
            Recipe currRecipe = SharedRecipes.recipes.get(id);

            if (currRecipe != null) {
                mCurrentId = currRecipe.name;

                // highlight selected item
                int index = recipeNames.indexOf(mCurrentId);

                if (index >= 0) {
                    mListView.setItemChecked(index, true);
                }

                // load item
                if (mDualPane) {
                    RecipeDetailFragment details = (RecipeDetailFragment) getFragmentManager().findFragmentById(R.id.recipeDetail);
                    details.setDetails(currRecipe.name);
                } else {
                    if (SharedRecipes.meals.containsKey(id)) {
                        SharedRecipes.meals.put(id, SharedRecipes.meals.get(id) + 1);
                    } else {
                        SharedRecipes.meals.put(id, 1);
                    }
                    mealCount++;
                    TextView t = getView().findViewById(R.id.recipeTitleWithCount);
                    t.setText("Recipe (" + mealCount + " Meal(s))");
                }
            }
        }
    }
}
