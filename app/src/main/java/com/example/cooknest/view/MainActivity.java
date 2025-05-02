package com.example.cooknest.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.cooknest.R;
import com.example.cooknest.view.Fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.cooknest.view.Fragments.SearchFragment;
import com.example.cooknest.view.Fragments.FavoritesFragment;
import com.example.cooknest.view.Fragments.PlannerFragment;
import com.example.cooknest.view.Fragments.ProfileFragment;
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            } else if (itemId == R.id.nav_calendar) {
                selectedFragment = new PlannerFragment();

            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();

            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }
}