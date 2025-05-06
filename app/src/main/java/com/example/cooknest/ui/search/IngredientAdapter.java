package com.example.cooknest.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private final OnIngredientClickListener listener;

    public interface OnIngredientClickListener {
        void onIngredientClick(String ingredient);
    }

    public IngredientAdapter(List<Ingredient> ingredients, OnIngredientClickListener listener) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        this.listener = listener;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ingredientImage;
        private final TextView ingredientName;

        ViewHolder(View view) {
            super(view);
            ingredientImage = view.findViewById(R.id.ingredient_image);
            ingredientName = view.findViewById(R.id.ingredient_name);
            view.setOnClickListener(v -> listener.onIngredientClick(ingredients.get(getAdapterPosition()).getStrIngredient()));
        }

        void bind(Ingredient ingredient) {
            ingredientName.setText(ingredient.getStrIngredient());
            // Construct ingredient image URL
            String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png";
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(ingredientImage);
        }
    }
}