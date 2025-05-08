package com.example.cooknest.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.contract.MealDetailsView;
import com.example.cooknest.data.db.MealRepository;
import com.example.cooknest.data.model.Ingredient;
import com.example.cooknest.data.model.Meal;
import com.example.cooknest.data.model.MealResponse;
import com.example.cooknest.data.model.PlannedMeals;
import com.example.cooknest.presenter.MealDetailsPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MealDetailsActivity extends AppCompatActivity implements MealDetailsView {
    private MealDetailsPresenter presenter;
    private FloatingActionButton fabFavorite;
    private boolean isFavorite = false;
    private Meal currentMeal;

    private FloatingActionButton btnAddToCalendar;
    Meal meal;
    public static final String EXTRA_MEAL_ID= "MEAL_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        // String mealId = getIntent().getStringExtra(EXTRA_MEAL_ID);

        meal = (Meal) getIntent().getSerializableExtra(EXTRA_MEAL_ID);


        btnAddToCalendar = findViewById(R.id.btnAddToCalendar);
        btnAddToCalendar.setOnClickListener(v -> handleAddToCalendar());

        Log.d("MealDetailsActivity", "MealId: " + meal.getIdMeal());

        presenter = new MealDetailsPresenter(this, getApplicationContext());

        fabFavorite = findViewById(R.id.fabFavorite);
        fabFavorite.setOnClickListener(v -> toggleFavorite());
        showMealDetails(meal);
    }
    private void handleAddToCalendar() {
        // Get current time as default
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            calendar.set(year, month, day);
            new TimePickerDialog(this, (timeView, hour, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                createCalendarEvent(calendar.getTimeInMillis());
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    public static String convertMillisToDateString(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int year = calendar.get(Calendar.YEAR) % 100; // Last two digits of the year
        int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format(Locale.getDefault(), "%02d%02d%02d", year, month, day);
    }
    private void createCalendarEvent(long startTime) {
       // List<Ingredient> ingredients = currentMeal.getIngredientsList();
      Log.i("test",  convertMillisToDateString(startTime));
        MealRepository a=new MealRepository(this);
        a.insertPlannedMeal(new PlannedMeals(currentMeal.getIdMeal(), convertMillisToDateString(startTime)));
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