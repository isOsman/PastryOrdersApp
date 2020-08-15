package com.example.osman.orders.recipes;

import android.util.Log;

import com.example.osman.orders.R;
import com.example.osman.orders.recipes.Recipe;

import java.util.ArrayList;

public class RecipeData {

    public Recipe[] recipes;

    public RecipeData(){
        Recipe recipe = new Recipe();
        recipe.setTitle("napoleon");
        recipe.setImgId(-1);
        recipe.setDifficult(Recipe.DIFF_EASY);
        recipe.setCookingTimeMins(60);
        recipe.setIngredients(new String[]{"яйца - 4шт","масло сливочное - 50мг"});
        recipe.setDescription("вкусно и дорого");

        ArrayList<Recipe.Step> stepList = new ArrayList<>();
        stepList.add(new Recipe.Step(1,"взять два яйца"));
        stepList.add(new Recipe.Step(2,"отдать два яйца"));
        stepList.add(new Recipe.Step(3,"готово - вы кондитер"));
        recipe.setSteps(stepList);

        recipe.setDifficult(Recipe.DIFF_HARD);
        recipe.setImgId(R.drawable.lion);
        Recipe napoleon = recipe.build();

        recipe.setDifficult(Recipe.DIFF_MEDIUM);
        recipe.setImgId(R.drawable.idol);
        recipe.setTitle("Зебра");
        Recipe zebra = recipe.build();

        recipe.setDifficult(Recipe.DIFF_EASY);
        recipe.setTitle("Маффины от мафиози");
        recipe.setOpen(true);
        Recipe maffin = recipe.build();

        recipe.setImgId(R.drawable.logo);
        recipe.setTitle("Эклеры по американски");
        recipe.setOpen(true);
        Recipe ecclere = recipe.build();

        recipes = new Recipe[]{
                napoleon
                ,zebra
                ,maffin
                ,ecclere
                ,napoleon
                ,zebra
                ,ecclere
        };
    }

}
