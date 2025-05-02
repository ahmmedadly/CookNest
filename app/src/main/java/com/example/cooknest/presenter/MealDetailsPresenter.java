package com.example.cooknest.presenter;

import android.content.Context;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.ApiService;
import com.example.cooknest.data.network.RetrofitClient;
import com.example.cooknest.view.MealDetailsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailsPresenter {
    private final MealDetailsView view;
    private final MealRepository repository;
    private final ApiService apiService;
    private Meal currentMeal;

    public MealDetailsPresenter(MealDetailsView view, Context context) {
        this.view = view;
        this.repository = new MealRepository(context);
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    public void loadMealDetails(String mealId) {
        apiService.getMealDetails(Integer.parseInt(mealId)).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getMeals().isEmpty()) {
                    currentMeal = response.body().getMeals().get(0);
                    view.showMealDetails(currentMeal);
                } else {
                    view.showError("Failed to load meal details");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }

    public void checkIfFavorite(String mealId) {
        new Thread(() -> {
            Meal meal = repository.getMealById(Integer.parseInt(mealId));
            view.setFavoriteStatus(meal != null && meal.isFavorite());
        }).start();
    }

    public void toggleFavorite() {
        if (currentMeal != null) {
            new Thread(() -> {
                currentMeal.setFavorite(!currentMeal.isFavorite());
                repository.insertMeal(currentMeal);
            }).start();
        }
    }
}