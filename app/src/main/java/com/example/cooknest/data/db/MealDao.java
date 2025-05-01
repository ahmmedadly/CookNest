package com.example.cooknest.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cooknest.data.model.Meal;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Query("SELECT * FROM meals")
    List<Meal> getAllMeals();

    @Query("SELECT * FROM meals WHERE isFavorite = 1")
    List<Meal> getFavorites();

    @Query("SELECT * FROM meals WHERE idMeal = :id")
    Meal getMealById(String id);

    @Query("DELETE FROM meals")
    void clearMeals();
}

