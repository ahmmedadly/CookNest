package com.example.cooknest.presenter;

import android.content.Context;
import android.util.Log;

import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.ApiService;
import com.example.cooknest.data.network.RetrofitClient;
import com.example.cooknest.contract.MainView;
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
        view.showLoading(true);
        Log.d("API_CALL", "Fetching random meal");
        apiService.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                view.showLoading(false);
                Log.d("API_RESPONSE", "Status: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        Log.d("API_DATA", "Received meal: " + meals.get(0).getStrMeal());
                        view.showRandomMeal(meals.get(0));
                    } else {
                        view.showError("No meals in response");
                    }
                } else {
                    view.showError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.showLoading(false);
                Log.e("API_FAILURE", "Error: ", t);
                view.showError(t.getMessage());
            }
        });
    }

}