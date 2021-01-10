package com.example.truyen.models;

import java.io.Serializable;

public class Story implements Serializable {
    int id, storyID, ratting, ratting_count;
    int categoriesId;
    String name, author, date, thumbnail_image, description;
    int totalChapter;
    int viewType;



    public Story(int id, int storyID, String name, String author, int ratting, int ratting_count, String description, String thumbnail_image) {
        this.id = id;
        this.storyID = storyID;
        this.ratting = ratting;
        this.ratting_count = ratting_count;
        this.name = name;
        this.author = author;
        this.thumbnail_image = thumbnail_image;
        this.description = description;
    }

    public Story(int id, String name, int totalChapter, String author, String date, String thumbnail_image, int viewType) {
        this.id = id;
        this.name = name;
        this.totalChapter = totalChapter;
        this.author = author;
        this.date = date;
        this.thumbnail_image = thumbnail_image;
        this.viewType = viewType;
    }

    public Story(int id, int categoriesId, String name) {
        this.id = id;
        this.categoriesId = categoriesId;
        this.name = name;

    }

    public Story(int id, String name, String thumbnail_image) {
        this.id = id;
        this.name = name;
        this.thumbnail_image = thumbnail_image;
    }

    public Story() {

    }


    public int getStoryID() {
        return storyID;
    }

    public void setStoryID(int storyID) {
        this.storyID = storyID;
    }

    public int getRatting() {
        return ratting;
    }

    public void setRatting(int ratting) {
        this.ratting = ratting;
    }

    public int getRatting_count() {
        return ratting_count;
    }

    public void setRatting_count(int ratting_count) {
        this.ratting_count = ratting_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
