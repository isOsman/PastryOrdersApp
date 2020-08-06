package com.example.osman.orders;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class TipAdapter extends ArrayAdapter<Tip> {

    private Context context;
    private Storage storage;
    private int resource;
    private ArrayList<Tip> tipList;
    private LayoutInflater inflater;


    public TipAdapter(@NonNull Context context, int resource, ArrayList<Tip> tips,Storage storage) {
        super(context, resource, tips);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.tipList = tips;
        this.storage = storage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.resource,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(tipList.get(position).isOpen()){
            viewHolder.textView.setText(tipList.get(position).getText());
            viewHolder.textView.setBackgroundResource(R.drawable.tip_item_open);
            Log.d(storage.TAG,"isOpen : " + position + " | text : " + tipList.get(position).getText());
        }else if(!tipList.get(position).isOpen() && tipList.get(position).isCanOpen()){
            viewHolder.textView.setText("");
            viewHolder.textView.setHint(R.string.tip_unlock_text);
            viewHolder.textView.setBackgroundResource(R.drawable.tip_item_can_open);
            viewHolder.textView.setOnClickListener(new ClickListener(position));
            Log.d(storage.TAG,"isNOTOpen : " + position + " | text : " + tipList.get(position).getText());
        }else{
            viewHolder.textView.setText("");
            viewHolder.textView.setBackgroundResource(R.drawable.tip_item_close);
            viewHolder.textView.setHint(R.string.tip_lock_text);
        }

        return convertView;


    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        try {
            storage.writeTips(tipList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    //-------------------------------------------------------------------
    //class for processing each view
   private class ViewHolder {
        TextView textView;

        ViewHolder(View view){
            this.textView = view.findViewById(R.id.tip_item);
        }


       public String getString(int resource){
           return context.getResources().getString(resource);
       }

    }


    class ClickListener implements View.OnClickListener{
        int pos;

        ClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View view) {
            ((TextView) view.findViewById(R.id.tip_item)).setText(tipList.get(pos).getText());
            tipList.get(pos).setOpen(true);
            notifyDataSetChanged();

        }
    }




}
