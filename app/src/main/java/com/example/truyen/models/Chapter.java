package com.example.truyen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chapter implements Serializable {
    int nID;
    @SerializedName("chapter_id")
    @Expose
    public int chapter_id;
    @SerializedName("story_id")
    @Expose
    public int idStory;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("created_at")
    @Expose
    public String created_at;
    @SerializedName("contents")
    @Expose
    public String contents;


    public Chapter(int nID, int chapter_id, int idStory, String title, String created_at, String contents) {
        this.nID = nID;
        this.chapter_id = chapter_id;
        this.idStory = idStory;
        this.title = title;
        this.created_at = created_at;
        this.contents = contents;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    //    @SerializedName("content")
//    @Expose
//    private String  content;
    public Chapter(int idStory, String title) {
        this.idStory = idStory;
        this.title = title;
    }

//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

    public Chapter(int idStory, String title, int chapter_id) {
        this.idStory = idStory;
        this.title = title;
        this.chapter_id = chapter_id;
    }
    public Chapter(int idStory, String title,int chapter_id, String contents) {
        this.idStory = idStory;
        this.title = title;
        this.chapter_id = chapter_id;
        this.contents = contents;
    }


    public Chapter() {
    }



    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getIdStory() {
        return idStory;
    }

    public void setIdStory(int idStory) {
        this.idStory = idStory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
