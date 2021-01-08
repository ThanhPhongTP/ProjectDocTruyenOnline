package com.example.truyen.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.ChapterWatchedAdapter;
import com.example.truyen.adapter.FragmentAdapter;
import com.example.truyen.fragments.FragmentListActivity;
import com.example.truyen.fragments.FragmentListChapterOffline;
import com.example.truyen.fragments.Fragment_Detail;
import com.example.truyen.fragments.Fragment_Detail_offline;
import com.example.truyen.models.Chapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.DatabaseSQLite;
import com.google.android.material.tabs.TabLayout;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.truyen.service.DatabaseSQLite.database;

public class StoryOffLine extends AppCompatActivity {
    private Story story;
    private TextView txtNameStoryDetail, txtContentStory, txtCountCategoriesDetail, txtAuthorDetail;
    private ImageView imgStoryDetail, imgLike, imgBack, imgDownload;
    private LinearLayout linearLayout, linear_download;
    private SharedPreferences_Utils sharedPreferences_utils;
    private ChapterWatchedAdapter chapterWatchedAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Story> storyArrayList;
    private ArrayList<Chapter> chapterList;
    private List<Chapter> chapterListFromSharePre;
    public static int nID_OFFLINE;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    BlurImageView blurImageView;
    private Button btnRead;
    String story_title, sIMG, author, sLinkImg, sDescription;
    private int nLike, rating_count, ratting, storyID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        checkTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        setControl();
        setBack();
        addTabs();
        showData();
        setEvent();
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            setTheme(nID);
        }
    }

    private void setBack(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            nID_OFFLINE = intent.getIntExtra("NEWSTORY", 0);
        }
    }

    public void addTabs() {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.add(new Fragment_Detail_offline(), "Chi tiết");
        fragmentAdapter.add(new FragmentListChapterOffline(), "Danh sách chương");
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackground(null);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void showData(){
        getDataIntent();
        database = new DatabaseSQLite(getApplicationContext(),"Story.sqlite", null, 1);
        Cursor cursor = database.GetData("SELECT * FROM Stories WHERE story_id = " + nID_OFFLINE);
        while (cursor.moveToNext()) {
            story_title = cursor.getString(cursor.getColumnIndex("story_title") );
            author = cursor.getString(cursor.getColumnIndex("author") );
            sLinkImg = cursor.getString(cursor.getColumnIndex("thumbnail_image") );
            txtNameStoryDetail.setText(story_title);
            txtAuthorDetail.setText("Tác giả: " + author);
            Picasso.with(getApplication())
                    .load(sLinkImg)
                    .into(imgStoryDetail);
            Picasso.with(getApplication())
                    .load(sLinkImg)
                    .into(blurImageView);
            blurImageView.setBlur(15);
        }

        String sNameColumn[] = cursor.getColumnNames();

        cursor.close();
    }

    private void setEvent() {
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    private void setControl() {
        txtCountCategoriesDetail = findViewById(R.id.txtCountCategoriesDetail);
        txtAuthorDetail = findViewById(R.id.txtAuthorDetail);
        txtContentStory = findViewById(R.id.txtContentStory);
        sharedPreferences_utils = new SharedPreferences_Utils(this);
        imgLike = findViewById(R.id.imglike);
        linearLayout = findViewById(R.id.linear_like);
        btnRead = findViewById(R.id.btnChapterRedStoryLimit1);
        imgStoryDetail = findViewById(R.id.imgStoryDetail);
        blurImageView = findViewById(R.id.imgBackground);
        imgBack = findViewById(R.id.imgback);
        imgDownload = findViewById(R.id.download);
        viewPager = findViewById(R.id.viewDetail);
        tabLayout = findViewById(R.id.tabDetail);
        txtNameStoryDetail = findViewById(R.id.txtNameStoryDetail);
        imgDownload.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        linear_download = findViewById(R.id.linear_download);
        linear_download.setVisibility(View.GONE);
    }
}
