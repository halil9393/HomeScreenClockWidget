package com.example.homescreenclockwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Uygulama Debug edildiğinde pendingintentler bozulabiliyor, her açılışta repair etmesi için yazıldı...
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//saniye isteğe bağlı; kaçıncı saniyede güncelleme geldiğini takip için
        Date date = new Date();
        String dateformatted = dateFormat.format(date);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.clock_widget);
        views.setTextViewText(R.id.appwidget_text,dateformatted);
        appWidgetManager.updateAppWidget(new ComponentName(this.getPackageName(), ClockWidget.class.getName()), views);
    }
}