/*
 * Copyright (c)  2020. Indev Consultancy Private Limited,
 * Auther : Vimal Kumar
 * Date : 2020/12/15
 * Modified Date :
 * Modified By :
 */

package com.anshu.chatapp.Utills;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;

import androidx.appcompat.app.AlertDialog;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommonClass {
    public static void setPopupForStopSurvey(Context context) {
        new AlertDialog.Builder(context).setTitle("Alert!")
                .setMessage("Are you sure to want stop survey.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //TODO here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //TODO here
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    public static boolean isInternetOn(Context c) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showPopupForNoInternet(Context context) {
        new AlertDialog.Builder(context).setTitle("Alert!")
                .setMessage("Network Error, Please check internet connection.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //TODO here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //TODO here
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    public static boolean screenTimeOutStatus(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            return pm.isScreenOn();
        }
    }


    public static void CallLanguage(String languageToLoad, Context context) {

        if (!languageToLoad.equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = context.getResources().getConfiguration();
                //config.locale = locale;
                config.setLocale(locale);
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

            } else {
                Resources resources = context.getResources();
                Configuration configuration = resources.getConfiguration();
                //configuration.setLocale(new Locale(lang));
                configuration.locale = new Locale(languageToLoad);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    context.getApplicationContext().createConfigurationContext(configuration);
                }
            }
        }
    }

    public static String formatDate(String timestamp) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Convert timestamp to LocalDateTime
            LocalDateTime dateTime = parseTimestamp(timestamp);

            // Get today's date
            LocalDate today = LocalDate.now();

            // Get yesterday's date
            LocalDate yesterday = today.minusDays(1);

            // Format today's and yesterday's date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            String todayStr = today.format(formatter);
            String yesterdayStr = yesterday.format(formatter);

            // Format the timestamp date
            String timestampDate = dateTime.toLocalDate().format(formatter);

            // Compare the timestamp date with today and yesterday
            if (timestampDate.equals(todayStr)) {
                return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
            } else if (timestampDate.equals(yesterdayStr)) {
                return "Yesterday";
            } else {
                return timestampDate;
            }
        }

        return timestamp;
    }

    private static LocalDateTime parseTimestamp(String timestamp) {
        // Convert timestamp to LocalDateTime
        Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamp));
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String formatTime(String timestamp) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Convert timestamp to LocalDateTime
            LocalDateTime dateTime = parseTimestamp(timestamp);

            // Format the time part
            String timeStr = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));

            return timeStr;
        }

        return timestamp;
    }

}
