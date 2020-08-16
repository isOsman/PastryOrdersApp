package com.example.osman.orders.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.osman.orders.R;

public class RecipeIngredientsFragment extends Fragment {
    public static final String RECIPE_INGREDIENTS = "RECIPE_INGREDIENTS";

    public static RecipeIngredientsFragment newInstance(String[] ingredients){

        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle dataArgs = new Bundle();
        dataArgs.putStringArray(RECIPE_INGREDIENTS,ingredients);
        fragment.setArguments(dataArgs);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_ingredients,null);
        Bundle args = getArguments();
        String[] ingrs = args.getStringArray(RECIPE_INGREDIENTS);
        TextView ingrText = view.findViewById(R.id.ingredients_text);
        StringBuilder sb = new StringBuilder();
        for(String s : ingrs){
            sb.append(" - " + s + "\n");
        }

        ingrText.setText(sb);

        return view;
    }

}
