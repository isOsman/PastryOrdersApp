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
        stepList.add(new Recipe.Step(R.drawable.lion,"взять два яйца"));
        stepList.add(new Recipe.Step(R.drawable.idol,"отдать два яйца"));
        stepList.add(new Recipe.Step(R.drawable.logo,"дфывоадфоывдалофыдвл" +
                "ыдвлаофдлыва" +
                "фыжваодлыфовадл" +
                "фыоаожфывафыдвлоажфдываофдлывоажд" +
                "офждывало фжвдлыоа" +
                " фываожфдывао"));
        stepList.add(new Recipe.Step(R.drawable.lion,"взять два яйца"));
        stepList.add(new Recipe.Step(R.drawable.idol,"отдать два яйца жыдвлао ывлаодл ывдлаодлыво ывлдоадлывоа ывало" +
                "ывдлаоыдлвао" +
                "ывадлыовао ывдлоаыдвлоа"));
        stepList.add(new Recipe.Step(R.drawable.logo,"готово - вы кондитер"));
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
        recipe.setOpen(false);
        Recipe maffin = recipe.build();

        recipe.setImgId(R.drawable.logo);
        recipe.setTitle("Эклеры по американски");
        recipe.setOpen(false);
        Recipe ecclere = recipe.build();

        recipe.setTitle("Трайфл");
        recipe.setOpen(false);
        Recipe tryfle = recipe.build();


        recipes = new Recipe[]{
                tryfle
                ,napoleon
                ,zebra
                ,tryfle
                ,maffin
                ,ecclere
                ,napoleon
                ,zebra
                ,ecclere
                ,tryfle
        };
    }

}
