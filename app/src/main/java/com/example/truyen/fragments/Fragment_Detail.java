package com.example.truyen.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.ChapterWatchedAdapter;
import com.example.truyen.models.Chapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.APIService;
import com.example.truyen.service.DataService;
import com.jgabrielfreitas.core.BlurImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Detail extends Fragment {
    private Story story;
    private TextView txtContentStory;
    private Button btnListChapters;
    private RecyclerView recyclerView_ChapterWatched;
    private ArrayList<Chapter> chapterList;
    private SharedPreferences_Utils sharedPreferences_utils;
    private ChapterWatchedAdapter chapterWatchedAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Story> storyArrayList;
    private List<Chapter> chapterListFromSharePre;
    BlurImageView blurImageView;
    int nID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setControl(view);
        getIntentFormListStory();
        showDetailfromID();
        return view;
    }

    private void setControl(View view) {
        txtContentStory = view.findViewById(R.id.txtContentStory);
        sharedPreferences_utils = new SharedPreferences_Utils(getContext());
//        recyclerView_ChapterWatched = view.findViewById(R.id.recyclerView_ChapterWatched);
    }


    @Override
    public void onResume() {
//        getListChapterListFromSharePre();
        super.onResume();
    }

//    private void getListChapterListFromSharePre() {
//        chapterListFromSharePre = sharedPreferences_utils.getDataSharePreferences_From_ChapterWatched(chapterList, nID);
//        if (chapterListFromSharePre != null) {
//            chapterWatchedAdapter = new ChapterWatchedAdapter(getContext(), chapterListFromSharePre);
//            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//            recyclerView_ChapterWatched.setLayoutManager(linearLayoutManager);
//            recyclerView_ChapterWatched.setHasFixedSize(true);
//            recyclerView_ChapterWatched.setAdapter(chapterWatchedAdapter);
//            chapterWatchedAdapter.notifyDataSetChanged();
//        }
//    }


    private void getIntentFormListStory() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            nID = intent.getIntExtra("NEWSTORY", 0);
            Log.d("NEWSTORY", nID + "");
        }
    }

    private void showDetailfromID() {
        getIntentFormListStory();
        DataService dataService = APIService.getService();
//        String uttt = "categories/"+categories.getId()+"/stories?page="+page";
        dataService.getStory("stories/" + nID + "/detail").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//                        JSONObject fatherJSON = new JSONObject(response.body());
                        String description = (jsonObject.getString("description"));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtContentStory.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtContentStory.setText(Html.fromHtml(description));
                        }
//                        blurImageView.setBlur(3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}