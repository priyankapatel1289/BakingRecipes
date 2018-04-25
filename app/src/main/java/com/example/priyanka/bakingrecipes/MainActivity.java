package com.example.priyanka.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.priyanka.bakingrecipes.models.RecipeModel;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.RecipeListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void itemClicked(RecipeModel model) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ingredientsFragment.setIngredientsList(model.getIngredients());
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
            Log.v("TAG", "ANOTHER THING --------------------- " + model.getIngredients());
            startActivity(intent);
        }
    }
}
