package com.app.example.osman.orders.recipes;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.example.osman.orders.dialog.BasicDialog;
import com.app.example.osman.orders.MathUtils;
import com.example.osman.orders.R;
import com.app.example.osman.orders.Storage;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailAdapter extends FragmentStatePagerAdapter implements RecipePayFragment.PayListener {


//    private AdapterPayListener listener;
    private ViewPager viewPager;
    private Recipe recipe;
    private Context context;
    private int contentLimit;
    private boolean payed;

    public RecipeDetailAdapter(FragmentManager fm, Recipe recipe, Context context, ViewPager viewPager) {
        super(fm);
        this.recipe = recipe;
        this.context = context;
        //limit free content
        this.contentLimit = MathUtils.getPercent(recipe.getSteps().size(),MathUtils.DEFAULT_CONTENT_PERCENT);
        this.viewPager = viewPager;
//        this.listener = listener;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        //if is ingredients
        if (position == 0 ) return RecipeIngredientsFragment.newInstance(recipe.getIngredients());

        //if is not open(not payed)
        if(!recipe.isOpen()) {
            if (position > contentLimit) return RecipePayFragment.newInstance(this,recipe.getSteps().get(position-1));
        }

        //if is open(free)
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
    public void onPay() throws IOException, ClassNotFoundException {
        Log.d(Recipe.RTAG, "onPay: _ recipe: " + recipe.toString());
        payed = true;
        recipe.setOpen(true);
        notifyDataSetChanged();
        viewPager.setCurrentItem(0);
        BasicDialog dialog = new BasicDialog();
        dialog.showDialog(context,context.getString(R.string.recipe_pay_thanks));
        Storage storage = Storage.getInstance(context);
        ArrayList<Recipe> recipes = storage.getRecipes();
        for(Recipe r : recipes){
            if(r.getSKU_ID().equalsIgnoreCase(recipe.getSKU_ID())){
                r.setOpen(true);
                Log.d(Recipe.RTAG, "onPay_ setOpen: " + r.getSKU_ID());
                break;
            }
        }

//        Log.d(Recipe.RTAG, "onPay: print list");
//        for(Recipe r : recipes){
//            Log.d(Recipe.RTAG,r.toString());
//        }
        storage.writeRecipes(recipes);
        Log.d(Recipe.RTAG, "onPay: writerecipes ");



//        listener.onPay();

    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        //if content payed then update view
        if( (object instanceof RecipePayFragment) && payed){
            ((RecipePayFragment)object).update();
        }

        return super.getItemPosition(object);
    }

//    public interface AdapterPayListener{
//        void onPay();
//    }
}
