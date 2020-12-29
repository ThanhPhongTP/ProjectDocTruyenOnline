package com.example.projectdoctruyenonline.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chapter implements Serializable {
    @SerializedName("story_id")
    @Expose
    public int idStory;
    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("contents")
    @Expose
    public String contents;


    @SerializedName("chapter_id")
    @Expose
    public int chapter_id;

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
