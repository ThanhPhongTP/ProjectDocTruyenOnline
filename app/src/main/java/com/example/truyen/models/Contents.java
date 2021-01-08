package com.example.truyen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contents implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("chapter_id")
    @Expose
    private int chapter_id;


    @SerializedName("content")
    @Expose
    private String  content;

    public Contents(int id, int chapter_id, String content) {
        this.id = id;
        this.chapter_id = chapter_id;
        this.content = content;
    }
    public Contents( int chapter_id) {
        this.chapter_id = chapter_id;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
