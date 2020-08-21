package com.example.osman.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class OrderEdit extends Activity implements View.OnClickListener {

//    private InterstitialAd interstitialAd;

    private TextView orderIdView;
    private EditText customerView;
    private EditText cakeView;
    private DatePicker orderDateView;
    private DatePicker sendDateView;
    private EditText weightView;
    private EditText priceView;
    private CheckBox madeView;
    private Button okBtn;
    private Button cancelBtn;

    private Order editOrder;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);

        //add: start
//        MobileAds.initialize(this);
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//
//        List<String> testDevs = new ArrayList<>();
//        testDevs.add("2WGDU17A14000038");
//        testDevs.add("192.168.169.102:5555 ");
//
//        RequestConfiguration configuration =
//                new RequestConfiguration.Builder().setTestDeviceIds(testDevs).build();
//        MobileAds.setRequestConfiguration(configuration);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        interstitialAd.loadAd(adRequest);

        //on close add
//        interstitialAd.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed() {
//                try{
//                    Intent intent = new Intent(TipActivity.this,TopLevelActivity.class);
//                    startActivity(intent);
//                    finish();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

        //add: end

        //fill all views
        Bundle extras = getIntent().getExtras();
        editOrder = new Order();
        editOrder.setId(extras.getInt("orderId"));
        editOrder.setCustomer(extras.getString("customer"));
        editOrder.setCake(extras.getString("cake"));
        editOrder.setOrderDate(extras.getString("orderDate"));
        editOrder.setSendDate(extras.getString("sendDate"));
        editOrder.setWeight(extras.getDouble("weight"));
        editOrder.setPrice(extras.getDouble("price"));
        editOrder.setMade(extras.getBoolean("made"));


        orderIdView = (TextView) findViewById(R.id.orderIdEdit);
        customerView = (EditText) findViewById(R.id.customerEdit);
        cakeView = (EditText) findViewById(R.id.cakeEdit);
        orderDateView = (DatePicker) findViewById(R.id.orderDateEdit);
        sendDateView = (DatePicker) findViewById(R.id.sendDateEdit);
        weightView =  (EditText) findViewById(R.id.weightEdit);
        priceView = (EditText) findViewById(R.id.priceEdit);
        madeView = (CheckBox) findViewById(R.id.madeBoxEdit);
        okBtn = (Button) findViewById(R.id.okBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        orderIdView.setText(String.valueOf(getString(R.string.orderNumber) + " " +editOrder.getId()));
        customerView.setText(editOrder.getCustomer());
        cakeView.setText(editOrder.getCake());

        //split date for "."
        //dd.mm.yyyy - String
        String dateArr[] = editOrder.getOrderDate().trim().split("\\.");
        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        month--;
        int year = Integer.parseInt(dateArr[2]);
        orderDateView.updateDate(year,month,day);

        dateArr = editOrder.getSendDate().trim().split("\\.");
        day = Integer.parseInt(dateArr[0]);
        month = Integer.parseInt(dateArr[1]);
        month--;
        year = Integer.parseInt(dateArr[2]);
        sendDateView.updateDate(year,month,day);

        weightView.setText(String.valueOf(editOrder.getWeight()));
        priceView.setText(String.valueOf(editOrder.getPrice()));
        madeView.setChecked(editOrder.isMade());
        madeView.setText(editOrder.isMade() ? R.string.madeTrue : R.string.madeFalse);


        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        madeView.setOnClickListener(this);
    }


    private String getDate(DatePicker datePicker){
        return datePicker.getDayOfMonth() + "." + (datePicker.getMonth()+1) + "." + datePicker.getYear();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.madeBoxEdit){
            madeView.setText(madeView.isChecked() ? getString(R.string.madeTrue) : getString(R.string.madeFalse));
        }
        if(view.getId() == R.id.cancelBtn) {

            //show add
//            showAdd();

            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }else if (view.getId() == R.id.okBtn){

            if (!checkViews()) return;

            //show add
//            showAdd();

            Intent intent = new Intent();
            intent.putExtra("orderId",editOrder.getId());
            intent.putExtra("customer",customerView.getText());
            intent.putExtra("cake",cakeView.getText());
            intent.putExtra("orderDate",getDate(orderDateView));
            intent.putExtra("sendDate",getDate(sendDateView));
            intent.putExtra("weight",Double.parseDouble(weightView.getText().toString()));
            intent.putExtra("price",Double.parseDouble(priceView.getText().toString()));
            intent.putExtra("made",madeView.isChecked());
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    private boolean checkViews(){
        if(customerView.getText().toString().trim().length() == 0
                || cakeView.getText().toString().trim().length() == 0
                || (weightView.getText().toString().trim().length() == 0 || weightView.getText().toString().trim().length() > 10)
                || (priceView.getText().toString().trim().length() == 0 || priceView.getText().toString().trim().length() > 10) )
        {
            Toast.makeText(this,R.string.checkView,Toast.LENGTH_SHORT).show();
            return false;
        }else if(!checkDate(orderDateView,sendDateView)) {
            Toast.makeText(this,R.string.checkDate,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;


    }

    private boolean checkDate(DatePicker date1, DatePicker date2){
        try {
            Date dateLarge = new SimpleDateFormat("dd.MM.yyyy").parse(getDate(date1));
            Date dateLess = new SimpleDateFormat("dd.MM.yyyy").parse(getDate(date2));
            if(dateLarge.compareTo(dateLess) > 0){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //show add
//        showAdd();
        super.onBackPressed();
    }


    public void showAdd(){
        //use random for show add
//        Random random = new Random();
//        if (random.nextBoolean() && interstitialAd.isLoaded()){
//            interstitialAd.show();
//        }
    }
}