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

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MealRepository mealRepository;
    private MealAdapter adapter;
    private TextView tvEmpty;

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
        loadFavorites();
        Log.d("FavoritesFragment", "Favorites fragment created");

        return view;
    }
/*@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    mealRepository = new MealRepository(requireActivity());
    adapter = new MealAdapter(
            meal -> {
                // Handle meal click
                Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                intent.putExtra("MEAL_ID", meal.getIdMeal());
                startActivity(intent);
            },
            (meal, isFavorite) -> {
                // Handle favorite toggle
                if (!isFavorite) {
                    mealRepository.deleteMeal(meal);
                    Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                    loadFavorites(); // Refresh the list after removal
                }
            }
    );}*/
    private void setupAdapter() {
         adapter = new MealAdapter(
                meal -> {
                    // Handle meal click - open details
                    Intent intent = new Intent(getContext(), MealDetailsActivity.class);
                    intent.putExtra("MEAL_ID", meal.getIdMeal());
                    startActivity(intent);
                },
                (meal, isFavorite) -> {
                    // Handle favorite toggle
                    mealRepository.insertMeal(meal);
                    if (isFavorite) {
                        Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
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
                    recyclerView.setAdapter(adapter);
                    Log.d("HomeFragment", "Meals loaded: " + meals.size());
                } else {
                    Log.d("HomeFragment", "No meals found.");
                }
            });

            new Thread(() -> {
                List<Meal> favorites = mealRepository.getAllFavoriteMeals();
                requireActivity().runOnUiThread(() -> {
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
    }