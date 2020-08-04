package com.example.osman.orders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class TopLevelActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        try {
            Storage.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnClick(View view) {
        if(view.getId() == R.id.orderBtn){
            startActivity(new Intent(this,MainActivity.class));
        }else if(view.getId() == R.id.tipsBtn){
            startActivity(new Intent(this,TipActivity.class));
        }
    }
}