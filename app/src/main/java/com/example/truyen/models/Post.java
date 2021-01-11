package com.example.truyen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("query")
    @Expose
    private String query;

    public Post(String query) {
        this.query = query;
    }
}
