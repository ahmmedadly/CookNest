package com.example.cooknest.view;

import com.example.cooknest.data.model.Meal;
import java.util.List;

public interface MainView {
    void showRandomMeal(Meal meal);
    void showMeals(List<Meal> meals);
    void showCategories();
    void showAreas();
    void showIngredients();
    void showError(String message);
    void showMessage(String message);
}