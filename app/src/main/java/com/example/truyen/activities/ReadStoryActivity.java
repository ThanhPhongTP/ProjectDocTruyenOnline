package com.example.truyen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.ViewPagerReadStoryAdapter;
import com.example.truyen.models.Chapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.APIService;
import com.example.truyen.service.DataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private int id = 0;
    private int storyId = 0;
    private String title ="";
    private String url = "";
    private int page = 1;
    int story_id, chapter_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme();
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        init();
        actionToolBar();
        dataIntent();
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            setTheme(nID);
        }
    }

    private void getAPI() {
        ArrayList<Chapter> listdata = new ArrayList<Chapter>();
        DataService dataService= APIService.getService();
        dataService.getChapterList(story_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int indexofchapter = -1;
                if (response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            int storyID = object.getInt("story_id");
                            String sTitle = object.getString("title");
                            int nID = object.getInt("chapter_id");
                            String sContent = object.getString("contents");

                            listdata.add(new Chapter(storyID, sTitle, nID, sContent));
//                            Log.d("kokoa",listdata.get(i).getTitle()+"");
                            if (listdata.get(i).getChapter_id() == chapter_id) {
                                indexofchapter = i;
                                //Lưu chương đã đọc
                                sharedPreferencesUtils.setDataSharePreferences_From_Chapter(new Chapter(listdata.get(i).getIdStory(),
                                        listdata.get(i).getTitle(), listdata.get(i).getChapter_id(), listdata.get(i).getContents()));
                            }
                        }
                        viewPagerReadStoryAdapter = new ViewPagerReadStoryAdapter(getSupportFragmentManager(), listdata, new Chapter());
                        viewPager.setAdapter(viewPagerReadStoryAdapter);
                        viewPager.setCurrentItem(indexofchapter);
                        Log.d("INDEXOFCHATTER",indexofchapter+"");
                        viewPagerReadStoryAdapter.notifyDataSetChanged();
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


    private void dataIntent() {
        Intent intent = getIntent();
        if (intent!= null) {
            if (intent.hasExtra(Commons.Chapter)) {
//                chapter = (Chapter) intent.getSerializableExtra(Commons.Chapter);
                story_id = intent.getIntExtra(Commons.Chapter, 0);
                chapter_id = intent.getIntExtra("CHAPTER_ID", 0);
                if (Commons.isConnectedtoInternet(this)) {
                    getAPI();
                }else {
                    Commons.showDialogError(this);
                }
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
        viewPager = findViewById(R.id.fr_container_test);
        sharedPreferencesUtils = new SharedPreferences_Utils(this);
        linearLayout_Visible = findViewById(R.id.linearLayout_Visible);
    }
}