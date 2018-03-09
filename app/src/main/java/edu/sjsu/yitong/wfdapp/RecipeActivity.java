package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
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

public class RecipeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }
}

//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("names", recipeNames);
//        RecipeListFragment listFragment = new RecipeListFragment();
//        listFragment.setArguments(bundle);
////        listView = (ListView) findViewById(R.id.recipeList);
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, recipeNames);
////        listView.setAdapter(adapter);
//
////        Configuration config = getResources().getConfiguration();
////        FragmentManager fragmentManager = getFragmentManager();
////        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////
////        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
////            LandscapeFragment landscapeFragment = new LandscapeFragment();
////            fragmentTransaction.replace(android.R.id.content, landscapeFragment);
////        } else {
////
////        }
//
//        // find the retained fragment on activity restarts
//
//
//    }
//
//
//}
