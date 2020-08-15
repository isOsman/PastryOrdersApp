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

        ArrayList<Recipe> recipeArrayList = null;
        try {
            recipeArrayList = Storage.getInstance(this).getRecipes();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this,recipeArrayList);
        recyclerView.setAdapter(recipeCardAdapter);






    }
}