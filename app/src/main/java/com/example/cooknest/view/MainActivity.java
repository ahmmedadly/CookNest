package com.example.cooknest.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cooknest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                showToast("Home Selected");
                return true;
            }
            else if (itemId == R.id.nav_search) {
                showToast("Search Selected");
                return true;
            }
            else if (itemId == R.id.nav_favorites) {
                showToast("Favorites Selected");
                return true;
            }
            else if (itemId == R.id.nav_calendar) {
                showToast("Calendar Selected");
                return true;
            }
            else if (itemId == R.id.nav_profile) {
                showToast("Profile Selected");
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //TODO : add fragments for each tab Ya king
}