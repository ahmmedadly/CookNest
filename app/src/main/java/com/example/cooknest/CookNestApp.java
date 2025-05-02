package com.example.cooknest;

import android.app.Application;

import com.example.cooknest.data.db.MealsDatabase;


public class CookNestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MealsDatabase.getInstance(this);

    }
}
