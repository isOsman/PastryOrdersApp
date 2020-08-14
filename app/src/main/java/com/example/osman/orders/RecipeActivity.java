package com.example.osman.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.osman.orders.recipes.Recipe;
import com.example.osman.orders.recipes.RecipeCardAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setTitle("Пирог на адылфоваджофыва");
        recipe.setImgId(R.drawable.lion);
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
        recipeArrayList.add(recipe.build());
        recipe.setImgId(R.drawable.idol);
        Recipe napoleon = recipe.build();
        recipeArrayList.add(napoleon);
        Log.d(Recipe.RTAG,napoleon.toString());

        recipe.setTitle("zebra");
        recipe.setOpen(false);
        recipe.setDifficult(Recipe.DIFF_MEDIUM);
        Recipe zebra = recipe.build();
        recipeArrayList.add(zebra);
        Log.d(Recipe.RTAG,zebra.toString());

        recipe.setTitle("Пирог на пироге,дрова во дворе");
        recipe.setDifficult(Recipe.DIFF_HARD);
        recipe.setImgId(R.drawable.lion);
        recipe.setDescription("Очень очень дорогой и очень очень оченьский торт.");
        Recipe pancake = recipe.build();
        recipeArrayList.add(pancake);
        Log.d(Recipe.RTAG,pancake.toString());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this,recipeArrayList);
        recyclerView.setAdapter(recipeCardAdapter);






    }
}