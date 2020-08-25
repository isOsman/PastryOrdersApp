package com.app.example.osman.orders.Pay;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayClient {

    //all items
    public ArrayList<String> skuList = new ArrayList<>();

    //all item details
    public HashMap<String, SkuDetails> skuDetailsMap = new HashMap<>();

    private Activity mActivity;
    private BillingClient mBillingClient;

//    private Storage storage;

    public PayClient(Activity activity) throws IOException, ClassNotFoundException {
        this.mActivity = activity;
//        storage = Storage.getInstance(activity);
        mBillingClient = BillingClient.newBuilder(mActivity).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list!= null){
                    querySkuDetails();//get sku info
                    List<Purchase> purchasesList = queryPurchases();//get purchase info

                    //if item payed - provide it to the user
                   finishPay(skuList,purchasesList);
                }

            }
        }).build();

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    //here we can get purchases info
                    querySkuDetails();//get sku info
                    List<Purchase> purchasesList = queryPurchases();//get purchase info

                    //if item payed - provide it to the user
                    finishPay(skuList,purchasesList);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                //on no internet connection
            }
        });


    }


    //get skuDetails info
    private void querySkuDetails(){
        SkuDetailsParams.Builder skuDetailsParamsBuilder = SkuDetailsParams.newBuilder();
        skuDetailsParamsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(skuDetailsParamsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    for(SkuDetails skuDetails : list){
                        skuDetailsMap.put(skuDetails.getSku(),skuDetails);
                    }
                }
            }
        });
    }

    //get purchases info
    private List<Purchase> queryPurchases(){
        Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
        return purchasesResult.getPurchasesList();
    }


    //pay item
    public void launchBilling(String skuId){
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetailsMap.get(skuId))
                .build();

        mBillingClient.launchBillingFlow(mActivity,billingFlowParams);
    }


    //check payed items and provide it to the user
    private void finishPay(List<String> skuList,List<Purchase> purchaseList){
        for(Purchase purchase : purchaseList){
            String purchaseId = purchase.getSku();
            for(String sku : skuList){
                if(sku.equals(purchaseId)){
                    //put your logic here...
                }
            }
        }
    }

}
