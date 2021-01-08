package com.example.truyen.models;

public class Ratting {
    int story_id;
    String story_title;
    String author;
    Double rating;
    int rating_count;
    String  description;
    String thumbnail_image;

    public Ratting(int story_id, String story_title, String author, Double rating, int rating_count, String description,  String thumbnail_image) {
        this.story_id = story_id;
        this.story_title = story_title;
        this.author = author;
        this.rating = rating;
        this.rating_count = rating_count;
        this.description = description;
        this.thumbnail_image = thumbnail_image;
    }

    public Ratting() {
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
