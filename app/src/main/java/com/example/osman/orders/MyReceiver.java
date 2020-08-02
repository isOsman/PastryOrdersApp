package com.example.osman.orders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    private void showNotification(Context context) {
        int NOTIFICATION_ID = 101;
        Intent notificationIntent = new Intent(context,MainActivity.class);
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
}
