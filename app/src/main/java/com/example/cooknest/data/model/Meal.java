package com.example.cooknest.data.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "favorite_meals")
public class Meal implements Serializable {
        @PrimaryKey
        @NonNull
        private int idMeal;
        private String strMeal;
        private String strCategory;
        private String strArea;
        private String strInstructions;
        private String strMealThumb;
        private String strYoutube;
        private boolean isFavorite;
        private boolean isPlanned;
        public String ingredients;
        private String measures;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;
        private String strIngredient5;
        private String strIngredient6;
        private String strIngredient7;
        private String strIngredient8;
        private String strIngredient9;
        private String strIngredient10;
        private String strIngredient11;
        private String strIngredient12;
        private String strIngredient13;
        private String strIngredient14;
        private String strIngredient15;
        private String strIngredient16;
        private String strIngredient17;
        private String strIngredient18;
        private String strIngredient19;
        private String strIngredient20;
        private String strMeasure1;
        private String strMeasure2;
        private String strMeasure3;
        private String strMeasure4;
        private String strMeasure5;
        private String strMeasure6;
        private String strMeasure7;
        private String strMeasure8;
        private String strMeasure9;
        private String strMeasure10;
        private String strMeasure11;
        private String strMeasure12;
        private String strMeasure13;
        private String strMeasure14;
        private String strMeasure15;
        private String strMeasure16;
        private String strMeasure17;
        private String strMeasure18;
        private String strMeasure19;
        private String strMeasure20;

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
        public String getStrIngredient1() { return strIngredient1; }
        public String getStrIngredient2() { return strIngredient2; }
        public String getStrIngredient3() { return strIngredient3; }
        public String getStrIngredient4() { return strIngredient4; }
        public String getStrIngredient5() { return strIngredient5; }
        public String getStrIngredient6() { return strIngredient6; }
        public String getStrIngredient7() { return strIngredient7; }
        public String getStrIngredient8() { return strIngredient8; }
        public String getStrIngredient9() { return strIngredient9; }
        public String getStrIngredient10() { return strIngredient10; }
        public String getStrIngredient11() { return strIngredient11; }
        public String getStrIngredient12() { return strIngredient12; }
        public String getStrIngredient13() { return strIngredient13; }
        public String getStrIngredient14() { return strIngredient14; }
        public String getStrIngredient15() { return strIngredient15; }
        public String getStrIngredient16() { return strIngredient16; }
        public String getStrIngredient17() { return strIngredient17; }
        public String getStrIngredient18() { return strIngredient18; }
        public String getStrIngredient19() { return strIngredient19; }
        public String getStrIngredient20() { return strIngredient20; }
        public String getStrMeasure1() { return strMeasure1; }
        public String getStrMeasure2() { return strMeasure2; }
        public String getStrMeasure3() { return strMeasure3; }
        public String getStrMeasure4() { return strMeasure4; }
        public String getStrMeasure5() { return strMeasure5; }
        public String getStrMeasure6() { return strMeasure6; }
        public String getStrMeasure7() { return strMeasure7; }
        public String getStrMeasure8() { return strMeasure8; }
        public String getStrMeasure9() { return strMeasure9; }
        public String getStrMeasure10() { return strMeasure10; }
        public String getStrMeasure11() { return strMeasure11; }
        public String getStrMeasure12() { return strMeasure12; }
        public String getStrMeasure13() { return strMeasure13; }
        public String getStrMeasure14() { return strMeasure14; }
        public String getStrMeasure15() { return strMeasure15; }
        public String getStrMeasure16() { return strMeasure16; }
        public String getStrMeasure17() { return strMeasure17; }
        public String getStrMeasure18() { return strMeasure18; }
        public String getStrMeasure19() { return strMeasure19; }
        public String getStrMeasure20() { return strMeasure20; }

// Setters for other fields
public void setStrIngredient1(String strIngredient1) { this.strIngredient1 = strIngredient1; }
        public void setStrIngredient2(String strIngredient2) { this.strIngredient2 = strIngredient2; }
        public void setStrIngredient3(String strIngredient3) { this.strIngredient3 = strIngredient3; }
        public void setStrIngredient4(String strIngredient4) { this.strIngredient4 = strIngredient4; }
        public void setStrIngredient5(String strIngredient5) { this.strIngredient5 = strIngredient5; }
        public void setStrIngredient6(String strIngredient6) { this.strIngredient6 = strIngredient6; }
        public void setStrIngredient7(String strIngredient7) { this.strIngredient7 = strIngredient7; }
        public void setStrIngredient8(String strIngredient8) { this.strIngredient8 = strIngredient8; }
        public void setStrIngredient9(String strIngredient9) { this.strIngredient9 = strIngredient9; }
        public void setStrIngredient10(String strIngredient10) { this.strIngredient10 = strIngredient10; }
        public void setStrIngredient11(String strIngredient11) { this.strIngredient11 = strIngredient11; }
        public void setStrIngredient12(String strIngredient12) { this.strIngredient12 = strIngredient12; }
        public void setStrIngredient13(String strIngredient13) { this.strIngredient13 = strIngredient13; }
        public void setStrIngredient14(String strIngredient14) { this.strIngredient14 = strIngredient14; }
        public void setStrIngredient15(String strIngredient15) { this.strIngredient15 = strIngredient15; }
        public void setStrIngredient16(String strIngredient16) { this.strIngredient16 = strIngredient16; }
        public void setStrIngredient17(String strIngredient17) { this.strIngredient17 = strIngredient17; }
        public void setStrIngredient18(String strIngredient18) { this.strIngredient18 = strIngredient18; }
        public void setStrIngredient19(String strIngredient19) { this.strIngredient19 = strIngredient19; }
        public void setStrIngredient20(String strIngredient20) { this.strIngredient20 = strIngredient20; }

        public void setStrMeasure1(String strMeasure1) { this.strMeasure1 = strMeasure1; }
        public void setStrMeasure2(String strMeasure2) { this.strMeasure2 = strMeasure2; }
        public void setStrMeasure3(String strMeasure3) { this.strMeasure3 = strMeasure3; }
        public void setStrMeasure4(String strMeasure4) { this.strMeasure4 = strMeasure4; }
        public void setStrMeasure5(String strMeasure5) { this.strMeasure5 = strMeasure5; }
        public void setStrMeasure6(String strMeasure6) { this.strMeasure6 = strMeasure6; }
        public void setStrMeasure7(String strMeasure7) { this.strMeasure7 = strMeasure7; }
        public void setStrMeasure8(String strMeasure8) { this.strMeasure8 = strMeasure8; }
        public void setStrMeasure9(String strMeasure9) { this.strMeasure9 = strMeasure9; }
        public void setStrMeasure10(String strMeasure10) { this.strMeasure10 = strMeasure10; }
        public void setStrMeasure11(String strMeasure11) { this.strMeasure11 = strMeasure11; }
        public void setStrMeasure12(String strMeasure12) { this.strMeasure12 = strMeasure12; }
        public void setStrMeasure13(String strMeasure13) { this.strMeasure13 = strMeasure13; }
        public void setStrMeasure14(String strMeasure14) { this.strMeasure14 = strMeasure14; }
        public void setStrMeasure15(String strMeasure15) { this.strMeasure15 = strMeasure15; }
        public void setStrMeasure16(String strMeasure16) { this.strMeasure16 = strMeasure16; }
        public void setStrMeasure17(String strMeasure17) { this.strMeasure17 = strMeasure17; }
        public void setStrMeasure18(String strMeasure18) { this.strMeasure18 = strMeasure18; }
        public void setStrMeasure19(String strMeasure19) { this.strMeasure19 = strMeasure19; }
        public void setStrMeasure20(String strMeasure20) { this.strMeasure20 = strMeasure20; }


        // Helper methods
        public String getIngredient(int index) {
                switch (index) {
                        case 1: return getStrIngredient1();
                        case 2: return getStrIngredient2();
                        case 3: return getStrIngredient3();
                        case 4: return getStrIngredient4();
                        case 5: return getStrIngredient5();
                        case 6: return getStrIngredient6();
                        case 7: return getStrIngredient7();
                        case 8: return getStrIngredient8();
                        case 9: return getStrIngredient9();
                        case 10: return getStrIngredient10();
                        case 11: return getStrIngredient11();
                        case 12: return getStrIngredient12();
                        case 13: return getStrIngredient13();
                        case 14: return getStrIngredient14();
                        case 15: return getStrIngredient15();
                        case 16: return getStrIngredient16();
                        case 17: return getStrIngredient17();
                        case 18: return getStrIngredient18();
                        case 19: return getStrIngredient19();
                        case 20: return getStrIngredient20();
                        default: return null;
                }
        }

        public String getMeasure(int index) {
                switch (index) {
                        case 1: return getStrMeasure1();
                        case 2: return getStrMeasure2();
                        case 3: return getStrMeasure3();
                        case 4: return getStrMeasure4();
                        case 5: return getStrMeasure5();
                        case 6: return getStrMeasure6();
                        case 7: return getStrMeasure7();
                        case 8: return getStrMeasure8();
                        case 9: return getStrMeasure9();
                        case 10: return getStrMeasure10();
                        case 11: return getStrMeasure11();
                        case 12: return getStrMeasure12();
                        case 13: return getStrMeasure13();
                        case 14: return getStrMeasure14();
                        case 15: return getStrMeasure15();
                        case 16: return getStrMeasure16();
                        case 17: return getStrMeasure17();
                        case 18: return getStrMeasure18();
                        case 19: return getStrMeasure19();
                        case 20: return getStrMeasure20();
                        default: return null;
                }
        }
        public List<Ingredient> getIngredientsList() {
                List<Ingredient> list = new ArrayList<>();

                List<String> ingredientsList = Arrays.asList(ingredients.split("\\|"));
                List<String> measuresList = Arrays.asList(measures.split("\\|"));

                for (int i = 0; i < ingredientsList.size(); i++) {
                        String ingredient = ingredientsList.get(i);
                        String measure = (i < measuresList.size()) ? measuresList.get(i) : "";

                        if (!ingredient.isEmpty()) {
                                Ingredient ing = new Ingredient();
                                ing.setStrIngredient(ingredient.trim());
                                ing.setStrMeasure(measure.trim());
                                list.add(ing);
                        }
                }
                return list;
        }

        // Existing methods
        public void setIngredients(List<String> ingredients) {
                this.ingredients = TextUtils.join("|", ingredients);
        }

        public List<String> getIngredients() {
                return Arrays.asList(ingredients.split("\\|"));
        }

    public void setMeasures(String measures) {
        this.measures = measures;
    }
    public String getMeasures() {
        return measures;
    }

}
