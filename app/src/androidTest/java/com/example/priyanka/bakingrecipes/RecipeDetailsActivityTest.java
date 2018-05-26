package com.example.priyanka.bakingrecipes;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> recipeDetailsActivityActivityTestRule
            = new ActivityTestRule<>(RecipeDetailsActivity.class);

    @Before
    public void setUp() {
        RecipeDetailsActivity mRecipeDetailsActivity = recipeDetailsActivityActivityTestRule.getActivity();
        assertThat(mRecipeDetailsActivity, notNullValue());
    }

    @Test
    public void checkTabLayoutDisplayed() {
        onView(withId(R.id.tab_layout))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkFragmentDisplayed() {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        recipeDetailsActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout_details, ingredientsFragment).commit();

    }

}
