package com.example.truyen.models;

public class SearchHistory {
    String sSearch;

    public SearchHistory(String sSearch) {
        this.sSearch = sSearch;
    }

    public SearchHistory() {
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }
}
