package com.example.truyen.service;

public class APIService {
    private static final String BASE_URL = "https://story.banabatech.com/api/";
    public static DataService getService(){
        return APIRetrofitClient.getClient(BASE_URL).create(DataService.class);
    }
}
