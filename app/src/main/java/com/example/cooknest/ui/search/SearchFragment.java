package com.example.cooknest.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cooknest.Adapters.MealAdapter;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.network.ApiService;
import com.example.cooknest.data.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private TextInputLayout textInputLayout;
    private TextInputEditText etSearch;
    private TabLayout tabLayout;
    private RecyclerView rvSearchResults;
    private MealAdapter adapter;

    private ApiService apiService;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Init views
        textInputLayout = view.findViewById(R.id.textInputLayout);
        etSearch = view.findViewById(R.id.etSearch);
        tabLayout = view.findViewById(R.id.tabLayout);
        rvSearchResults = view.findViewById(R.id.rvSearchResults);

        // Init API
        apiService = RetrofitClient.getInstance().getApiService();

        // Setup RecyclerView
        adapter = new MealAdapter(meal -> {
            // Handle meal click
            Toast.makeText(getContext(), "Meal clicked: " + meal.getStrMeal(), Toast.LENGTH_SHORT).show();
        }, (meal, isFavorite) -> {
            // Handle favorite click
            Toast.makeText(getContext(), "Favorite clicked: " + meal.getStrMeal(), Toast.LENGTH_SHORT).show();
        });
        if (getContext() != null) {
            adapter.setMeals(new ArrayList<>());
            adapter.notifyDataSetChanged();
            rvSearchResults.setAdapter(adapter);
            rvSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        rvSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchResults.setAdapter(adapter);

        // Handle search icon click
        textInputLayout.setEndIconOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                performSearch(query);
            } else {
                Toast.makeText(getContext(), "Please enter a search keyword", Toast.LENGTH_SHORT).show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        textInputLayout.setHint("Enter category name");
                        break;
                    case 1:
                        textInputLayout.setHint("Enter area (country)");
                        break;
                    case 2:
                        textInputLayout.setHint("Enter ingredient");
                        break;
                    default:
                        textInputLayout.setHint("Enter Meal Name");
                        break;
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });

        return view;
    }

    private void performSearch(String query) {
        int selectedTab = tabLayout.getSelectedTabPosition();
        Call<MealResponse> call = null;

        try {
            switch (selectedTab) {
                case 0: // Category
                    call = apiService.getMealsByCategory(query);
                    break;
                case 1: // Area
                    call = apiService.getMealsByArea(query);
                    break;
                case 2: // Ingredient
                    call = apiService.getMealsByIngredient(query);
                    break;
                default://Search all
                    call = apiService.searchMeals(query);
                    break;
            }

            if (call != null) {
                call.enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Meal> results = response.body().getMeals();
                            if (results != null && !results.isEmpty()) {
                                adapter.setMeals(results);
                            } else {
                                showEmptyState();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Search error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmptyState() {
        adapter.setMeals(new ArrayList<>());
        Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
    }
}