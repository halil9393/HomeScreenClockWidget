package com.example.homescreenclockwidget;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class ClockWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.i("tag_flow","updateAppWidget çalıştı");

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String dateformatted = dateFormat.format(date);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setTextViewText(R.id.appwidget_text,dateformatted);

        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views); // <-- Bu yöntem sadece gelen widget id günceller
        appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), ClockWidget.class.getName()), views);//Hepsini günceller


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i("tag_flow","onUpdate çalıştı. Şimdiki zaman:"+ Calendar.getInstance().getTime());

        // Not: Ekrana yeni bir widget eklendiğinde liste hep 1 eleman (sonuncu widget) dönüyor.
        //      Aslında ekranda olan widget sayısı farklı olabiliyor. Sistem tarafından metot çalıştırıldığında ise;
        //      gerçek liste dönüyor. Bu yüzden ıd listene bağımlı kod yazılmadı...

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        startAlarmForUpdate(context);
    }

    private void startAlarmForUpdate(Context context) {
        Log.i("tag_flow","startAlarmForUpdate çalıştı");

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0);

        // Telefon saatinin 1 dakika ilerisine alarm ayarlanması için...
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE)+1));
        calendar.set(Calendar.SECOND,5);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60, pendingIntent);

        Log.i("tag_alarm","Tekrarlı Alarm kuruldu.İlk çalışma zamanı:"+calendar.getTime());

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        // son widget silindiğinde, widget kalmadığında çalışır. Çalışan arka plan görevleri burada kaldırılacak

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null && alarmManager != null) {
            Log.i("tag_flow","PendingIntent ve AlarmManager bulundu ve iptal ediliyor...");
            alarmManager.cancel(pendingIntent);
        }

    }
}