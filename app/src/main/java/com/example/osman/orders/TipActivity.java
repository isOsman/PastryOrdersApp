package com.example.osman.orders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.osman.orders.dialog.BasicDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TipActivity extends AppCompatActivity {

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        //add: start
//        MobileAds.initialize(this);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        List<String> testDevs = new ArrayList<>();
        testDevs.add("2WGDU17A14000038");
        testDevs.add("192.168.169.102:5555 ");

        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDevs).build();
        MobileAds.setRequestConfiguration(configuration);

        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

        //on close add
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                try{
                    Intent intent = new Intent(TipActivity.this,TopLevelActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //add: end


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

        try {
            if (storage.getTipDialogStatus().equals(Request.DIALOG_ON)){
                BasicDialog dialog = new BasicDialog();
                dialog.showDialog(this,getString(R.string.tips_dialog));
                storage.writeTipDialogStatus(Request.DIALOG_OFF);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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

    //on back arrow click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        //show add if loaded
        if(Request.ADD_IS_ON && interstitialAd.isLoaded()){
            interstitialAd.show();
        }else {
            //back to TopLevelActivity
            super.onBackPressed();
        }
    }
}