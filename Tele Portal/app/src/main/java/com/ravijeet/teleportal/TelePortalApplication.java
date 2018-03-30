package com.ravijeet.teleportal;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ravijeet on 2/27/18.
 */

public class TelePortalApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
