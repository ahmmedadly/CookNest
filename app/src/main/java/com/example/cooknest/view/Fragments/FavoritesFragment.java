package com.example.cooknest.view.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cooknest.R;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Meal;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MealRepository mealRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.rvFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mealRepository = new MealRepository(requireActivity());
        loadFavorites();
        return view;
    }

    private void loadFavorites() {
        new Thread(() -> {
            List<Meal> favorites = mealRepository.getAllFavoriteMeals();
            requireActivity().runOnUiThread(() -> {
                // Setup adapter with favorites
            });
        }).start();
    }
}