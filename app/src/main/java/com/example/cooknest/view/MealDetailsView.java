package com.example.cooknest.view;

import com.example.cooknest.data.model.Meal;

public interface MealDetailsView {
    void showMealDetails(Meal meal);
    void setFavoriteStatus(boolean isFavorite);
    void showError(String message);
}