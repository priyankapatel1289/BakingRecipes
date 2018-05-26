package com.example.priyanka.bakingrecipes;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);



    @Before
    public void  recipeListFragment() {
        mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void checkFragmentListView() {
        onView(withId(R.id.frag_list)).check(matches(isDisplayed()));
    }

    @Test
    public void clickListViewItem_OpenDetailActivity() {
        onView(allOf(withId(R.id.tv_recipe__list_rv), withText("Nutella Pie"))).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.rv_fragment_layout)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
