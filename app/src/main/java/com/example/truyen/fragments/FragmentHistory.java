package com.example.truyen.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.HistoryFragmentsAdapter;
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

public class FragmentHistory extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView, rcvDeXuat;
    private HomeStoriesAdapter homeAdapter;
    private List<Ratting> listDeXuat;
    private HistoryFragmentsAdapter adapter;
    private List<Story> storyList;
    private TextView txtNodataHistory;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        setControl(view);
        buildRecyclerview();
        showRcvDeXuat();
        return view;
    }

    @Override
    public void onResume() {
        buildRecyclerview();
        super.onResume();
    }

    private void buildRecyclerview() {
        storyList = sharedPreferencesUtils.get_SharedPreferences_Story_HistoryFragment();
        if (storyList.size() > 0) {
            adapter = new HistoryFragmentsAdapter(getActivity(), storyList);
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            txtNodataHistory.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            txtNodataHistory.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void showRcvDeXuat(){
        listDeXuat = new ArrayList<>();
        homeAdapter = new HomeStoriesAdapter(getContext(), listDeXuat);
        rcvDeXuat.setAdapter(homeAdapter);
        DataService dataService = APIService.getService();
        dataService.getNewStory().enqueue(new Callback<String>() {
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
                            listDeXuat.add(new Ratting(story_id, story_title, author, rating, rating_count, description, thumbnail_image));
                            homeAdapter.notifyDataSetChanged();
                            rcvDeXuat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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
        txtNodataHistory = view.findViewById(R.id.txtNodataHistory);
        rcvDeXuat = view.findViewById(R.id.rcv_dexuat);
        recyclerView = view.findViewById(R.id.recyclerView_Docganday);
    }

}