package com.example.projectdoctruyenonline.models;

import java.io.Serializable;

public class Story implements Serializable {
    int id;
    int categoriesId;
    String name, author, date;
    int totalChapter;


    public Story(int id, String name, int totalChapter, String author, String date) {
        this.id = id;
        this.name = name;
        this.totalChapter = totalChapter;
        this.author = author;
        this.date = date;
    }

    public Story(int id, int categoriesId, String name) {
        this.id = id;
        this.categoriesId = categoriesId;
        this.name = name;

    }

    public Story(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Story() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalChapter() {
        return totalChapter;
    }

    public void setTotalChapter(int totalChapter) {
        this.totalChapter = totalChapter;
    }
}
