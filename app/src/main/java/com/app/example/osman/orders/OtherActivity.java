package com.app.example.osman.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.example.osman.orders.dialog.BasicDialog;
import com.example.osman.orders.R;

import java.io.IOException;

public class OtherActivity extends AppCompatActivity {

    Button addOnBtn;
    Button addOffBtn;
    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.otherItem);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            storage = Storage.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        addOffBtn = (Button) findViewById(R.id.add_off);
        addOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"ADD OFF",Toast.LENGTH_SHORT).show();
                try {
                    storage.writeAddStatus(Request.ADD_OFF);
                    Request.ADD_IS_ON = false;
                    new BasicDialog().showDialog(OtherActivity.this,getString(R.string.add_pay_thanks));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addOnBtn = (Button) findViewById(R.id.app_add);
        addOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"ADD ON",Toast.LENGTH_SHORT).show();
                try {
                    storage.writeAddStatus(Request.ADD_ON);
                    Request.ADD_IS_ON = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}