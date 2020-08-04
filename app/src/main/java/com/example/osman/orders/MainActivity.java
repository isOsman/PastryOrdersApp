package com.example.osman.orders;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener{

    Storage storage;
    OrderAdapter adapter;
    ArrayList<Order> orders;
    Button addButton;
    TextView topView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);




//        Intent serviceIntent = new Intent(this,MyService.class);
//        if(!isMyServiceRunning(MyService.class)) {
//            startService(serviceIntent);
//        }


        try {
            storage = Storage.getInstance(this);
            ArrayList<Tip> tips = storage.getTips();
            if(tips == null){
                Log.d(storage.TAG,"tips is null");
            }else{
                Log.d(storage.TAG,"tips non null");
                tips.get(1).setOpen(false);
                storage.writeTips(tips);
                tips = storage.getTips();
                for(Tip tip : tips){
                    Log.d(storage.TAG,tip.toString());
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //setAlarm();



        ListView ordersList;
        topView = (TextView) findViewById(R.id.topView);
        try {
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

    private void setAlarm() {
        Log.d("MYTAG","Start Alarm");
        Intent intent = new Intent(this,MyReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,3);
        calendar.set(Calendar.MINUTE,51);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),TimeUnit.MINUTES.toMillis(1),alarmIntent);


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
                order.setWeight(extras.getInt("weight"));
                order.setPrice(extras.getInt("price"));
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
                order.setWeight(extras.getInt("weight"));
                order.setPrice(extras.getInt("price"));
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

