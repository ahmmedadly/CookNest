package com.example.cooknest.data.db;

import android.content.Context;
import com.example.cooknest.data.db.MealDao;
import com.example.cooknest.data.db.MealsDatabase;
import com.example.cooknest.data.model.Meal;
import java.util.List;

public class MealRepository {
    private MealDao mealDao;

    public MealRepository(Context context) {
        MealsDatabase database = MealsDatabase.getInstance(context);
        mealDao = database.mealDao();
    }

    public void insertMeal(Meal meal) {
        new Thread(() -> mealDao.insertMeal(meal)).start();
    }

    public void deleteMeal(Meal meal) {
        new Thread(() -> mealDao.deleteMeal(meal)).start();
    }

    public List<Meal> getAllFavoriteMeals() {
        return mealDao.getAllFavoriteMeals();
    }

    public List<Meal> getAllPlannedMeals() {
        return mealDao.getAllPlannedMeals();
    }

    public Meal getMealById(int mealId) {
        return mealDao.getMealById(mealId);
    }
}