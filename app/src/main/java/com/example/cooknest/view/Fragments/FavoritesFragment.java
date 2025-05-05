package com.example.cooknest.view.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cooknest.R;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.Adapters.MealAdapter;
import com.example.cooknest.view.MealDetailsActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MealRepository mealRepository;
    private MealAdapter adapter;
    private TextView tvEmpty;
    private SwipeRefreshLayout swipeRefreshFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.rvFavorites);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mealRepository = new MealRepository(requireActivity());
        setupAdapter();
        recyclerView.setAdapter(adapter);
        swipeRefreshFavorites = view.findViewById(R.id.swipeRefresh);
        swipeRefreshFavorites.setOnRefreshListener(() -> {
            loadFavorites();  // Reload data
        });

        loadFavorites();
        Log.d("FavoritesFragment", "Favorites fragment created");

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void setupAdapter() {
        adapter = new MealAdapter(
                meal -> {
                    // Handle meal click - open details
                    Log.d("FavoritesFragment", "Clicked meal ID: " + meal.getIdMeal());
                    Intent intent = new Intent(getContext(), MealDetailsActivity.class);
                    intent.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, String.valueOf(meal.getIdMeal()));
                    startActivity(intent);
                },
                (meal, isFavorite) -> {
                    // Handle favorite toggle
                    mealRepository.insertMeal(meal);
                    if (isFavorite) {
                        Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {//snak bar to undo delete from favorite
                        mealRepository.deleteMeal(meal);
                        // Show Snackbar with Undo
                        Snackbar snackbar = Snackbar.make(
                                requireView(),
                                "Removed from favorites",
                                Snackbar.LENGTH_LONG
                        );
                        snackbar.setAction("Undo", v -> {
                            meal.setFavorite(true);
                            mealRepository.insertMeal(meal);
                            loadFavorites();
                            Toast.makeText(getContext(), "Restored to favorites", Toast.LENGTH_SHORT).show();
                        });
                        snackbar.show();
                        //to reload data after remove from favorite
                        loadFavorites();

                    }
                }
        );
    }
    private void loadFavorites () {

        MutableLiveData<List <Meal> >mealLiveData = new MutableLiveData<>();
        mealLiveData.observe(getViewLifecycleOwner(), meals -> {
            if (meals != null && !meals.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                adapter.setMeals(mealLiveData.getValue());
                Log.d("HomeFragment", "Meals loaded: " + meals.size());
            } else {
                Log.d("HomeFragment", "No meals found.");
            }
        });

        new Thread(() -> {
            List<Meal> favorites = mealRepository.getAllFavoriteMeals();
            requireActivity().runOnUiThread(() -> {
                swipeRefreshFavorites.setRefreshing(false);
                if (favorites == null || favorites.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    Log.d("FavoritesFragment", "Favorites loaded: " + favorites.size());
                    tvEmpty.setVisibility(View.GONE);
                    mealLiveData.postValue(favorites);
                }
            });
        }).start();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }
}