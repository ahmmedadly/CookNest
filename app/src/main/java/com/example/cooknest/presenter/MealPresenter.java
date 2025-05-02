package com.example.cooknest.presenter;

import android.content.Context;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.ApiService;
import com.example.cooknest.data.network.RetrofitClient;
import com.example.cooknest.view.MealView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealPresenter {
    private MealView view;
    private MealRepository repository;
    private ApiService apiService;

    public MealPresenter(MealView view, Context context) {
        this.view = view;
        this.repository = new MealRepository(context);
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    public void getMealDetails(int mealId) {
        apiService.getMealDetails(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    view.showMealDetails(response.body().getMeals().get(0));
                } else {
                    view.showError("Failed to fetch meal details");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }

    public void checkIfFavorite(int mealId) {
        new Thread(() -> {
            Meal meal = repository.getMealById(mealId);
            view.setFavoriteStatus(meal != null && meal.isFavorite());
        }).start();
    }

    public void toggleFavorite(Meal meal, boolean isFavorite) {
        meal.setFavorite(isFavorite);
        repository.insertMeal(meal);
    }
}