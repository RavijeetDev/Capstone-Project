package com.ravijeet.teleportal.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.ravijeet.teleportal.TelePortalApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ravijeet on 2/27/18.
 */

public class Utility {

    public static boolean isInternetConnectionAvailable() {

        ConnectivityManager manager = (ConnectivityManager)
                TelePortalApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnected();
    }

    public static String getFormattedDate(String format, String dateString){

        String formattedDate = "";
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date existingDate = dateFormat.parse(dateString);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            formattedDate = simpleDateFormat.format(existingDate);
        } catch (Exception e){

        }

        return formattedDate;
    }


}
