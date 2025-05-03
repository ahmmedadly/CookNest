package com.example.cooknest.data.model;

import com.example.cooknest.data.model.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MealResponse {
    @SerializedName("meals")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals != null ? meals : new ArrayList<>();
    }
}