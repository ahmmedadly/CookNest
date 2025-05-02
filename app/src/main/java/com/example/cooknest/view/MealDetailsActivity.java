package com.example.cooknest.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.presenter.MealDetailsPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MealDetailsActivity extends AppCompatActivity implements MealDetailsView {
    private MealDetailsPresenter presenter;
    private FloatingActionButton fabFavorite;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        String mealId = getIntent().getStringExtra("MEAL_ID");
        presenter = new MealDetailsPresenter(this, getApplicationContext());

        fabFavorite = findViewById(R.id.fabFavorite);
        fabFavorite.setOnClickListener(v -> toggleFavorite());

        if (mealId != null) {
            presenter.loadMealDetails(mealId);
            presenter.checkIfFavorite(mealId);
        }
    }

    private void toggleFavorite() {
        isFavorite = !isFavorite;
        fabFavorite.setImageResource(isFavorite ?
                R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
        presenter.toggleFavorite();
    }

    @Override
    public void showMealDetails(Meal meal) {
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .into((ImageView) findViewById(R.id.ivMealThumb));

        ((TextView) findViewById(R.id.tvMealName)).setText(meal.getStrMeal());
        ((TextView) findViewById(R.id.tvArea)).setText(meal.getStrArea());
        ((TextView) findViewById(R.id.tvCategory)).setText(meal.getStrCategory());
        ((TextView) findViewById(R.id.tvInstructions)).setText(meal.getStrInstructions());
    }

    @Override
    public void setFavoriteStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
        fabFavorite.setImageResource(isFavorite ?
                R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}