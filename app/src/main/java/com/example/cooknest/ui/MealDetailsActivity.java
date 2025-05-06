package com.example.cooknest.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.contract.MealDetailsView;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.presenter.MealDetailsPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MealDetailsActivity extends AppCompatActivity implements MealDetailsView {
    private MealDetailsPresenter presenter;
    private FloatingActionButton fabFavorite;
    private boolean isFavorite = false;
    private Meal currentMeal;
    public static final String EXTRA_MEAL_ID= "MEAL_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        String mealId = getIntent().getStringExtra(EXTRA_MEAL_ID);


        Log.d("MealDetailsActivity", "MealId: " + mealId);

        presenter = new MealDetailsPresenter(this, getApplicationContext());

        fabFavorite = findViewById(R.id.fabFavorite);
        fabFavorite.setOnClickListener(v -> toggleFavorite());

        if (mealId != null) {
            presenter.loadMealDetails(mealId);
            presenter.checkIfFavorite(mealId);
        }
    }

    private void toggleFavorite() {
        if (currentMeal != null) {
        isFavorite = !isFavorite;
                currentMeal.setFavorite(isFavorite);
        fabFavorite.setImageResource(isFavorite ?
                R.drawable.favorite_f : R.drawable.favorite_nf);
            presenter.toggleFavorite(currentMeal);
        }
    }

    @Override
    public void showMealDetails(Meal meal) {
        if (meal == null) {
            showError("No meal details available");
            return;
        }
        this.currentMeal = meal;
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .into((ImageView) findViewById(R.id.ivMealThumb));

        ((TextView) findViewById(R.id.tvMealName)).setText(meal.getStrMeal());
        ((TextView) findViewById(R.id.tvArea)).setText(meal.getStrArea());
        ((TextView) findViewById(R.id.tvCategory)).setText(meal.getStrCategory());
        String instructions = meal.getStrInstructions()
                .replace("\r\n", "\n")  // Fix line endings
                .replace(". ", ".\n• "); // Add bullet points
        ((TextView) findViewById(R.id.tvInstructions)).setText("• " + instructions);    }

    @Override
    public void setFavoriteStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
        fabFavorite.setImageResource(isFavorite ?
                R.drawable.favorite_f : R.drawable.favorite_nf);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
        finish();
    }
    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}