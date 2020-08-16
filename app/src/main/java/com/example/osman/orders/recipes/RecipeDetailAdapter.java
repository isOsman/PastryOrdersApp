package com.example.osman.orders.recipes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.osman.orders.R;

public class RecipeDetailAdapter extends FragmentStatePagerAdapter {

    private Recipe recipe;
    private Context context;
    public RecipeDetailAdapter(FragmentManager fm, Recipe recipe, Context context){
        super(fm);
        this.recipe = recipe;
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0 ) return RecipeIngredientsFragment.newInstance(recipe.getIngredients());
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
        return  (context.getString(R.string.step_title) + " " + (position+1));

    }
}
