package com.example.osman.orders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TopLevelActivity extends AppCompatActivity {


    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

//        Log.d("MYTAG", "Start Alarm");
//        Intent intent = new Intent(this, MyReceiver.class);
//        sendBroadcast(intent);

        setAlarm();

        try {
            storage = Storage.getInstance(this);
            Request.ADD_IS_ON = storage.addIsOn();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

//        BANNER ADD
            MobileAds.initialize(this);

//
//        AdView mAdview = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdview.loadAd(adRequest);

    }

    public void btnClick(View view) {
        if(view.getId() == R.id.orderBtn){
            startActivity(new Intent(this,MainActivity.class));
        }else if(view.getId() == R.id.tipsBtn){
            startActivity(new Intent(this,TipActivity.class));
        }else if(view.getId() == R.id.recipesBtn){
            startActivity(new Intent(this,RecipeActivity.class));
        }else if(view.getId() == R.id.otherBtn){
            startActivity(new Intent(this,OtherActivity.class));
        }
    }

    public  void setAlarm() {
        Intent checkIntent = new Intent(TopLevelActivity.this,MyReceiver.class);
        boolean hasAlarm = (PendingIntent.getBroadcast(this,0,checkIntent,PendingIntent.FLAG_NO_CREATE) != null);


        if(!hasAlarm) {
            Log.d("MYTAG", "Start Alarm");
            Intent intent = new Intent(this, MyReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Request.NOTIFICATION_HOUR);
            calendar.set(Calendar.MINUTE, Request.NOTIFICATION_MINUTE);

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TimeUnit.MINUTES.toMillis(5), alarmIntent);

            Toast.makeText(this, "Alarm started", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Alarm has", Toast.LENGTH_SHORT).show();
        }

    }
}