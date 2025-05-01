package com.example.cooknest.data.db;

import android.content.Context;

import com.example.cooknest.data.model.Meal;

import java.util.List;
import java.util.concurrent.Executors;

public class MealRepository {
    private MealDao mealDao;

    public MealRepository(Context context) {
        MealsDatabase db = MealsDatabase.getInstance(context);
        mealDao = db.mealDao();
    }

    public void insertMeal(Meal meal) {
        Executors.newSingleThreadExecutor().execute(() -> mealDao.insertMeal(meal));
    }

    public List<Meal> getAllMeals() {
        return mealDao.getAllMeals();
    }

    public List<Meal> getFavorites() {
        return mealDao.getFavorites();
    }

    public void cacheApiMeals(List<Meal> meals) {
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Meal meal : meals) {
                mealDao.insertMeal(meal);
            }
        });
    }
}
