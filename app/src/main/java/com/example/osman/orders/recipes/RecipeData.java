package com.example.osman.orders.recipes;

import android.util.Log;

import com.example.osman.orders.R;
import com.example.osman.orders.recipes.Recipe;

import java.util.ArrayList;

// TODO: 20.08.2020 add (String)  UNIT_ID field
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
        stepList.add(new Recipe.Step(R.drawable.logo,"Приготовить муку" +
                "просеять" +
                "продавить" +
                "продать" +
                "оживить дракона " +
                " дать ему дрожжей"));
        stepList.add(new Recipe.Step(R.drawable.lion,"взять два яйца"));
        stepList.add(new Recipe.Step(R.drawable.idol,"отдать два яйца жыдвлао ывлаодл ывдлаодлыво ывлдоадлывоа ывало" +
                "ывдлаоыдлвао" +
                "ывадлыовао ывдлоаыдвлоа"));
        stepList.add(new Recipe.Step(R.drawable.logo,"готово - вы кондитер"));
        stepList.add(new Recipe.Step(R.drawable.lion,"взять два яйца"));
        stepList.add(new Recipe.Step(R.drawable.idol,"отдать два яйца жыдвлао ывлаодл ывдлаодлыво ывлдоадлывоа ывало" +
                "ывдлаоыдлвао" +
                "ывадлыовао ывдлоаыдвлоа"));
        stepList.add(new Recipe.Step(R.drawable.logo,"готово - вы кондитер"));
        recipe.setSteps(stepList);

        recipe.setSKU_ID("RC_NAPOLEON_1");
        recipe.setDifficult(Recipe.DIFF_HARD);
        recipe.setImgId(R.drawable.lion);
        Recipe napoleon = recipe.build();

        recipe.setSKU_ID("RC_ZEBRA_1");
        recipe.setDifficult(Recipe.DIFF_MEDIUM);
        recipe.setImgId(R.drawable.idol);
        recipe.setTitle("Зебра");
        Recipe zebra = recipe.build();

        recipe.setCookingTimeMins(125);
        recipe.setSKU_ID("RC_MAFFINS_1");
        recipe.setDifficult(Recipe.DIFF_EASY);
        recipe.setTitle("Маффины от мафиози");
        recipe.setOpen(true);
        Recipe maffin = recipe.build();

        recipe.setSKU_ID("RC_ECLERS_1");
        recipe.setImgId(R.drawable.logo);
        recipe.setTitle("Эклеры по американски");
        recipe.setOpen(false);
        Recipe ecclere = recipe.build();

        recipe.setCookingTimeMins(238);
        recipe.setSKU_ID("RC_TRAYFL_1");
        recipe.setTitle("Трайфл");
        recipe.setOpen(false);
        Recipe tryfle = recipe.build();


        recipes = new Recipe[]{
                tryfle
                ,napoleon
                ,zebra
                ,maffin
                ,ecclere
        };
    }

}
