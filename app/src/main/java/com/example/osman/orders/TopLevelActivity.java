package com.example.osman.orders;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;

public class TopLevelActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        try {
            Storage.getInstance(this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

//        BANNER ADD
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//
//            }
//        });
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
        }
    }
}