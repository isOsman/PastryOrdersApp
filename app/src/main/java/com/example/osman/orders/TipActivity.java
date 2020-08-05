package com.example.osman.orders;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.tip_list);
        TipAdapter tipAdapter = new TipAdapter(this,R.layout.tip_item,tips,storage);
        listView.setAdapter(tipAdapter);


    }
}