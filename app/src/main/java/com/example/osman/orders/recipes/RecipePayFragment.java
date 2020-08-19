package com.example.osman.orders.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.osman.orders.R;

public class RecipePayFragment extends Fragment {

    public static RecipePayFragment newInstance(){
        return new RecipePayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View payView = inflater.inflate(R.layout.recipe_pay,null);
        Button payBtn = (Button) payView.findViewById(R.id.pay_btn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Click",Toast.LENGTH_SHORT).show();
            }
        });

        return  payView;

    }
}
