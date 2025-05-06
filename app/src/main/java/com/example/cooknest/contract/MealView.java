package com.example.cooknest.contract;

import com.example.cooknest.data.model.Meal;

public interface MealView {
    void showMealDetails(Meal meal);
    void setFavoriteStatus(boolean isFavorite);
    void showError(String message);
}