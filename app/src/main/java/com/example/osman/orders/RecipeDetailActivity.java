package com.example.osman.orders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.osman.orders.recipes.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String RECIPE_EXTRA = "RECIPE";
    public static final String TAG = "RD_TAG";

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_EXTRA);
        Log.d(TAG, recipe.toString());
    }
}