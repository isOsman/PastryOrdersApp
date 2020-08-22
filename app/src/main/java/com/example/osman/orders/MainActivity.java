package com.example.osman.orders;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    InterstitialAd interstitialAd;

    Storage storage;
    OrderAdapter adapter;
    ArrayList<Order> orders;
    Button addButton;
    TextView topView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);


        try {
            storage = Storage.getInstance(this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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
                    Intent intent = new Intent(MainActivity.this,TopLevelActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

            //add: end


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.ordersItem);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);







//        Intent serviceIntent = new Intent(this,MyService.class);
//        if(!isMyServiceRunning(MyService.class)) {
//            startService(serviceIntent);
//        }




        //setAlarm();



        ListView ordersList;
        topView = (TextView) findViewById(R.id.topView);

        try {
//            Toast.makeText(this,"Storage: " + (storage == null),Toast.LENGTH_SHORT).show();
            orders = storage.getList();
            if(orders == null)orders = new ArrayList<>();

            if (orders.size() == 0){
                topView.setText(getString(R.string.noOrders));
            }else{
                topView.setText(String.valueOf(getString(R.string.orderCount) + orders.size()));
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ordersList = (ListView) findViewById(R.id.orderList);
        adapter = new OrderAdapter(this,R.layout.order_item,orders,storage,topView);

        ordersList.setAdapter(adapter);

        addButton = (Button) findViewById(R.id.addBtn);
        addButton.setOnClickListener(this);




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
        try {
            //if add is on and add is loaded : show
            if(storage.addIsOn() && interstitialAd.isLoaded()){
                interstitialAd.show();
            }else {
                //back to TopLevelActivity
                super.onBackPressed();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Request.ORDER_EDIT) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Order order = new Order();
                order.setId(extras.getInt("orderId"));
                order.setCustomer(String.valueOf(extras.get("customer")));
                order.setCake(String.valueOf(extras.get("cake")));
                order.setOrderDate(extras.getString("orderDate"));
                order.setSendDate(extras.getString("sendDate"));
                order.setWeight(extras.getDouble("weight"));
                order.setPrice(extras.getDouble("price"));
                order.setMade(extras.getBoolean("made"));
                adapter.setList(adapter.currPos, order);
                Toast.makeText(this, R.string.changed, Toast.LENGTH_LONG).show();
            }

        }else if(requestCode == Request.ORDER_ADD){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Order order = new Order();
                try {
                    order.setId(storage.getAndIncID());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                order.setCustomer(String.valueOf(extras.get("customer")));
                order.setCake(String.valueOf(extras.get("cake")));
                order.setOrderDate(extras.getString("orderDate"));
                order.setSendDate(extras.getString("sendDate"));
                order.setWeight(extras.getDouble("weight"));
                order.setPrice(extras.getDouble("price"));
                order.setMade(extras.getBoolean("made"));
                adapter.add(order);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, R.string.added, Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(this,OrderAdd.class);
            startActivityForResult(intent,Request.ORDER_ADD);
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}

