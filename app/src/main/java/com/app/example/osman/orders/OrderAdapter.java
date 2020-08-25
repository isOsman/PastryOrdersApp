package com.app.example.osman.orders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.example.osman.orders.R;

import java.io.IOException;
import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order>{
    private LayoutInflater inflater;
    private  int layout;
    private ArrayList<Order> orderList;
    public int currPos;//position of current order
    private Context mContext;
    private Storage storage;
    private TextView topView;

    public OrderAdapter(@NonNull Context context, int resource,ArrayList<Order> orderList,Storage storage,TextView topView) {
        super(context, resource,orderList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.orderList = orderList;
        this.mContext = context;
        this.storage = storage;
        this.topView = topView;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }



        //fill order
        final Order order = orderList.get(position);

        viewHolder.orderIdView.setText(String.valueOf(viewHolder.getString(R.string.orderNumber)
                + " " + order.getId()));
        viewHolder.customerView.setText(String.valueOf(viewHolder.getString(R.string.customer)
                + " " + order.getCustomer()));
        viewHolder.cakeView.setText(String.valueOf(viewHolder.getString(R.string.cake)
                + " " + order.getCake()));
        viewHolder.orderDateView.setText(String.valueOf(viewHolder.getString(R.string.orderDate)
                + " " + order.getOrderDate()));
        viewHolder.sendDateView.setText(String.valueOf(viewHolder.getString(R.string.sendDate)
                + " " + order.getSendDate()));
        viewHolder.weightView.setText(String.valueOf(viewHolder.getString(R.string.weight)
                + " " + order.getWeight() + " " + viewHolder.getString(R.string.weight_unit) ));
        viewHolder.priceView.setText(String.valueOf(viewHolder.getString(R.string.price)
                + " " + order.getPrice() + " " + viewHolder.getString(R.string.price_unit)));

        viewHolder.madeBox.setChecked(order.isMade());
        viewHolder.madeBox.setClickable(false);


        //remove order
        viewHolder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(mContext);
//                 Toast.makeText(mContext,order.toString(),Toast.LENGTH_SHORT).show();
                 builder.setTitle("Удалить?");
                 builder.setMessage(order.toString(mContext));
                 builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                   @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        orderList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(mContext,"Удалено",Toast.LENGTH_SHORT).show();
                    }
                });
                 builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                 builder.show();
            }
        });



        //edit order
        viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                currPos = position;
                intent.putExtra("orderId",order.getId());
                intent.putExtra("customer",order.getCustomer());
                intent.putExtra("cake",order.getCake());
                intent.putExtra("orderDate",order.getOrderDate());
                intent.putExtra("sendDate",order.getSendDate());
                intent.putExtra("weight",order.getWeight());
                intent.putExtra("price",order.getPrice());
                intent.putExtra("made",order.isMade());

                intent.setClass(mContext,OrderEdit.class);

                ((Activity) mContext).startActivityForResult(intent,Request.ORDER_EDIT);

            }
        });


        return convertView;

    }

    public int getCurrPos(){
        return currPos;
    }

    public void setList(int pos,Order order){
        orderList.set(pos,order);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        try {
            storage.writeList(orderList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (orderList.size() == 0){
            topView.setText(mContext.getString(R.string.noOrders));
        }else{
            topView.setText(String.valueOf(mContext.getString(R.string.orderCount) + orderList.size()));
        }
    }



    //-------------------------------------------------------------------

    //class for processing each view
    private class ViewHolder {
        TextView orderIdView;
        TextView customerView;
        TextView cakeView;
        TextView orderDateView;
        TextView sendDateView;
        TextView weightView;
        TextView priceView;
        CheckBox madeBox;
        Button removeBtn;
        Button editBtn;

        ViewHolder(View view){
            orderIdView = (TextView) view.findViewById(R.id.orderIdView);
            customerView = (TextView) view.findViewById(R.id.customerView);
            cakeView = (TextView) view.findViewById(R.id.cakeView);
            orderDateView = (TextView) view.findViewById(R.id.orderDateView);
            sendDateView = (TextView) view.findViewById(R.id.sendDateView);
            weightView = (TextView) view.findViewById(R.id.weightView);
            priceView = (TextView) view.findViewById(R.id.priceView);
            madeBox = (CheckBox) view.findViewById(R.id.madeBox);
            removeBtn = (Button) view.findViewById(R.id.removeBtn);
            editBtn = (Button) view.findViewById(R.id.editBtn);
        }


        public String getString(int resource){
            return mContext.getResources().getString(resource);
        }

    }


}
