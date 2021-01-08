package com.example.truyen.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.R;
import com.example.truyen.adapter.ChapterListAdapter;
import com.example.truyen.adapter.ChapterListBottomSheetAdapter;
import com.example.truyen.models.Chapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.APIService;
import com.example.truyen.service.DataService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.truyen.activities.StoryDetailActivity.nID;

public class FragmentBottomSheet extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private int storyId = 0;
    private String title = "";
    private String url = "";
    private List<Chapter> chapterList;
    private LinearLayoutManager linearLayoutManager;
    private ChapterListBottomSheetAdapter adapter;
    private Story story;
    private int totalItemCount, firstVisibleItemCount, visibleItemCount, previousTotal;
    private boolean isLoadDing = true;
    private int page = 1;
    private Toolbar toolbar_ChapterList;
    private NestedScrollView nestedScrollViewChapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkTheme();
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        setControl(view);
        getAPI();

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
                        adapter = new ChapterListBottomSheetAdapter(getContext(), chapterList);
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

    private void setControl(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_ChapterList);
        nestedScrollViewChapter = view.findViewById(R.id.nestedScrollViewChapter);
        chapterList = new ArrayList<>();
    }
}
