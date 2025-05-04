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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.cooknest.R;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.presenter.MainPresenter;
import com.example.cooknest.view.MainView;
import com.example.cooknest.view.MealDetailsActivity;
import com.example.cooknest.Adapters.MealAdapter;
import com.google.android.material.snackbar.Snackbar;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements MainView {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MealAdapter adapter;
    private MainPresenter presenter;
    private SwipeRefreshLayout swipeRefresh;
    private MealRepository mealRepository;

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
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        mealRepository = new MealRepository(requireActivity());
        // Setup adapter
        adapter = new MealAdapter(
                meal -> {
                    Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                    intent.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, String.valueOf(meal.getIdMeal()));
                    startActivity(intent);
                },
                (meal, isFavorite) -> {
                    meal.setFavorite(isFavorite);
                    if(isFavorite) {
                        mealRepository.insertMeal(meal);
                    }else {
                        mealRepository.deleteMeal(meal);
                    }
                    Toast.makeText(getContext(),
                            isFavorite ? "Added to favorites" : "Removed from favorites",
                            Toast.LENGTH_SHORT).show();
                }
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Setup swipe refresh
        swipeRefresh.setOnRefreshListener(() -> {
            loadMeals();
            swipeRefresh.setRefreshing(false);
        });

        // Initialize presenter
        presenter = new MainPresenter(this, requireActivity());
        loadMeals();
    }

    private void loadMeals() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getRandomMeal();
    }

    @Override
    public void showRandomMeal(Meal meal) {
        progressBar.setVisibility(View.GONE);
        if (adapter != null) {
            adapter.setMeals(Collections.singletonList(meal));
        } else {
            Log.e("HomeFragment", "Adapter is null when showing meal");
        }
    }



    @Override
    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        if (getView() != null) {
            Snackbar.make(getView(), "Error: " + message, Snackbar.LENGTH_LONG)
                    .setAction("Retry", v -> loadMeals())
                    .show();
        }
    }

    // Other interface methods...
    @Override public void showMeals(List<Meal> meals) {}
    @Override public void showCategories() {}
    @Override public void showAreas() {}
    @Override public void showIngredients() {}

    @Override
    public void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        swipeRefresh.setRefreshing(isLoading);
    }


    @Override public void showMessage(String message) {}
}