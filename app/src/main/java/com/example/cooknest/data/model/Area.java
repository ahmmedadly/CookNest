package com.example.cooknest.data.model;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("strArea")
    private String strArea;
    @SerializedName("strAreaThumb")
    private String strAreaThumb;
    @SerializedName("strAreaDescription")
    private String strAreaDescription;

    public String getStrAreaThumb() {
        return strAreaThumb;
    }
    public String getStrAreaDescription() {
        return strAreaDescription;
    }
    public String getStrArea() {
        return strArea;
    }
}
