package com.example.recipe_scanner_app;

public class RecipeAPI {
    @GET("data/2.5/weather?units=metric&appid=d4bcd386f260da1156f5004738841477")
    Call<Recipe> getData(@Query("q") String location);
}
