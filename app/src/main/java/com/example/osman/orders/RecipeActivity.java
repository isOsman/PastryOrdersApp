package com.example.osman.orders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.osman.orders.recipes.Recipe;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Recipe recipe = new Recipe();
        recipe.setTitle("none");
        recipe.setCookedImgs(new int[]{1,2,3});
        recipe.setDifficult(Recipe.DIFF_EASY);
        recipe.setCookingTimeMins(60);
        recipe.setIngredients(new String[]{"egg - 4шт","oil - 50мг"});
        recipe.setDescription("best cake");

        ArrayList<Recipe.Step> stepList = new ArrayList<>();
        stepList.add(new Recipe.Step(1,"get egg"));
        stepList.add(new Recipe.Step(2,"get sugar"));
        stepList.add(new Recipe.Step(3,"ready"));
        recipe.setSteps(stepList);

        recipe.setOpen(true);


        recipe.setTitle("napoleon");
        Recipe napoleon = recipe.build();
        Log.d(Recipe.RTAG,napoleon.toString());

        recipe.setTitle("zebra");
        recipe.setOpen(false);
        Recipe zebra = recipe.build();
        Log.d(Recipe.RTAG,zebra.toString());

        recipe.setTitle("pancake");
        Recipe pancake = recipe.build();
        Log.d(Recipe.RTAG,pancake.toString());




    }
}