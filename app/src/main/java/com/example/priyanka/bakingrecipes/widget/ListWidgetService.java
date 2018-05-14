package com.example.priyanka.bakingrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.priyanka.bakingrecipes.R;
import com.example.priyanka.bakingrecipes.models.IngredientsModel;

import java.util.ArrayList;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewsFactory(this.getApplicationContext(), intent);
    }

    class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();
        private Context mContext;
        private int appWidgetId;
        private static final int mCount = 10;
        private Intent mIntent;

        public ListViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
            mIntent = intent;

        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredientsList == null)
                return 0;
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item_layout);
            views.setTextViewText(R.id.widget_tv_ingredients_quantity, "Quantity");
            views.setTextViewText(R.id.widget_tv_ingredients_measurement, "Measurement");
            views.setTextViewText(R.id.widget_tv_ingredients_ingredient, ingredientsList.get(position).getIngredient());

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(Intent.EXTRA_TEXT, ingredientsList.get(position));
            views.setOnClickFillInIntent(R.id.widget_tv_ingredients_ingredient, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}


