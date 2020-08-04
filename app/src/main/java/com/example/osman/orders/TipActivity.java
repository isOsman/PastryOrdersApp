package com.example.osman.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        ArrayList<Tip> tips = new ArrayList<>();
        tips.add(new Tip("Используйте комара вместо мухи и слона",true));
        tips.add(new Tip("Используйте комара вместо мухи и слона",false));
        tips.add(new Tip("Используйте комара вместо мухи и слона или Используйте комара вместо мухи и слона",false));

        ListView listView = (ListView) findViewById(R.id.tip_list);
        try {
            TipAdapter tipAdapter = new TipAdapter(this,R.layout.tip_item,tips,Storage.getInstance(this));
            listView.setAdapter(tipAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}