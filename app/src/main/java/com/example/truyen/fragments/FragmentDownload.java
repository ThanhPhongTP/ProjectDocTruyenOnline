package com.example.truyen.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.DownloadAdapter;
import com.example.truyen.adapter.FollowAdapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.DatabaseSQLite;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.truyen.service.DatabaseSQLite.database;

public class FragmentDownload extends Fragment {

    private RecyclerView rcvDownload;
    private DownloadAdapter adapter;
    private TextView tv_Download;
    private List<Story> storyList;
    private SharedPreferences_Utils sharedPreferencesUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        setControl(view);
        createData();
        return view;
    }

    @Override
    public void onResume() {
//        createData();
        super.onResume();
    }

    private void createData() {
        adapter = new DownloadAdapter(getActivity(), storyList);
        rcvDownload.setAdapter(adapter);
        database = new DatabaseSQLite(getActivity(), "Story.sqlite", null, 1);
        database.QueryData(
                "CREATE TABLE IF NOT EXISTS Stories(Id INTEGER PRIMARY KEY AUTOINCREMENT, story_id INTEGER, story_title VARCHAR(200)," +
                " author VARCHAR(200), rating DOUBLE, rating_count DOUBLE, description LONGTEXT, thumbnail_image VARCHAR(1000))");

        Cursor cursor = database.GetData("SELECT * FROM Stories");
        while (cursor.moveToNext()) {
            storyList.add(new Story(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7)
            ));
        }
        adapter.notifyDataSetChanged();
        rcvDownload.setLayoutManager(new LinearLayoutManager(getActivity()));
        //ẩn text "Bạn chưa có truyện tải về"
        if (storyList.size() > 0)
            tv_Download.setVisibility(View.GONE);
        else
            tv_Download.setVisibility(View.VISIBLE);
    }

    private void setControl(View view) {
        storyList = new ArrayList<>();
        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
        tv_Download = view.findViewById(R.id.tv_Download);
        rcvDownload = view.findViewById(R.id.rcv_download);

    }
}