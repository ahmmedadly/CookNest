package com.example.cooknest.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cooknest.data.model.Meal;

 @Database(entities = {Meal.class}, version = 1)
public abstract class MealsDatabase extends RoomDatabase {
    public abstract MealDao mealDao();

    private static MealsDatabase instance;

    public static synchronized MealsDatabase getInstance(
            Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealsDatabase.class, "meals_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

