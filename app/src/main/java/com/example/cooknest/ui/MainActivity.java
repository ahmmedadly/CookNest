package com.example.cooknest.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.cooknest.R;
import com.example.cooknest.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.cooknest.ui.search.SearchFragment;
import com.example.cooknest.ui.favorite.FavoritesFragment;
import com.example.cooknest.ui.calender.PlannerFragment;
import com.example.cooknest.ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    AlertDialog.Builder  builder;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_search) {
                if(mAuth.getCurrentUser() != null)
                    selectedFragment = new SearchFragment();
                else
                {
                    signupForMore();
                }
            } else if (itemId == R.id.nav_favorites) {
                if(mAuth.getCurrentUser() != null)
                selectedFragment = new FavoritesFragment();
                else
                {
                    signupForMore();
                }
            } else if (itemId == R.id.nav_calendar) {
                if(mAuth.getCurrentUser() != null)
                selectedFragment = new PlannerFragment();
else
                {
                    signupForMore();
                }
            } else if (itemId == R.id.nav_profile) {
                if(mAuth.getCurrentUser() != null)
                selectedFragment = new ProfileFragment();
                else
                {
                    signupForMore();
                }

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

        builder = new AlertDialog.Builder(this);
    }
    public void signupForMore() {
        builder.setTitle("Sign up for more features!")
                .setMessage("Save your favorite dishes \n and plan your meals")
                .setCancelable(true)
                .setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


}