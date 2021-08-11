package com.example.homescreenclockwidget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("tag_flow","MyAlarmReceiver onReceive çalıştı.Şimdiki zaman:"+ Calendar.getInstance().getTime());

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//saniye isteğe bağlı; kaçıncı saniyede güncelleme geldiğini takip için
        Date date = new Date();
        String dateformatted = dateFormat.format(date);

        Toast.makeText(context, "ClockWidget: "+dateformatted, Toast.LENGTH_LONG).show();// isteğe bağlı, çalıştığını görmek için
        Log.i("tag_flow","MyAlarmReceiver onReceive çalıştı"+dateformatted+" gettime:"+date.getTime());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setTextViewText(R.id.appwidget_text,dateformatted);
        appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), ClockWidget.class.getName()), views);

    }
}
