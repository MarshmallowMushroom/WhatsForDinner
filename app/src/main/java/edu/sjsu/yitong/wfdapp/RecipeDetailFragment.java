package edu.sjsu.yitong.wfdapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

/**
 * Created by yitong on 3/6/18.
 */

public class RecipeDetailFragment extends Fragment {
    protected String recipeName;
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
            recipeName =  savedInstanceState.getString("curID");
        }

        if (this.getActivity().getIntent() != null) {
            String r = (String) this.getActivity().getIntent().getStringExtra("curID");

            if (r != null) {
                recipeName = r;
            }
        }

        if (recipeName != null) {
            setDetails(recipeName);
        }
    }

    public void setDetails(String name) {
        Recipe recipe = SharedRecipes.recipes.get(name);
        TextView view = getView().findViewById(R.id.recipeDetailName);
        view.setText(name);

        ImageView imageView = getView().findViewById(R.id.recipeDetailImage);
        imageView.setImageBitmap(recipe.bitmap.bitmap);

        ListView lv = getView().findViewById(R.id.recipeDetailIngredientList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getView().getContext(), android.R.layout.simple_list_item_1, recipe.ingredients);
        lv.setAdapter(adapter);

        TextView directions = getView().findViewById(R.id.recipeDetailCookingDirection);
        directions.setText(recipe.cookingDirection);
    }
}
