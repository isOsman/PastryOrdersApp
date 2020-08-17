package com.example.osman.orders;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tipsItem);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Storage storage = null;
        ArrayList<Tip> tips = null;

        try {
             storage = Storage.getInstance(this);
             tips = storage.getTips();
             checkTips(storage,tips);
        } catch (IOException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }



        ListView listView = (ListView) findViewById(R.id.tip_list);
        TipAdapter tipAdapter = new TipAdapter(this,R.layout.tip_item,tips,storage);
        listView.setAdapter(tipAdapter);


    }

    private void checkTips(Storage storage,ArrayList<Tip> tips) throws IOException, ParseException {
        Date date =  new Date(Long.parseLong(storage.getDate()));
        Log.d(storage.TAG,"checkTips | last date: " + date.toString());
        long readyTip = DateUtils.getDateDiff(date,new Date(), TimeUnit.DAYS);
//        Toast.makeText(this,"READY TIP: " + readyTip,Toast.LENGTH_SHORT).show();
        Log.d(Storage.TAG,"ready tip : " + readyTip);
//        Toast.makeText(this,"READY TIP: " + readyTip,Toast.LENGTH_SHORT).show();

//        Toast.makeText(this,"PARSED DATE: " + date.toString(),Toast.LENGTH_SHORT).show();
        if(readyTip<1) return;

        for (int i = 0;(i<tips.size()) && (readyTip>=1);i++){
            if(!tips.get(i).isOpen() && (!tips.get(i).isCanOpen())){
                tips.get(i).setCanOpen(true);
                readyTip--;
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,DateUtils.WAKE_HOUR);
        calendar.set(Calendar.MINUTE,0);
        Date writeDate = calendar.getTime();
        Log.d(Storage.TAG,"next tip date: " + writeDate.toString());
        storage.writeDate(writeDate.getTime()+"");
        storage.writeTips(tips);


    }
}