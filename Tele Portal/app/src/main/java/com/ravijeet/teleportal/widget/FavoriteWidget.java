package com.ravijeet.teleportal.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.ravijeet.teleportal.R;


/**
 * Created by Ravijeet on 3/12/18.
 */

public class FavoriteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_tv_show_widget);

        Intent tvShowListIntent = new Intent(context, FavoriteWidgetIntentService.class);
        tvShowListIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        tvShowListIntent.setData(Uri.parse(tvShowListIntent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.tv_show_list, tvShowListIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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
