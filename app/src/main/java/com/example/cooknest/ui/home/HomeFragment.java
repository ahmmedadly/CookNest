package com.example.cooknest.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cooknest.ui.search.AreaAdapter;
import com.example.cooknest.ui.search.CategoryAdapter;
import com.example.cooknest.ui.search.IngredientAdapter;
import com.example.cooknest.Adapters.MealAdapter;
import com.example.cooknest.R;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.AreaResponse;
import com.example.cooknest.data.model.CategoryResponse;
import com.example.cooknest.data.model.IngredientResponse;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.ApiService;
import com.example.cooknest.data.network.RetrofitClient;
import com.example.cooknest.presenter.MainPresenter;
import com.example.cooknest.contract.MainView;
import com.example.cooknest.ui.MealDetailsActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements MainView {

    private RecyclerView recyclerView, rvCategories, rvAreas, rvIngredients;
    private MealAdapter adapter;
    private MainPresenter presenter;
    private SwipeRefreshLayout swipeRefresh;
    private MealRepository mealRepository;
    private CategoryAdapter categoryAdapter;
    private AreaAdapter areaAdapter;
    private IngredientAdapter ingredientAdapter;
    private ApiService apiService;
    private RecyclerView rvLazyMeals;
    private MealAdapter lazyMealAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        recyclerView = view.findViewById(R.id.rvMeals);
        rvCategories = view.findViewById(R.id.rvCategories);
        rvAreas = view.findViewById(R.id.rvAreas);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        rvLazyMeals = view.findViewById(R.id.rvLazyMeals);
        lazyMealAdapter = new MealAdapter(
                meal -> {
                    Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                    intent.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, String.valueOf(meal.getIdMeal()));
                    startActivity(intent);
                },
                (meal, isFavorite) -> {
                    meal.setFavorite(isFavorite);
                    if (isFavorite) {
                        mealRepository.insertMeal(meal);
                    } else {
                        mealRepository.deleteMeal(meal);
                    }
                    Toast.makeText(getContext(),
                            isFavorite ? "Added to favorites" : "Removed from favorites",
                            Toast.LENGTH_SHORT).show();
                }
        );
        rvLazyMeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvLazyMeals.setAdapter(lazyMealAdapter);


        mealRepository = new MealRepository(requireActivity());
        apiService = RetrofitClient.getInstance().getApiService();

        // Initialize Presenter
        presenter = new MainPresenter(this, requireActivity());

        // Setup Adapters
        setupMealAdapter();
        setupCategoryAreaIngredientAdapters();

        // Setup Swipe to Refresh
        swipeRefresh.setOnRefreshListener(() -> {
            loadMeals();
            loadLazyMeals();
            fetchCategoriesAreasIngredients();
            swipeRefresh.setRefreshing(false);
        });

        // Load data
        loadMeals();
        loadLazyMeals();
        fetchCategoriesAreasIngredients();
    }

    private void setupMealAdapter() {
        adapter = new MealAdapter(
                meal -> {
                    Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                    intent.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, String.valueOf(meal.getIdMeal()));
                    startActivity(intent);
                },
                (meal, isFavorite) -> {
                    meal.setFavorite(isFavorite);
                    if (isFavorite) {
                        mealRepository.insertMeal(meal);
                    } else {
                        mealRepository.deleteMeal(meal);
                    }
                    Toast.makeText(getContext(),
                            isFavorite ? "Added to favorites" : "Removed from favorites",
                            Toast.LENGTH_SHORT).show();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupCategoryAreaIngredientAdapters() {
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), category -> {
            apiService.getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
                @Override
                public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful()) {
                        adapter.setMeals(response.body().getMeals());
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                }
            });
        });
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);

        areaAdapter = new AreaAdapter(new ArrayList<>(), area -> {
            apiService.getMealsByArea(area).enqueue(new Callback<MealResponse>() {
                @Override
                public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful()) {
                        adapter.setMeals(response.body().getMeals());
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                }
            });
        }, getContext()); // Pass the context
        rvAreas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvAreas.setAdapter(areaAdapter);

        ingredientAdapter = new IngredientAdapter(new ArrayList<>(), ingredient -> {
            apiService.getMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
                @Override
                public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful()) {
                        adapter.setMeals(response.body().getMeals());
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                }
            });
        });
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvIngredients.setAdapter(ingredientAdapter);
    }

    private void fetchCategoriesAreasIngredients() {
        apiService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    categoryAdapter.setCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
            }
        });

        apiService.getAreas().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    areaAdapter.setAreas(response.body().getAreas());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
            }
        });

        apiService.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful()) {
                    ingredientAdapter.setIngredients(response.body().getIngredients());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
            }
        });
    }
    private void loadLazyMeals() {
        List<Meal> lazyMeals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            apiService.getRandomMeal().enqueue(new Callback<MealResponse>() {
                @Override
                public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Meal meal = response.body().getMeals().get(0);
                        if (!lazyMeals.contains(meal)) {
                            lazyMeals.add(meal);
                        }

                        if (lazyMeals.size() == 5) {
                            lazyMealAdapter.setMeals(lazyMeals);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                    Log.e("HomeFragment", "Failed to fetch lazy meal: " + t.getMessage());
                }
            });
        }
    }


    private void loadMeals() {
        presenter.getRandomMeal();
    }

    @Override
    public void showRandomMeal(Meal meal) {
        adapter.setMeals(Collections.singletonList(meal));
    }

    @Override
    public void showMeals(List<Meal> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void showCategories() {
        // Already handled by Retrofit call
    }

    @Override
    public void showAreas() {
        // Already handled by Retrofit call
    }

    @Override
    public void showIngredients() {
        // Already handled by Retrofit call
    }

    @Override
    public void showLoading(boolean isLoading) {
                swipeRefresh.setRefreshing(isLoading);
    }

    @Override
    public void showError(String message) {
                if (getView() != null) {
            Snackbar.make(getView(), "Error: " + message, Snackbar.LENGTH_LONG)
                    .setAction("Retry", v -> {
                        loadMeals();
                        fetchCategoriesAreasIngredients();
                    })
                    .show();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
