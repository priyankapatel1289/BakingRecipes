package com.example.priyanka.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.priyanka.bakingrecipes.models.IngredientsModel;
import com.example.priyanka.bakingrecipes.models.StepsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {
    private String name;
    private ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();
    private ArrayList<StepsModel> stepsList = new ArrayList<>();
    private ArrayList<StepsModel> videoUrlList = new ArrayList<>();
    private String STEPS_FRAGMENT_INSTANCE = "steps_fragment_instance";
    private String INGREDIENTS_FRAGMENT_INSTANCE = "ingredients_fragment_instance";

    private StepsFragment stepsFragment;
    private IngredientsFragment ingredientsFragment;
    private VideoInstructionsFragment videoInstructionsFragment;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);
        ingredientsFragment = new IngredientsFragment();
        stepsFragment = new StepsFragment();
        videoInstructionsFragment = new VideoInstructionsFragment();

        if (savedInstanceState!= null) {
            if (savedInstanceState.containsKey(STEPS_FRAGMENT_INSTANCE)) {
                stepsFragment = (StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState, STEPS_FRAGMENT_INSTANCE);
            } else if (savedInstanceState.containsKey(INGREDIENTS_FRAGMENT_INSTANCE)) {
                ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().getFragment(savedInstanceState, INGREDIENTS_FRAGMENT_INSTANCE);
            }
        }

        tabLayout.addTab(tabLayout.newTab().setText(R.string.ingredients));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.steps));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.video_instructions));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
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
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (stepsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, STEPS_FRAGMENT_INSTANCE, stepsFragment);
        }
        if (ingredientsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, INGREDIENTS_FRAGMENT_INSTANCE, ingredientsFragment);
        }
    }

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
