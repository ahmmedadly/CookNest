package com.example.cooknest.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey
    private int idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryDescription;

    // Getters and setters
    public int getIdCategory() { return idCategory; }
    public void setIdCategory(int idCategory) { this.idCategory = idCategory; }
    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }
    public String getStrCategoryThumb() { return strCategoryThumb; }
    public void setStrCategoryThumb(String strCategoryThumb) { this.strCategoryThumb = strCategoryThumb; }
    public String getStrCategoryDescription() { return strCategoryDescription; }
    public void setStrCategoryDescription(String strCategoryDescription) { this.strCategoryDescription = strCategoryDescription; }
}