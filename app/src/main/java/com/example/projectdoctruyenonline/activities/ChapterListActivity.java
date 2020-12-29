package com.example.projectdoctruyenonline.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.adapter.ChapterListAdapter;
import com.example.projectdoctruyenonline.models.Categories;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.models.Story;
import com.example.projectdoctruyenonline.service.APIService;
import com.example.projectdoctruyenonline.service.DataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChapterListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private int id = 0;
    private int storyId = 0;
    private String title ="";
    private String url = "";
    private List<Chapter> chapterList;
    private LinearLayoutManager linearLayoutManager;
    private ChapterListAdapter adapter;
    private Story story;

    private  int totalItemCount,firstVisibleItemCount,visibleItemCount,previousTotal;
    private boolean isLoadDing  = true;
    private int page = 1;
    private Toolbar toolbar_ChapterList;
    private NestedScrollView nestedScrollViewChapter;
    private int nID, nChapter_ID;
    private String sChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        recyclerView = findViewById(R.id.recyclerView_ChapterList);
        nestedScrollViewChapter = findViewById(R.id.nestedScrollViewChapter);
        toolbar_ChapterList = findViewById(R.id.toolbar_ChapterList);
        actionToolBar(toolbar_ChapterList);
        getIntentFromStory();
        if (Commons.isConnectedtoInternet(this)) {
            getAPI();
            loadMoreData();
        }
        else {
            Commons.showDialogError(this);
        }
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
    private void getIntentFromStory() {
        Intent intent = getIntent();
        if (intent != null) {
            nID = intent.getIntExtra(Commons.Chapter, 0);
//            Log.d("NEWSTORY", nID+"");
        }
    }
    private void loadMoreData() {
        nestedScrollViewChapter.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
//                    progressBar.setVisibility(View.VISIBLE);
                    getAPI();
                }
            }
        });
    }
    private void getAPI() {
        chapterList = new ArrayList<>();
        adapter = new ChapterListAdapter(this, chapterList);
        recyclerView.setAdapter(adapter);

        DataService dataService = APIService.getService();
//        stories/+"+story.getId()+"/chapters?page="+page
        dataService.getChapterList(nID).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                Log.d("fdsasss", ".");
                chapterList  = response.body();
                adapter = new ChapterListAdapter(ChapterListActivity.this,chapterList);
                linearLayoutManager = new LinearLayoutManager(ChapterListActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);


//                try {
//                    JSONArray jsonArray = new JSONArray(response.body().toString());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        nChapter_ID = object.getInt("story_id");
//                        sChapter = object.getString("title");
//                        chapterList.add(new Chapter(nChapter_ID, sChapter));
//                        adapter.notifyDataSetChanged();
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("ERRRRORRR",t.toString());
//            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Log.d("ydsf" , "sa");
            }
        });
    }

}