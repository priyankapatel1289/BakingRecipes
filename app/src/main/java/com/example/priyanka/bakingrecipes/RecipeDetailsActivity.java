package com.example.priyanka.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.priyanka.bakingrecipes.models.IngredientsModel;
import com.example.priyanka.bakingrecipes.models.StepsModel;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private String name;
    private ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();
    private ArrayList<StepsModel> stepsList = new ArrayList<>();
    private ArrayList<StepsModel> videoUrlList = new ArrayList<>();

    private int position;

    private StepsFragment stepsFragment;
    private IngredientsFragment ingredientsFragment;
    private VideoInstructionsFragment videoInstructionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Steps"));
        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ingredientsFragment = new IngredientsFragment();
        stepsFragment = new StepsFragment();
        videoInstructionsFragment = new VideoInstructionsFragment();

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("tabPosition");
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    position = tab.getPosition();
                    setCurrentTabFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("data")) {
                Bundle bundle = intent.getBundleExtra("data");
                ingredientsList = bundle.getParcelableArrayList("ingredients");
                stepsList = bundle.getParcelableArrayList("steps");
                videoUrlList = bundle.getParcelableArrayList("videoUrl");

                ingredientsFragment.setIngredientsList(ingredientsList);
                stepsFragment.setStepsList(stepsList);
                videoInstructionsFragment.setVideoUrlList(videoUrlList);

                name = bundle.getString("name");
                replaceFragment(ingredientsFragment);
                setTitle(name);
                Log.v("TAG", "VALUE OF INGREDIENTS IS: " + videoUrlList);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tabPosition", position);
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        position = savedInstanceState.getInt("tabPosition");
//    }

    public void replaceFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_details, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
    }

    public void setCurrentTabFragment(int tabPosition) {
        switch (tabPosition) {
            case 0:
                replaceFragment(ingredientsFragment);
                break;
            case 1:
                replaceFragment(stepsFragment);
                break;
            case 2:
                replaceFragment(videoInstructionsFragment);
                break;
        }
    }

}
