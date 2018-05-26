package com.example.priyanka.bakingrecipes;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.priyanka.bakingrecipes.models.RecipeModel;
import com.example.priyanka.bakingrecipes.widget.AppWidget;
import com.google.gson.Gson;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.RecipeListListener {

    public static String WIDGET_INGREDIENT_SHAREDPREF = "widget_ingredientsList";
    public RecipesListFragment recipesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesListFragment = new RecipesListFragment();

        ButterKnife.bind(this);
    }

    public void itemClicked(RecipeModel model) {

        Gson gson = new Gson();
        String jsonIngredients = gson.toJson(model.getIngredients());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WIDGET_INGREDIENT_SHAREDPREF, jsonIngredients);
        editor.apply();
        sendBroadcast();

        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            StepsFragment stepsFragment = new StepsFragment();
            VideoInstructionsFragment videoInstructionsFragment = new VideoInstructionsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ingredientsFragment.setIngredientsList(model.getIngredients());
            stepsFragment.setStepsList(model.getSteps());
            videoInstructionsFragment.setVideoUrlList(model.getVideoUrl());
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.replace(R.id.fragment_container_steps, stepsFragment);
            fragmentTransaction.replace(R.id.fragment_container_video, videoInstructionsFragment);
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
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent(this, AppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] ids = widgetManager.getAppWidgetIds(new ComponentName(this, AppWidget.class));
        widgetManager.notifyAppWidgetViewDataChanged(ids, R.id.lv_widget);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids);
        sendBroadcast(intent);
    }
}
