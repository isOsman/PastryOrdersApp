package com.app.example.osman.orders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.example.osman.orders.R;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    public static final int NOTIFICATION_ID = 101;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new MyTask().execute();
        return START_REDELIVER_INTENT;
    }

    private void showNotification() {
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("Привет Кондитер.")
                .setContentText("Проверь свои заказы!")
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_ALL);

        //startForeground(NOTIFICATION_ID,builder.build());
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }


    private class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while(true) {
                try {
                    Log.d("MYTAG","Start Task");
                    TimeUnit.MINUTES.sleep(1);
                    showNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
