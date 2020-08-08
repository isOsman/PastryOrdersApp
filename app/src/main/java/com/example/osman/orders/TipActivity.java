package com.example.osman.orders;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

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
        long readyTip = DateUtils.getDateDiff(date,new Date(), TimeUnit.HOURS);
        Toast.makeText(this,"READY TIP: " + readyTip,Toast.LENGTH_SHORT).show();
        Log.d(Storage.TAG,"ready tip : " + readyTip);
        Toast.makeText(this,"READY TIP: " + readyTip,Toast.LENGTH_SHORT).show();

        Toast.makeText(this,"PARSED DATE: " + date.toString(),Toast.LENGTH_SHORT).show();
        if(readyTip<1) return;
        for (int i = 0;(i<tips.size()) && (readyTip>=1);i++){
            if(!tips.get(i).isOpen() && (!tips.get(i).isCanOpen())){
                tips.get(i).setCanOpen(true);
                readyTip--;
            }
        }

        storage.writeDate(new Date().getTime()+"");
        storage.writeTips(tips);
    }
}