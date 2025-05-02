package com.example.cooknest.data.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "favorite_meals")
public class Meal {
        @PrimaryKey
        private int idMeal;
        private String strMeal;
        private String strCategory;
        private String strArea;
        private String strInstructions;
        private String strMealThumb;
        private String strYoutube;
        private boolean isFavorite;
        private boolean isPlanned;

        // Getters and setters
        public int getIdMeal() { return idMeal; }
        public void setIdMeal(int idMeal) { this.idMeal = idMeal; }
        public String getStrMeal() { return strMeal; }
        public void setStrMeal(String strMeal) { this.strMeal = strMeal; }
        public String getStrCategory() { return strCategory; }
        public void setStrCategory(String strCategory) { this.strCategory = strCategory; }
        public String getStrArea() { return strArea; }
        public void setStrArea(String strArea) { this.strArea = strArea; }
        public String getStrInstructions() { return strInstructions; }
        public void setStrInstructions(String strInstructions) { this.strInstructions = strInstructions; }
        public String getStrMealThumb() { return strMealThumb; }
        public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }
        public String getStrYoutube() { return strYoutube; }
        public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }
        public boolean isFavorite() { return isFavorite; }
        public void setFavorite(boolean favorite) { isFavorite = favorite; }
        public boolean isPlanned() { return isPlanned; }
        public void setPlanned(boolean planned) { isPlanned = planned; }
}