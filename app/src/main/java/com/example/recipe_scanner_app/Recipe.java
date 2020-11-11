package com.example.recipe_scanner_app;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {

    @SerializedName("name")
    public String name;
    @SerializedName("weather")
    public JSONArray weather;
    @SerializedName("main")
    public JSONObject main_weather;
    @SerializedName("wind")
    public JSONObject wind;

    String description;
    String icon;
    String temp;
    String min_temp;
    String max_temp;
    String humidity;
    String wind_speed;

    public void initiate() throws JSONException {
        name = this.name.getBytes().toString();
        weather = this.weather.getJSONArray(0);
        main_weather = this.main_weather.getJSONObject("main");
        wind = this.wind.getJSONObject("wind");
        description = this.weather.getJSONArray(0).getString(2);
        icon = this.weather.getJSONArray(0).getString(3);
        temp = this.main_weather.getString("temp");
        min_temp = this.main_weather.getString("temp_min");
        max_temp = this.main_weather.getString("temp_max");
        humidity = this.main_weather.getString("humidity");
        wind_speed = this.wind.getString("speed");
    }
}
