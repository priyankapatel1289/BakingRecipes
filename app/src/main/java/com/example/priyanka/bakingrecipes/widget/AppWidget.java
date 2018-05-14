package com.example.priyanka.bakingrecipes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.priyanka.bakingrecipes.MainActivity;
import com.example.priyanka.bakingrecipes.R;
import com.example.priyanka.bakingrecipes.models.IngredientsModel;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();

//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
////        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.hasExtra(MainActivity.BROADCAST_INTENT_EXTRA)) {
            Bundle bundle = intent.getBundleExtra(MainActivity.BROADCAST_INTENT_EXTRA);
            ingredientsList = bundle.getParcelableArrayList(MainActivity.BROADCAST_BUNDLE_EXTRA);

            Log.v("APP WIDGET", "ingredientsList is " + ingredientsList);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // There may be multiple widgets active, so update all of them
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, ListWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.setRemoteAdapter(R.id.lv_widget, intent);

            views.setEmptyView(R.id.lv_widget, R.id.widget_empty_text);

            Intent emptyTextIntent = new Intent(context, MainActivity.class);
            PendingIntent emptyTextPendingIntent = PendingIntent.getActivity(context, 0, emptyTextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_empty_text, emptyTextPendingIntent);

            Intent templateIntent = new Intent(context, MainActivity.class);
            templateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent templatePendingIntent =
                    PendingIntent.getActivity(context, 0, templateIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.lv_widget, templatePendingIntent);


            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

