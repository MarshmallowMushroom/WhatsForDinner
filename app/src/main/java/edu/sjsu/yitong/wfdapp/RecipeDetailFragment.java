package edu.sjsu.yitong.wfdapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yitong on 3/6/18.
 */

public class RecipeDetailFragment extends Fragment {
    protected Recipe mRecipe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mRecipe = (Recipe) savedInstanceState.getSerializable("curID");
        }

        if (this.getActivity().getIntent() != null) {
            Recipe r = (Recipe) this.getActivity().getIntent().getSerializableExtra("curID");

            if (r != null) {
                mRecipe = r;
            }
        }

        if (mRecipe != null) {
            load(mRecipe);
        }
    }

    public void load(Recipe recipe) {
        mRecipe = recipe;

        if (recipe != null) {
            // load views with Buzz object data
            setText(recipe.name);
        }
    }

    public void setText(String text) {
        TextView view = getView().findViewById(R.id.recipeDetailName);
        view.setText(text);
    }

}
