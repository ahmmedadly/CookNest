package com.example.cooknest.view.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.presenter.MainPresenter;
import com.example.cooknest.view.MainView;
import com.example.cooknest.view.MealDetailsActivity;
import com.example.cooknest.view.adapters.MealAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment implements MainView {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private com.example.cooknest.view.adapters.MealAdapter mealAdapter;
    private MainPresenter presenter;

    // Create the click listener as a class field
    private final MealAdapter.OnMealClickListener mealClickListener = meal -> {
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        intent.putExtra("MEAL_ID", meal.getIdMeal());
        startActivity(intent);
    };

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
        progressBar = view.findViewById(R.id.progressBar);

        // Setup RecyclerView and Adapter
        setupRecyclerView();

        // Initialize presenter and load data
        presenter = new MainPresenter(this, requireActivity());
        loadMeals();
    }

    private void setupRecyclerView() {
        // Initialize the adapter with the click listener
        mealAdapter = new MealAdapter(mealClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mealAdapter);
    }

    private void loadMeals() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getRandomMeal();
    }

    @Override
    public void showRandomMeal(Meal meal) {

    }

    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        if (getView() != null) {
            Snackbar.make(getView(), "Error: " + message, Snackbar.LENGTH_LONG)
                    .setAction("Retry", v -> loadMeals())
                    .show();
        }
        Log.e("HomeFragment", message);
    }

    @Override
    public void showMeals(List<Meal> meals) {
        progressBar.setVisibility(View.GONE);
        if (meals == null || meals.isEmpty()) {
            showError("No meals available");
            return;
        }
        mealAdapter.setMeals(meals);
    }

    // Other interface methods...
    @Override public void showCategories() {}
    @Override public void showAreas() {}
    @Override public void showIngredients() {}
    @Override public void showMessage(String message) {}
}