package edu.sjsu.yitong.wfdapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by yitong on 2/17/18.
 */

public class EditRecipeActivity extends Activity {

    Recipe r = null;
    Bitmap bitmap = null;

    private int PICK_IMAGE_REQUEST = 1;
    protected List<String> ingredients = new ArrayList<>();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdish);
        String id = getIntent().getExtras().getString("recipeID");

        // get ingredients from recipes
        for (Map.Entry<String,Recipe> entry : SharedRecipes.recipes.entrySet()) {
            for (Ingredient i : entry.getValue().ingredients) {
                if (!ingredients.contains(i.name)) {
                    ingredients.add(i.name);
                }
            }
        }

        r = SharedRecipes.recipes.get(id);
        int s = 0;
        final ViewGroup viewGroup = findViewById(R.id.recipeGroup);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, ingredients);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            AutoCompleteTextView childView = (AutoCompleteTextView) viewGroup.getChildAt(i);
            childView.setAdapter(adapter);
            childView.setThreshold(1);
            if (s < r.ingredients.size()) {
                childView.setText(r.ingredients.get(s).toString());
                s++;
            }

        }
        final EditText recipeName = findViewById(R.id.recipeName);
        recipeName.setText(r.name);
        final EditText cookingDirection = findViewById(R.id.directions);
        cookingDirection.setText(r.cookingDirection);

        ImageView imageView = findViewById(R.id.recipe_img);
        imageView.setImageBitmap(r.bitmap.bitmap);

        ImageButton addImagebtn = findViewById(R.id.add_recipe_btn);
        addImagebtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.activity_new_dish_img_select, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.imageUrlInput);

                // set title
                alertDialogBuilder.setTitle("Choose Image");

                alertDialogBuilder
                        .setMessage("Choose Image file")
                        .setCancelable(false)
                        .setPositiveButton("Local File", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }
                        })
                        .setNegativeButton("Use URL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    URL imgURL = new URL(userInput.getText().toString());
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                    StrictMode.setThreadPolicy(policy);
                                    bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
                                    ImageView imageView = findViewById(R.id.recipe_img);
                                    imageView.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Invalid URL Input, try again", Toast.LENGTH_LONG).show();
                                }
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        Button button = findViewById(R.id.save_new_dish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Ingredient> recipeIngredientList = new ArrayList<>();
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    AutoCompleteTextView childView = (AutoCompleteTextView) viewGroup.getChildAt(i);
                    String ingredient = childView.getText().toString().toLowerCase();
                    if (!ingredients.contains(ingredient)) {
                        ingredients.add(ingredient);
                    }
                    if (ingredient == null || ingredient.length() == 0) {
                        continue;
                    }
                    recipeIngredientList.add(new Ingredient(ingredient));
                }
                //save recipe object
                String directionText = cookingDirection.getText().toString();
                Recipe newRecipe = new Recipe(r.name, bitmap, recipeIngredientList, directionText);
                SharedRecipes.recipes.put(r.name, newRecipe);
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

    public void setRecipeImage(Uri uri) {;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ImageView imageView = findViewById(R.id.recipe_img);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
