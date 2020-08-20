package com.example.osman.orders.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.osman.orders.R;

import java.io.IOException;

public class RecipePayFragment extends Fragment {

    private Recipe.Step step;
    private PayListener listener;

    private View payView;
    private Button payBtn;

    public RecipePayFragment(PayListener listener,Recipe.Step step){
        this.listener = listener;
        this.step = step;
    }

    public static RecipePayFragment newInstance(PayListener listener,Recipe.Step step){
        return new RecipePayFragment(listener,step);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        payView = inflater.inflate(R.layout.recipe_pay_and_step,null);
        payBtn = (Button) payView.findViewById(R.id.pay_btn);



        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Click",Toast.LENGTH_SHORT).show();
                try {
                    listener.onPay();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                update();

            }
        });


        return  payView;

    }

    public interface PayListener{
        void onPay() throws IOException, ClassNotFoundException;

    }



    public void update(){
        payView.findViewById(R.id.recipe_pay_layout).setVisibility(View.GONE);
        View stepView = payView.findViewById(R.id.recipe_step_layout);
        ((TextView)stepView.findViewById(R.id.step_text)).setText(step.getInstruction());
        ((ImageView) stepView.findViewById(R.id.step_img)).setBackgroundResource(step.getImgId());
        stepView.setVisibility(View.VISIBLE);
    }


}
