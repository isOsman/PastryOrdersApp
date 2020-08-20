package com.example.osman.orders;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;

import com.example.osman.orders.recipes.Recipe;
import com.example.osman.orders.recipes.RecipeDetailAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String RECIPE_EXTRA = "RECIPE";
    public static final String TAG = "RD_TAG";

    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager viewPager;

    private RecipeDetailAdapter pagerAdapter;

    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_EXTRA);

//        Log.d(TAG, recipe.toString());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getTitle());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        viewPager = (ViewPager) findViewById(R.id.pager);

        tabs = (TabLayout) findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(viewPager);


        pagerAdapter = new RecipeDetailAdapter(getSupportFragmentManager(),recipe,this,viewPager);
        viewPager.setAdapter(pagerAdapter);




    }
}