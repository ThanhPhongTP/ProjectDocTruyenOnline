package com.example.projectdoctruyenonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.projectdoctruyenonline.adapter.ViewPagerReadStoryAdapter;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.models.Story;
import com.example.projectdoctruyenonline.service.APIService;
import com.example.projectdoctruyenonline.service.DataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadStoryActivity extends AppCompatActivity {
    public Toolbar testToolBarId;
    public LinearLayout linearLayout_Visible;
    private Chapter chapter;
    //    private AdView adView;
    private SharedPreferences_Utils sharedPreferencesUtils;
    private ViewPager viewPager;
    private ViewPagerReadStoryAdapter viewPagerReadStoryAdapter;
    private List<Chapter> chapterArrayList;
    private Story story;
    private ArrayList<Story> storyArrayList;
    private String sContent;
    private int page = 1;
    int idChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        viewPager = findViewById(R.id.fr_container_test);
        sharedPreferencesUtils = new SharedPreferences_Utils(this);
        init();
        actionToolBar();
        dataIntent();
    }

    private void getAPI() {
        DataService dataService = APIService.getService();
        dataService.getChapterList(chapter.getIdStory()).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response != null) {

                    chapterArrayList = response.body();
                    viewPagerReadStoryAdapter = new ViewPagerReadStoryAdapter(getSupportFragmentManager(), chapterArrayList, chapter);
                    viewPager.setAdapter(viewPagerReadStoryAdapter);
                    int indexofchapter = -1;
                    for (int i = 0; i < chapterArrayList.size(); i++) {
                        if (chapterArrayList.get(i).getChapter_id() == chapter.getChapter_id()) {
                            indexofchapter = i;
                        }
                    }
                    viewPager.setCurrentItem(indexofchapter);
                    Log.d("INDEXOFCHATTER",indexofchapter+"");
                    viewPagerReadStoryAdapter.notifyDataSetChanged();


//                    int indexofchapter = -1;
//
//                    try {
//                        JSONArray jsonArray = new JSONArray(response.body().toString());
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            idChapter = object.getInt("chapter_id");
//                            sContent = object.getString("contents");
//
//                            Log.d("fdsasss1", sContent + "");
//
//                                indexofchapter = i;
//                        }
//                        viewPagerReadStoryAdapter = new ViewPagerReadStoryAdapter(getSupportFragmentManager(), (List<Chapter>) jsonArray, new Chapter());
//                        viewPager.setAdapter(viewPagerReadStoryAdapter);
//                        viewPager.setCurrentItem(indexofchapter);
//                        Log.d("INDEXOFCHATTER",indexofchapter+"");
//                        viewPagerReadStoryAdapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                }

            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
            }
        });
    }

    private void getAPIShare() {
        DataService dataService = APIService.getService();
//        dataService.getChapterList(chapter.getIdStory(),page).enqueue(new Callback<List<Chapter>>() {
//            @Override
//            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
//                chapterArrayList = response.body();
//                viewPagerReadStoryAdapter = new ViewPagerReadStoryAdapter(getSupportFragmentManager(),chapterArrayList,chapter);
//                viewPager.setAdapter(viewPagerReadStoryAdapter);
//                int indexofchapter  = -1;
//                for (int i = 0;i<chapterArrayList.size();i++){
//                    if (chapterArrayList.get(i).getContents().getChapter_id() == chapter.getContents().getChapter_id()) {
//                        indexofchapter = i;
//                    }
//                }
//                viewPager.setCurrentItem(indexofchapter);
//            }
//            @Override
//            public void onFailure(Call<List<Chapter>> call, Throwable t) {
//            }
//        });
    }


    private void dataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
//            idChapter = intent.getIntExtra(Commons.Chapter, 0);
            chapter = (Chapter) intent.getSerializableExtra(Commons.Chapter);
            if (Commons.isConnectedtoInternet(this)) {
                getAPI();
            } else {
                Commons.showDialogError(this);
            }
        }


    }

    private void actionToolBar() {
        testToolBarId = findViewById(R.id.testToolBarId);
        setSupportActionBar(testToolBarId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testToolBarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
//        adView = findViewById(R.id.adView);
        linearLayout_Visible = findViewById(R.id.linearLayout_Visible);
//        chapterArrayList = new ArrayList<>();
    }
}