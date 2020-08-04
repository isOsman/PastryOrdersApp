package com.example.osman.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);


        String tip = "используйте муку только первого сорта" +
                ",если вы собираетесь испекать в воде то нужно конечно комара выгнать,у него крылья не образивные,придется муху конечно заказывать" +
                "но можно и муравьями обоийтись";
        ListView listView = (ListView) findViewById(R.id.tip_list);
        String[] data = {"","","","",tip,"","","","sdfsdf","","","","","","","sdfsdf","","","","","",tip,"","","","sdfsdf","","","",tip};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,R.layout.tip_item,data);
        listView.setAdapter(arrayAdapter);
    }
}