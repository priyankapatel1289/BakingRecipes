package com.example.priyanka.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.priyanka.bakingrecipes.models.IngredientsModel;
import com.example.priyanka.bakingrecipes.models.StepsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsFragment.VideoClickListener{
    private String name;
    private String STEPS_FRAGMENT_INSTANCE = "steps_fragment_instance";
    private String INGREDIENTS_FRAGMENT_INSTANCE = "ingredients_fragment_instance";
    private String VIDEO_FRAGMENT_INSTANCE = "video_fragment_instance";

    private StepsFragment stepsFragment;
    private IngredientsFragment ingredientsFragment;
    private VideoInstructionsFragment videoInstructionsFragment;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabToolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ingredientsFragment = new IngredientsFragment();
        stepsFragment = new StepsFragment();
        videoInstructionsFragment = new VideoInstructionsFragment();

        if (savedInstanceState!= null) {
            if (savedInstanceState.containsKey(STEPS_FRAGMENT_INSTANCE)) {
                stepsFragment = (StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState, STEPS_FRAGMENT_INSTANCE);
            } else if (savedInstanceState.containsKey(INGREDIENTS_FRAGMENT_INSTANCE)) {
                ingredientsFragment = (IngredientsFragment) getSupportFragmentManager().getFragment(savedInstanceState, INGREDIENTS_FRAGMENT_INSTANCE);
            } else if (savedInstanceState.containsKey(VIDEO_FRAGMENT_INSTANCE)) {
                videoInstructionsFragment = (VideoInstructionsFragment) getSupportFragmentManager().getFragment(savedInstanceState, VIDEO_FRAGMENT_INSTANCE);
            }
        }

        tabLayout.addTab(tabLayout.newTab().setText(R.string.ingredients));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.steps));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.video_instructions));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        DetailsPagerAdapter detailsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(detailsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
                ArrayList<IngredientsModel> ingredientsList = bundle.getParcelableArrayList("ingredients");
                ArrayList<StepsModel> stepsList = bundle.getParcelableArrayList("steps");
                ArrayList<StepsModel> videoUrlList = bundle.getParcelableArrayList("videoUrl");

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
        if (videoInstructionsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, VIDEO_FRAGMENT_INSTANCE, videoInstructionsFragment);
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

    @Override
    public void videoClicked(StepsModel model) {
        Bundle bundle = new Bundle();
        bundle.putString("videoURL", model.getVideoURL());
        VideoInstructionsFragment videoInstructionsFragment = new VideoInstructionsFragment();
        videoInstructionsFragment.setArguments(bundle);
        replaceFragment(videoInstructionsFragment);
    }
}
