package com.example.truyen.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Half;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.Commons;
import com.example.truyen.LoadingDialog;
import com.example.truyen.R;
import com.example.truyen.adapter.ChapterListAdapter;
import com.example.truyen.models.Chapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.APIService;
import com.example.truyen.service.DataService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.truyen.activities.StoryDetailActivity.nID;

public class FragmentListActivity extends Fragment {
    private RecyclerView recyclerView;
    private int id = 0;
    private int storyId = 0;
    private String title = "";
    private String url = "";
    private List<Chapter> chapterList;
    private LinearLayoutManager linearLayoutManager;
    private ChapterListAdapter adapter;
    private Story story;

    private int totalItemCount, firstVisibleItemCount, visibleItemCount, previousTotal;
    private boolean isLoadDing = true;
    private int page = 1;
    private Toolbar toolbar_ChapterList;
    private NestedScrollView nestedScrollViewChapter;
//    private int nID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkTheme();
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        setControl(view);
        getAPI();
//        showDialog();
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

    private void showDialog(){
        LoadingDialog loadingDialog = new LoadingDialog((Activity) getContext());
        if(chapterList.size() == 0)
            loadingDialog.startDialog();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(chapterList.size() > 0)
                    loadingDialog.dismissDialog();
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void setControl(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_ChapterList);
        nestedScrollViewChapter = view.findViewById(R.id.nestedScrollViewChapter);
        chapterList = new ArrayList<>();
    }

    private void loadMoreData() {
        nestedScrollViewChapter.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    getAPI();
                }
            }
        });
    }

    private void getAPI() {
        DataService dataService = APIService.getService();
        dataService.getChapterList(nID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int storyID = object.getInt("story_id");
                        int idChapter = object.getInt("chapter_id");
                        String sContent = object.getString("contents");
                        String sTitle = object.getString("title");

                        chapterList.add(new Chapter(storyID, sTitle, idChapter, sContent));
                        adapter = new ChapterListAdapter(getContext(), chapterList);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
//                    Log.d("klkl", chapterList.get(1).getTitle());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}