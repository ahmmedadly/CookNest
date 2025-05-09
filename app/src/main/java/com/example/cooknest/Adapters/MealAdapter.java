package com.example.cooknest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Meal;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> meals;
    private final OnMealClickListener listener;
    private final OnFavoriteClickListener favoriteListener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Meal meal, boolean isFavorite);
    }

    public MealAdapter(OnMealClickListener listener, OnFavoriteClickListener favoriteListener) {
        this.listener = listener;
        this.favoriteListener = favoriteListener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivMeal;
        private final TextView tvMealName, tvMealCategory;
        private final ImageButton btnFavorite;
        private final MaterialButton btnViewDetails;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvMealCategory = itemView.findViewById(R.id.tvMealCategory);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }

        void bind(Meal meal) {
            tvMealName.setText(meal.getStrMeal());
            tvMealCategory.setText(meal.getStrCategory());
            Glide.with(itemView.getContext())
                    .load(meal.getStrMealThumb())
                    .into(ivMeal);

            btnFavorite.setImageResource(meal.isFavorite() ?
                    R.drawable.favorite_f : R.drawable.favorite_nf);

            btnFavorite.setOnClickListener(v -> {
                boolean newState = !meal.isFavorite();
                meal.setFavorite(newState);
                btnFavorite.setImageResource(newState ?
                        R.drawable.favorite_f : R.drawable.favorite_nf);
                favoriteListener.onFavoriteClick(meal, newState);
            });

            itemView.setOnClickListener(v -> listener.onMealClick(meal));
            btnViewDetails.setOnClickListener(v -> listener.onMealClick(meal));

        }
    }
}