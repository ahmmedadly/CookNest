package com.example.cooknest.data.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlannedMeals")
public class PlannedMeals {

    @PrimaryKey(autoGenerate = true)
    private int mealId; // Foreign key to Meal
    private String date;

    public PlannedMeals(int mealId, String date) {

        this.mealId= mealId;

        this.date = date;
    }

    public void setMealId(int mealId) {
        this.mealId= mealId;
    }

    public void setDate( String date) {
        this.date = date;
    }

    public int getMealId() {
        return mealId;
    }

    public String getDate() {
        return date;
    }

}
