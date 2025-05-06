package com.example.cooknest.data.network;

import com.example.cooknest.data.model.AreaResponse;
import com.example.cooknest.data.model.CategoryResponse;
import com.example.cooknest.data.model.IngredientResponse;
import com.example.cooknest.data.model.MealResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") int mealId);
    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String query);
     @GET("categories.php")
     Call<CategoryResponse> getCategories();

     @GET("list.php?a=list")
     Call<AreaResponse> getAreas();

     @GET("list.php?i=list")
     Call<IngredientResponse> getIngredients();

}