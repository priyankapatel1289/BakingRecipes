package com.example.priyanka.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.priyanka.bakingrecipes.models.RecipeModel;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.RecipeListListener {

    public static String BROADCAST_INTENT_EXTRA = "widget_intent_extra";
    public static String BROADCAST_BUNDLE_EXTRA = "widget_bundle_extra";
    public static String WIDGET_INGREDIENT_SHAREDPREF = "widget_ingredientsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void itemClicked(RecipeModel model) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
//            StepsFragment stepsFragment = new StepsFragment();
//            VideoInstructionsFragment videoInstructionsFragment = new VideoInstructionsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ingredientsFragment.setIngredientsList(model.getIngredients());
//            stepsFragment.setStepsList(model.getSteps());
//            videoInstructionsFragment.setVideoUrlList(model.getVideoUrl());
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ingredients", model.getIngredients());
            bundle.putParcelableArrayList("steps", model.getSteps());
            bundle.putParcelableArrayList("videoUrl", model.getVideoUrl());

            bundle.putString("name", model.getName());
            intent.putExtra("data",bundle);
            startActivity(intent);

            Gson gson = new Gson();
            String jsonIngredients = gson.toJson(model.getIngredients());
            Log.v("MAIN ACTIVITY GSON", "GSON CONVERSION " + jsonIngredients);
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(WIDGET_INGREDIENT_SHAREDPREF, jsonIngredients);
            editor.apply();

/*            Intent broadcastIntent = new Intent();
//            broadcastIntent.setAction()
            Bundle broadcastBundle = new Bundle();
            bundle.putParcelableArrayList(BROADCAST_BUNDLE_EXTRA, model.getIngredients());

            intent.putExtra(BROADCAST_INTENT_EXTRA, broadcastBundle);
            sendBroadcast(broadcastIntent);*/

        }
    }
}
