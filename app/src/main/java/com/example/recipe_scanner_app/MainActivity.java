package com.example.recipe_scanner_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MenuItem;
import android.widget.SearchView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final ArrayList<Bitmap> recipePreviews = new ArrayList<>();
    final ArrayList<String> recipeNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.searchView);
        /*
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuRecyclerAdapter menuRecyclerAdapter = new MenuRecyclerAdapter(recipePreviews, recipeNames);
        recyclerView.setAdapter(menuRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.recipe_preview_view || item.getItemId() == R.id.recipe_name_view) {
            Intent intent = new Intent(MainActivity.this, DisplayRecipeActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        /*
        final RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);

        Call<Recipe> call = recipeAPI.getData();
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if(response.isSuccessful()) {
                    Recipe recipe = response.body();
                    try {
                        recipe.initiate();
                        recipeList.add(recipe);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) { }
        });*/
    }
}