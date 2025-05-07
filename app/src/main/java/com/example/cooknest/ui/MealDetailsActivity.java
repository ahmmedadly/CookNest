package com.example.cooknest.ui;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.contract.MealDetailsView;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.presenter.MealDetailsPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

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

        // Load meal thumbnail
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into((ImageView) findViewById(R.id.ivMealThumb));

        // Set text views
        ((TextView) findViewById(R.id.tvMealName)).setText(meal.getStrMeal());
        ((TextView) findViewById(R.id.tvArea)).setText(meal.getStrArea());
        ((TextView) findViewById(R.id.tvCategory)).setText(meal.getStrCategory());

        // Format and set instructions
        String instructions = meal.getStrInstructions().replace("\r\n", "\n").replace(". ", ".\n• ");
        ((TextView) findViewById(R.id.tvInstructions)).setText("• " + instructions);

        // Populate ingredients with images
        LinearLayout ingredientsContainer = findViewById(R.id.ingredientsContainer);
        ingredientsContainer.removeAllViews();

        for (int i = 1; i <= 20; i++) {
            String ingredient = meal.getIngredient(i);
            String measure = meal.getMeasure(i);
            if (ingredient != null && !ingredient.trim().isEmpty()) {
                // Construct image URL
                String sanitizedIngredient = ingredient.replace(" ", "_");
                String imageUrl = "https://www.themealdb.com/images/ingredients/" + sanitizedIngredient + "-Small.png";
                Log.d("MealDetailsActivity", "Loading ingredient " + i + ": " + ingredient + ", URL: " + imageUrl);

                // Create ImageView for ingredient image
                ImageView iv = new ImageView(this);
                int size = (int) (50 * getResources().getDisplayMetrics().density); // 50dp
                iv.setLayoutParams(new LinearLayout.LayoutParams(size, size));
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(iv);

                // Create TextView for measure and ingredient
                TextView tv = new TextView(this);
                tv.setText((measure != null && !measure.trim().isEmpty() ? measure + " " : "") + ingredient);
                tv.setPadding(10, 0, 0, 10);

                // Create horizontal LinearLayout row
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.addView(iv);
                row.addView(tv);
                ingredientsContainer.addView(row);
            }
        }

        // Embed YouTube video if available
        if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {
            String videoId = extractYoutubeVideoId(meal.getStrYoutube());
            if (videoId != null) {
                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtubePlayerView);
                getLifecycle().addObserver(youTubePlayerView);
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0);
                    }
                });
            }
        }
    }

    // Helper method to extract YouTube video ID
    private String extractYoutubeVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*";
            java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher matcher = compiledPattern.matcher(youtubeUrl);
            if (matcher.find()) {
                videoId = matcher.group();
            }
        }
        return videoId;
    }
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