package com.example.cooknest.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cooknest.data.model.Meal;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("SELECT * FROM favorite_meals WHERE isFavorite = 1")
    List<Meal> getAllFavoriteMeals();

    @Query("SELECT * FROM favorite_meals WHERE isPlanned = 1")
    List<Meal> getAllPlannedMeals();
    @Query("SELECT * FROM favorite_meals WHERE idMeal = :mealId")
    Meal getMealById(int mealId);
}