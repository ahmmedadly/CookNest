package com.example.cooknest.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "meals")
public class Meal {
        @PrimaryKey
        @NonNull
        private String idMeal;

        private String strMeal;
        private String strCategory;
        private String strArea;
        private String strInstructions;
        private String strMealThumb;

        private boolean isFavorite;


}
