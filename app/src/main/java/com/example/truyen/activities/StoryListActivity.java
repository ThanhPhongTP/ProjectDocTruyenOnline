package com.example.truyen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.adapter.StoryAdapter;
import com.example.truyen.models.Categories;
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

public class StoryListActivity extends AppCompatActivity {
    private Categories categories;
    private int id = 0,totalChapters;
    private String name = "";
    private String author;
    private String url ="";
    private int categoriesID = 0;
    private List<Story> listStory;
    private StoryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private Toolbar toolbar_Story;
    private  int totalItemCount,firstVisibleItemCount,visibleItemCount,previousTotal;
    private boolean isLoadDing  = true;
    private int page = 1;
    private NestedScrollView nestedScrollViewStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme();
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_by_categories);
        recyclerView = findViewById(R.id.recyclerViewStoryByCategoriesId);
        toolbar_Story = findViewById(R.id.toolbar_Story);
        nestedScrollViewStory = findViewById(R.id.nestedScrollViewStory);
        actionToolBar(toolbar_Story);
        getIntentFormCategoriesDetail();
        listStory = new ArrayList<>();
        adapter = new StoryAdapter(this, listStory);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        if (Commons.isConnectedtoInternet(this)) {
            getAPI();
            loadMoreData();
        } else {
            Commons.showDialogError(this);
        }
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            setTheme(nID);
        }
    }

    private void actionToolBar(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void loadMoreData() {
        nestedScrollViewStory.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
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
        DataService dataService= APIService.getService();
//        int a = 1;
//        String uttt = "categories/"+categories.getId()+"/stories?page="+page";
        dataService.getStoryByCategories(categories.getId(),page).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("getStoryByCategoriessss",response.body().toString());
                if (response.isSuccessful()) {
                    try {
                        JSONObject fatherJSON = new JSONObject(response.body().toString());
//                        JSONObject jsonObject=fatherJSON.getJSONObject("content");
//                        jsonObject.getString("?");
                        Log.d("respone", response.body().toString());
                        JSONArray arrayData = fatherJSON.getJSONArray("data");
//                    String name = arrayData.getString(0);
                        for (int i = 0; i < arrayData.length(); i++) {
                            JSONObject item = arrayData.getJSONObject(i);
                            id = item.getInt("id");
//                            categoriesID = item.getInt("category_id");
                            name = item.getString("name");
                            author = item.getString("author");
                            totalChapters = item.getInt("total chapter");
                            String sDate = item.getString("created_date");
                            String sIMG = item.getString("image");
                            listStory.add(new Story(id,name,totalChapters,author, sDate, sIMG, 0));
//                        for (int j =0;j<listStoryByCategoriesId.size();j++){
//                            Log.d("listStoryByCategoriesId",listStoryByCategoriesId.get(i).getId()+"");
//                        }
                            adapter.notifyDataSetChanged();
                        }
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



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Story>newList = new ArrayList<>();
                for (Story story : listStory){
                    String name = story.getName().toLowerCase();
                    if (name.contains(newText)){
                        newList.add(story);
                    }
                }
                adapter = new StoryAdapter(StoryListActivity.this, (ArrayList<Story>) listStory);
                adapter.setFilter(newList);
                linearLayoutManager = new LinearLayoutManager(StoryListActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void getIntentFormCategoriesDetail() {
        Intent intent = getIntent();
        categories = (Categories) intent.getSerializableExtra(Commons.Categories);
        String tess = categories.getId()+"";
        Log.d("StoryByasdas",tess);
    }
}