package com.example.cooknest.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cooknest.data.model.PlannedMeals;

import java.util.List;
@Dao
public interface PlannedMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlannedMeal(PlannedMeals plannedMeal);

    @Delete
    void deletePlannedMeal(PlannedMeals plannedMeal);

    @Query("SELECT * FROM PlannedMeals")
    List<PlannedMeals> getAllPlannedMeals();

    @Query("SELECT * FROM PlannedMeals WHERE mealId = :mealId")
    PlannedMeals getPlannedMealById(int mealId);

    @Query("SELECT * FROM PlannedMeals WHERE date = :date")
    List<PlannedMeals> getPlannedMealsByDate(String date);

}
