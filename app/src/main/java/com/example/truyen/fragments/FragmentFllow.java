package com.example.truyen.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.FollowAdapter;
import com.example.truyen.adapter.HomeStoriesAdapter;
import com.example.truyen.models.Ratting;
import com.example.truyen.models.Story;
import com.example.truyen.service.APIService;
import com.example.truyen.service.DataService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFllow extends Fragment {
    private RecyclerView recyclerView, rcvYeuThich;
    private HomeStoriesAdapter homeAdapter;
    private List<Ratting> listYeuThich;
    private FollowAdapter adapter;
    private TextView txtNodataHistory;
    private List<Story> storyList;
    private SharedPreferences_Utils sharedPreferencesUtils;
    private int story_id;
    private String story_title;
    private String author;
    private Double rating;
    private int rating_count;
    private String description;
    private String thumbnail_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fllow, container, false);
        setControl(view);
        buildRecyclerview();
        showRCVYeuThich();
        return view;
    }

    @Override
    public void onResume() {
        buildRecyclerview();
        super.onResume();
    }

    private void buildRecyclerview() {
        storyList =  sharedPreferencesUtils.get_SharedPreferences_Story_FollowFragment();
        if (storyList.size()>0) {
            adapter = new FollowAdapter(getActivity(), storyList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            txtNodataHistory.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            txtNodataHistory.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void showRCVYeuThich(){
        listYeuThich = new ArrayList<>();
        homeAdapter = new HomeStoriesAdapter(getContext(), listYeuThich);
        rcvYeuThich.setAdapter(homeAdapter);

        DataService dataService = APIService.getService();
        dataService.getHotStory().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject fatherJSON = new JSONObject(response.body().toString());
                        JSONArray arrayData = fatherJSON.getJSONArray("data");
                        for (int i = 0; i < arrayData.length(); i++) {
                            JSONObject item = arrayData.getJSONObject(i);
                            story_id = item.getInt("story_id");
                            story_title = item.getString("story_title");
                            author = item.getString("author");
                            rating = item.getDouble("rating");
                            rating_count = item.getInt("rating_count");
                            description = item.getString("description");
                            thumbnail_image = item.getString("thumbnail_image");
                            listYeuThich.add(new Ratting(story_id, story_title, author, rating, rating_count, description, thumbnail_image));
                            homeAdapter.notifyDataSetChanged();
                            rcvYeuThich.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }
    
    private void setControl(View view) {
        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
        txtNodataHistory = view.findViewById(R.id.txtFL);
        recyclerView = view.findViewById(R.id.rcv_follow);
        rcvYeuThich = view.findViewById(R.id.rcv_yeuthich);
    }
}