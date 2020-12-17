package com.example.projectdoctruyenonline.service;

import com.example.projectdoctruyenonline.models.Categories;
import com.example.projectdoctruyenonline.models.Chapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DataService {


    //Get Thể loại
    @GET("categories/")
    Call<List<Categories>> getCategories();


    //get truyện từ thể loại
    @GET("categories/{category_id}/stories?page=")
    Call<String> getStoryByCategories(@Path("category_id")int id,@Query("page")int page);

//truyện hay
    @GET("rates/")
    Call<String> getNewStory();
//    @GET("rates/1")
//    Call<String> getStory();

    //detail
//    @GET("rates/{story_id}/stories?")
//    Call<String> getStory(@Path("story_id")int nID);

    @GET
    Call<String> getStory(@Url String url);

    //Chương truyện
    @GET("stories/{story_id}/chapters?page=")
    Call<List<Chapter>> getChapterList(@Path("story_id")int id, @Query("page")int page);
//    @GET
//    Call<List<Chapter>> getChapterList(@Url String url);


}
