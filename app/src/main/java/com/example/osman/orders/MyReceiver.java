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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyReceiver extends BroadcastReceiver {

    public static final String MY_ACTION = "ALARM_ACTION";
    public static final String TAG = "NTAG";
    Storage storage;
    ArrayList<Order> orders;
    Calendar calendar;
    int notifId = 707;

    @Override
    public void onReceive(Context context, Intent intent) {

        showNotification(context,context.getString(R.string.greeting),context.getString(R.string.new_tip),notifId++);

        setAlarm(context);

        calendar = Calendar.getInstance();

        try {
            storage = Storage.getInstance(context);
            orders = storage.getList();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //if has orders in storage
        if((orders!= null) && (orders.size()!=0)){
            String date;
            for(Order o : orders){
                date = o.getSendDate().split("\\.")[0];
                Log.d(TAG,"order date: " + date);
                Log.d(TAG,"calendar date: " + calendar.get(Calendar.DAY_OF_MONTH));
//                showNotification(context,context.getString(R.string.greeting),context.getString(R.string.today_has_order)+" "+o.getCustomer()+" | " + o.getCake(),notifId++);

                //if in current day has orders
                if (calendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(date)){
                    Log.d(TAG,"Gotcha: " + date);
                    showNotification(context,context.getString(R.string.greeting),context.getString(R.string.today_has_order)+" "+o.getCustomer()+" | \"" + o.getCake()+"\"",notifId++);
                }
            }
        }

    }

    private void showNotification(Context context,String title,String subTitle,int notifId) {
        Intent notificationIntent = new Intent(context,TopLevelActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(subTitle)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);

        //startForeground(NOTIFICATION_ID,builder.build());
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifId,builder.build());
    }


    public  void setAlarm(Context context) {
        //if alarm is started
        Intent checkIntent = new Intent(context,MyReceiver.class);
        boolean hasAlarm = (PendingIntent.getBroadcast(context,0,checkIntent,PendingIntent.FLAG_NO_CREATE) != null);


        if(!hasAlarm) {
            Log.d("MYTAG", "Start Alarm");
            Intent intent = new Intent(context, MyReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Request.NOTIFICATION_HOUR);
            calendar.set(Calendar.MINUTE, Request.NOTIFICATION_MINUTE);

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
