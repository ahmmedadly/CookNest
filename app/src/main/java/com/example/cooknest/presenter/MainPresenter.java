package com.example.cooknest.presenter;

import android.content.Context;
import android.util.Log;

import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.ApiService;
import com.example.cooknest.data.network.RetrofitClient;
import com.example.cooknest.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainPresenter {
    private final MainView view;
    private final MealRepository repository;
    private final ApiService apiService;

    public MainPresenter(MainView view, Context context) {
        this.view = view;
        this.repository = new MealRepository(context);
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    public void getRandomMeal() {
        Log.d("API_CALL", "Fetching random meal");
        apiService.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                Log.d("API_RESPONSE", "Status: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        view.showError("Empty response body");
                        return;
                    }
                    Log.d("API_DATA", "Received: " + response.body().toString());
                    view.showMeals(response.body().getMeals());
                } else {
                    view.showError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Error: ", t);
                view.showError("Network error: " + t.getMessage());
            }
        });
    }

    public void getMealsByCategory(String category) {
        apiService.getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showMeals(response.body().getMeals());
                } else {
                    view.showError("No meals found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showError(t.getMessage());
            }

        });
    }
}