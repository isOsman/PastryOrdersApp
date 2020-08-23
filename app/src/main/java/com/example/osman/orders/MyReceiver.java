package com.example.osman.orders;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyReceiver extends BroadcastReceiver {

    public static final String MY_ACTION = "ALARM_ACTION";
    public static final String TAG = "NTAG";

    @Override
    public void onReceive(Context context, Intent intent) {

        setAlarm(context);
        showNotification(context);
    }

    private void showNotification(Context context) {
        int NOTIFICATION_ID = 101;
        Intent notificationIntent = new Intent(context,TopLevelActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("Привет Кондитер.")
                .setContentText("Проверь свои заказы!")
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_ALL);

        //startForeground(NOTIFICATION_ID,builder.build());
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }


    public  void setAlarm(Context context) {
        Intent checkIntent = new Intent(context,MyReceiver.class);
        boolean hasAlarm = (PendingIntent.getBroadcast(context,0,checkIntent,PendingIntent.FLAG_NO_CREATE) != null);


        if(!hasAlarm) {
            Log.d("MYTAG", "Start Alarm");
            Intent intent = new Intent(context, MyReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 10);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TimeUnit.MINUTES.toMillis(5), alarmIntent);

            Toast.makeText(context, "Alarm started", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Receiver alarm started");
        }else{
            Toast.makeText(context, "Alarm has", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Receiver alarm has");
        }

    }
}
