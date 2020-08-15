package com.example.osman.orders.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.osman.orders.R;
import com.squareup.picasso.Picasso;

public class RecipeFragment extends Fragment {
    public static final String IMG_ID = "IMG_ID";
    public static final String INSTRUCTION = "INSTRUCTION";

    public static RecipeFragment newInstance(Recipe.Step step){
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle dataArgs = new Bundle();
        dataArgs.putInt(IMG_ID,step.imgId);
        dataArgs.putString(INSTRUCTION,step.instruction);

        recipeFragment.setArguments(dataArgs);
        return recipeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step,null);
        Bundle args = getArguments();
        TextView stepText = view.findViewById(R.id.step_text);
        stepText.setText(args.getString(INSTRUCTION));

        ImageView stepImg = view.findViewById(R.id.step_img);
        Picasso.get()
                .load(args.getInt(IMG_ID))
                .placeholder(R.drawable.logo)
                .resize(512,512)
                .into(stepImg);

        return view;
    }
}
