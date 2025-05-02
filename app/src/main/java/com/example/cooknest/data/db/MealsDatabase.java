package com.example.cooknest.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.cooknest.data.model.Category;
import com.example.cooknest.data.model.Meal;

@Database(entities = {Meal.class, Category.class}, version = 1, exportSchema = false)
public abstract class MealsDatabase extends RoomDatabase {
    private static MealsDatabase instance;

    public abstract MealDao mealDao();

    public static synchronized MealsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealsDatabase.class, "meals_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}