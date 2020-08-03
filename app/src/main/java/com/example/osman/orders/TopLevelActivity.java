package com.example.osman.orders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
    }

    public void btnClick(View view) {
        if(view.getId() == R.id.orderBtn){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}