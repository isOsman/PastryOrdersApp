package com.app.example.osman.orders.recipes;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osman.orders.R;
import com.app.example.osman.orders.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.ViewHolder> {
    private ArrayList<Recipe> dataset;
    private Context context;

    private ItemClickListener itemClickListener;

    public static final String TAG = "CR_TAG";



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView cakeTitle;
        public ImageView cakeImg;
        public ImageView diffImg;
        public TextView diffText;
        public TextView timeText;
        public ImageView isOpenImg;
        public TextView cakeDesc;


        public ViewHolder(View card){
            super(card);
            this.view = card;
            cakeTitle = (TextView) card.findViewById(R.id.cake_title);
            cakeImg = (ImageView) card.findViewById(R.id.cake_img);
            diffImg = (ImageView) card.findViewById(R.id.diff_img);
            diffText = (TextView) card.findViewById(R.id.diff_text);
            timeText = (TextView) card.findViewById(R.id.time_text);
            isOpenImg = (ImageView) card.findViewById(R.id.isOpen);
            cakeDesc = (TextView) card.findViewById(R.id.cake_desc);

        }
    }

    public RecipeCardAdapter(Context context,ArrayList<Recipe> data,ItemClickListener itemClickListener){
        this.context = context;
        this.dataset = data;
        this.itemClickListener = itemClickListener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (CardView) LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.card_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int diff_img = -1;
        int diff_text_id = -1;


        Log.d(TAG, "onBindViewHolder _ pos: " + position + ": recipe: " + dataset.get(position).toString());

        Picasso.get()
                .load(dataset.get(position).getImgId())
                .placeholder(R.drawable.logo)
                .resize(512,512)
                .into(holder.cakeImg);


        if(dataset.get(position).isOpen()){
            Log.d(TAG, "onBindViewHolder _ is open : " + dataset.get(position).getSKU_ID());
            holder.isOpenImg.setBackgroundResource(R.drawable.unlock);
            holder.isOpenImg.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.color_unlock)));
//            holder.isOpenImg.setColorFilter(context.getResources().getColor(R.color.color_unlock));
        }
//        else{
//            holder.isOpenImg.setImageResource(R.drawable.lock);
//            holder.isOpenImg.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.color_lock)));
////            holder.isOpenImg.setColorFilter(context.getResources().getColor(R.color.color_lock));
//        }






        holder.view.setOnClickListener(new CardClickListener(position));

        holder.cakeTitle.setText(dataset.get(position).getTitle());





        switch (dataset.get(position).getDifficult()){
            case 1:
                diff_img = R.drawable.diff_easy;
                diff_text_id = R.string.diff_easy;
                break;
            case 2:
                diff_img = R.drawable.diff_medium;
                diff_text_id = R.string.diff_meduim;
                break;
            case 3:
                diff_img = R.drawable.diff_hard;
                diff_text_id = R.string.diff_hard;
                break;
        }

        holder.diffImg.setImageResource(diff_img);

        holder.diffText.setText(diff_text_id);

        int mins = dataset.get(position).getCookingTimeMins();
        int hour = 60;



        if(mins<=hour){
            holder.timeText.setText(mins+context.getString(R.string.minute_unit));
        }else{
            int resMins,resHours;
            resHours = mins / hour;
            resMins = mins % hour;
            holder.timeText.setText(resHours + context.getString(R.string.hour_unit) + resMins + context.getString(R.string.minute_unit));
        }


//        holder.timeImg.setImageResource(R.drawable.clock);
        holder.cakeDesc.setText(dataset.get(position).getDescription());





    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    class CardClickListener implements View.OnClickListener{
        int itemPos;

        public CardClickListener(int itemPos){
            this.itemPos = itemPos;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.RECIPE_EXTRA,dataset.get(itemPos));
            itemClickListener.onCardClick(itemPos);
            context.startActivity(intent);
        }
    }

    public void update(ArrayList<Recipe> recipes){
        this.dataset = recipes;
        for (Recipe r: dataset){
            Log.d(TAG, "update _ recipe: " + r.toString());
        }
        notifyDataSetChanged();
    }





    public interface ItemClickListener{
        void onCardClick(int itemPos);
    }


}
