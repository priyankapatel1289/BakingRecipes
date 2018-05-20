package com.example.priyanka.bakingrecipes;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void  recipeListFragment() {
        mainActivityActivityTestRule.getActivity()
                .getFragmentManager().beginTransaction();
    }
    @Test
    public void clickListViewItem_OpenDetailActivity() {
        Espresso.onView(withId(R.id.frag_list)).check(matches(isDisplayed()));
    }

}
