package com.example.truyen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categories implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("total story")
    @Expose
    int total;

    int img_Categories;

    public Categories(Integer id, String name, int total, int img_Categories) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.img_Categories = img_Categories;
    }

    public Categories() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getImg_Categories() {
        return img_Categories;
    }

    public void setImg_Categories(int img_Categories) {
        this.img_Categories = img_Categories;
    }
}