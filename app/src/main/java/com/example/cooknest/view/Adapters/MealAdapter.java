package com.example.cooknest.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Meal;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> meals;
    private OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    public MealAdapter(OnMealClickListener listener) {
        this.listener = listener;
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
        private final TextView tvMealName;
        private final TextView tvMealCategory;
        private final Button btnViewDetails;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvMealCategory = itemView.findViewById(R.id.tvMealCategory);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }

        public void bind(Meal meal) {
            tvMealName.setText(meal.getStrMeal());
            tvMealCategory.setText(meal.getStrCategory());
            Glide.with(itemView.getContext())
                    .load(meal.getStrMealThumb())
                    .into(ivMeal);

            btnViewDetails.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMealClick(meal);
                }
            });
        }
    }
}