package com.example.truyen.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.adapter.ChapterListOfflineAdapter;

import com.example.truyen.models.Chapter;
import com.example.truyen.service.DatabaseSQLite;

import java.util.ArrayList;
import java.util.List;

import static com.example.truyen.activities.StoryDetailActivity.nID;
import static com.example.truyen.activities.StoryOffLine.nID_OFFLINE;
import static com.example.truyen.service.DatabaseSQLite.database;

public class FragmentListChapterOffline extends Fragment {
    private RecyclerView recyclerView;
    private List<Chapter> chapterList;
    private LinearLayoutManager linearLayoutManager;
    private ChapterListOfflineAdapter adapter;
    private NestedScrollView nestedScrollViewChapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        checkTheme();
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        setControl(view);
        ShowData();
        return view;
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            getActivity().setTheme(nID);
        }
    }

//    private void getDataIntent() {
//        Intent intent = getActivity().getIntent();
//        if (intent != null) {
//            nID = intent.getIntExtra("NEWSTORY", 0);
//        }
//    }

    private void ShowData(){
//        getDataIntent();
        adapter = new ChapterListOfflineAdapter(getContext(), chapterList);
        recyclerView.setAdapter(adapter);
        database = new DatabaseSQLite(getActivity(), "Story.sqlite", null, 1);
//        database.QueryData(
//                "CREATE TABLE IF NOT EXISTS Chapter(Id INTEGER PRIMARY KEY AUTOINCREMENT, chapter_id INTEGER, " +
//                        "story_id INTEGER, title VARCHAR(200), created_at VARCHAR(100), contents LONGTEXT)");

        Cursor cursor = database.GetData("SELECT * FROM Chapter WHERE story_id = " + nID_OFFLINE);
        while (cursor.moveToNext()) {
            chapterList.add(new Chapter(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setControl(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_ChapterList);
        nestedScrollViewChapter = view.findViewById(R.id.nestedScrollViewChapter);
        chapterList = new ArrayList<>();
    }

}
