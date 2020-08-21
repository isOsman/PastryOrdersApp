package com.example.osman.orders;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osman.orders.recipes.Recipe;
import com.example.osman.orders.recipes.RecipeCardAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecipeCardAdapter recipeCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.recipesItem);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<Recipe> recipeArrayList = null;
        try {
            recipeArrayList = Storage.getInstance(this).getRecipes();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recipeCardAdapter = new RecipeCardAdapter(this,recipeArrayList);
        recyclerView.setAdapter(recipeCardAdapter);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recipeCardAdapter.notifyDataSetChanged();
    }
}