package com.example.priyanka.bakingrecipes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private int numberOfTabs;

    public DetailsPagerAdapter(FragmentManager fm, int mNumberOfTabs, Context mContext) {
        super(fm);
        this.numberOfTabs = mNumberOfTabs;
        this.context = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IngredientsFragment();
            case 1:
                return new StepsFragment();
            case 2:
                return new VideoInstructionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.ingredient);
            case 1:
                return context.getString(R.string.steps);
            case 2:
                return context.getString(R.string.video_instructions);
        }
        return null;

    }
}
