package com.example.projectdoctruyenonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.projectdoctruyenonline.adapter.StoryWatchedAdapter;
import com.example.projectdoctruyenonline.models.Story;

import java.util.List;

public class StoryWatchedActivity extends AppCompatActivity {
    private Toolbar toolbar_StoryWatched;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StoryWatchedAdapter storyAdapter;
    private List<Story> storyList;
    private TextView txtNodataStoryWatched;
    private SharedPreferences_Utils sharedPreferencesUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_watched);

        sharedPreferencesUtils = new SharedPreferences_Utils(this);
        storyList =  sharedPreferencesUtils.get_SharedPreferences_Story_HistoryFragment();
        init();
        actionToolBar(toolbar_StoryWatched);
        buildRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildRecyclerView();

    }

    private void buildRecyclerView() {
        if (storyList.size()>0) {
            storyAdapter = new StoryWatchedAdapter(this, storyList);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(storyAdapter);
            storyAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            txtNodataStoryWatched.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            txtNodataStoryWatched.setVisibility(View.VISIBLE);
        }
    }
    private void init() {
        txtNodataStoryWatched = findViewById(R.id.txtNodataStoryWatched);
        toolbar_StoryWatched = findViewById(R.id.toolbar_StoryWatched);
        recyclerView  = findViewById(R.id.recyclerView_Watched);
    }
    private void actionToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}