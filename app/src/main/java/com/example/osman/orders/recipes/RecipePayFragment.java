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

public class RecipePayFragment extends Fragment {

    Recipe recipe;
    PayListener listener;

    public RecipePayFragment(PayListener listener){
        this.listener = listener;
    }

    public static RecipePayFragment newInstance(PayListener listener){
        return new RecipePayFragment(listener);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View payView = inflater.inflate(R.layout.recipe_pay_and_step,null);
        final Button payBtn = (Button) payView.findViewById(R.id.pay_btn);



        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Click",Toast.LENGTH_SHORT).show();
                listener.onPay();
//                payView.findViewById(R.id.recipe_pay_layout).setVisibility(View.GONE);
//                recipe.setOpen(true);
//                View view1 = payView.findViewById(R.id.recipe_step_layout);
//                ((TextView)view1.findViewById(R.id.step_text)).setText("sdflkjsdlkf sdlkfj sdlfkj sdfjslkdfjslkdf sdlkf");
//                ((ImageView) view1.findViewById(R.id.step_img)).setBackgroundResource(R.drawable.clock);
//                view1.setVisibility(View.VISIBLE);


            }
        });


        return  payView;

    }

    public interface PayListener{
        void onPay();

    }


}
