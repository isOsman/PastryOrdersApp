package com.example.osman.orders.recipes;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.osman.orders.MathUtils;
import com.example.osman.orders.R;

public class RecipeDetailAdapter extends FragmentStatePagerAdapter implements RecipePayFragment.PayListener {

    private Recipe recipe;
    private Context context;
    private int contentLimit;
    public RecipeDetailAdapter(FragmentManager fm, Recipe recipe, Context context){
        super(fm);
        this.recipe = recipe;
        this.context = context;
        //limit free content
        this.contentLimit = MathUtils.getPercent(recipe.getSteps().size(),MathUtils.DEFAULT_CONTENT_PERCENT);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0 ) return RecipeIngredientsFragment.newInstance(recipe.getIngredients());
        if(!recipe.isOpen()) {
            if (position > contentLimit) return RecipePayFragment.newInstance(this);
        }
        return RecipeFragment.newInstance(recipe.getSteps().get(position-1));
    }

    @Override
    public int getCount() {
        return (recipe.getSteps().size()+1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        if(position == 0) return context.getString(R.string.recipe_ingredients);
//        if(position>contentLimit){
//            return "Купите";
//        }
        return  (context.getString(R.string.step_title) + " " + (position+1));

    }

    @Override
    public void onPay() {
        Log.d(Recipe.RTAG, "onPay: ");
        recipe.setOpen(true);
        notifyDataSetChanged();
    }
}
