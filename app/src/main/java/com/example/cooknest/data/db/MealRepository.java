package com.example.cooknest.data.db;

import android.content.Context;
import com.example.cooknest.data.db.MealDao;
import com.example.cooknest.data.db.MealsDatabase;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.PlannedMeals;

import java.util.List;

public class MealRepository {
    private MealDao mealDao;
    private PlannedMealDao plannedMealDao;


    public MealRepository(Context context) {
        MealsDatabase database = MealsDatabase.getInstance(context);
        mealDao = database.mealDao();
        plannedMealDao = database.plannedMealDao();
    }


    public void insertPlannedMeal(PlannedMeals plannedMeal) {
        new Thread(() -> plannedMealDao.insertPlannedMeal(plannedMeal)).start();
    }

    public void deletePlannedMeal(PlannedMeals plannedMeal) {
        new Thread(() -> plannedMealDao.deletePlannedMeal(plannedMeal)).start();
    }

    public List<PlannedMeals> getAllPlannedMeals() {
        return plannedMealDao.getAllPlannedMeals();
    }

    public PlannedMeals getPlannedMealById(int mealId) {
        return plannedMealDao.getPlannedMealById(mealId);
    }

    public List<PlannedMeals> getPlannedMealsByDate(String date) {
        return plannedMealDao.getPlannedMealsByDate(date);
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

    public Meal getMealById(int mealId) {
        return mealDao.getMealById(mealId);
    }
}