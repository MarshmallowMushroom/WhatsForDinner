package edu.sjsu.yitong.wfdapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitong on 3/6/18.
 */

public class RecipeListFragment extends Fragment implements AdapterView.OnItemClickListener {
    protected String mCurrentId;
    protected ListView mListView;
    protected Boolean mDualPane;

    Map<String, Recipe> recipes = new HashMap();
    ArrayList<String> recipeNames = new ArrayList<>();
    String recipeFile = "new_dish_activity_recipes.txt";

    // a bundle object we want to retain
//    private Bundle mydata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, parent, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recipes = readRecipesFromFile();

        for (String key : recipes.keySet()) {
            recipeNames.add(key);
        }

        this.mListView = getActivity().findViewById(R.id.listNames);

        if (this.mListView != null) {
            this.mListView.setOnItemClickListener(this);
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
            // get matching recipe id from position
            String buzzId = recipeNames.get(position);
            showDetails(buzzId);
        }
    }

    public void showDetails(String id) {
        if (id != null && !id.isEmpty() && recipes != null && !recipes.isEmpty()) {
            // find Recipe object
            Recipe currRecipe = recipes.get(id);

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
                    details.load(currRecipe);
                } else {
                    Toast.makeText(getActivity(), "singlepane", Toast.LENGTH_LONG).show();
                    // launch new activity because we're in single mode pane
//                    Intent showContent = new Intent(getActivity(), HoneybuzzDetailsActivity.class);
//                    showContent.putExtra(HoneybuzzDetailsActivity.EXTRA_BUZZ, buzz);
//                    startActivity(showContent);
                }
            }
        }
    }

    private Map<String, Recipe> readRecipesFromFile() {
        Map<String, Recipe> savedRecipe = new HashMap<>();
        try {
            FileInputStream inputStream = getActivity().openFileInput(recipeFile);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedRecipe = (Map<String, Recipe>) in.readObject();
            in.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedRecipe;
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // set the retain instance flag
//        setRetainInstance(true);
//    }
//
//    public void setBundle(Bundle data) {
//        this.mydata = data;
//    }
//
//    public Bundle getBundle() {
//        return mydata;
//    }
}
