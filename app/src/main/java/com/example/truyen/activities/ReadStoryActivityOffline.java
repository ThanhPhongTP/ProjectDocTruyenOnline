package com.example.truyen.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.ViewPagerReadStoryAdapter;
import com.example.truyen.adapter.ViewPagerReadStoryOfflineAdapter;
import com.example.truyen.models.Chapter;
import com.example.truyen.service.DatabaseSQLite;

import java.util.ArrayList;

import static com.example.truyen.service.DatabaseSQLite.database;

public class ReadStoryActivityOffline extends AppCompatActivity {
    public Toolbar testToolBarId;
    public LinearLayout linearLayout_Visible;
    private SharedPreferences_Utils sharedPreferencesUtils;
    private ViewPager viewPager;
    private ViewPagerReadStoryOfflineAdapter viewPagerReadStoryOfflineAdapter;
    int story_id, chapter_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme();
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        setControl();
        actionToolBar();
        getData();
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            setTheme(nID);
        }
    }

    private void getData(){
        int indexofchapter = -1;
        dataIntent();
        ArrayList<Chapter> listdata = new ArrayList<Chapter>();
        database = new DatabaseSQLite(getApplicationContext(),"Story.sqlite", null, 1);
        Cursor cursor = database.GetData("SELECT * FROM Chapter WHERE story_id = " + story_id);
        while (cursor.moveToNext()) {
            int nChapter_id = cursor.getInt(cursor.getColumnIndex("chapter_id") );
            int nStory_id = cursor.getInt(cursor.getColumnIndex("story_id") );
            String title = cursor.getString(cursor.getColumnIndex("title") );
            String contents = cursor.getString(cursor.getColumnIndex("contents") );
            Log.d("akjdalk", title);
            listdata.add(new Chapter(nStory_id, title, nChapter_id, contents));
            for (int i = 0; i < listdata.size(); i++){
                if (listdata.get(i).getChapter_id() == chapter_id) {
                    indexofchapter = i;
                }
            }
            viewPagerReadStoryOfflineAdapter = new ViewPagerReadStoryOfflineAdapter(getSupportFragmentManager(), listdata, new Chapter());
            viewPager.setAdapter(viewPagerReadStoryOfflineAdapter);
            viewPager.setCurrentItem(indexofchapter);
            Log.d("INDEXOFCHATTER",indexofchapter+"");
            viewPagerReadStoryOfflineAdapter.notifyDataSetChanged();
        }
    }

    private void dataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Commons.Chapter)) {
                story_id = intent.getIntExtra(Commons.Chapter, 0);
                chapter_id = intent.getIntExtra("CHAPTER_ID", 0);
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

    private void setControl() {
        viewPager = findViewById(R.id.fr_container_test);
        sharedPreferencesUtils = new SharedPreferences_Utils(this);
        linearLayout_Visible = findViewById(R.id.linearLayout_Visible);
    }
}
