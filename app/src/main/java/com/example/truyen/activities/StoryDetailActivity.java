package com.example.truyen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.ChapterListAdapter;
import com.example.truyen.adapter.ChapterWatchedAdapter;
import com.example.truyen.adapter.FragmentAdapter;
import com.example.truyen.fragments.FragmentDownload;
import com.example.truyen.fragments.FragmentListActivity;
import com.example.truyen.fragments.Fragment_Detail;
import com.example.truyen.models.Chapter;
import com.example.truyen.models.Story;
import com.example.truyen.service.APIService;
import com.example.truyen.service.DataService;
import com.example.truyen.service.DatabaseSQLite;
import com.google.android.material.tabs.TabLayout;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.truyen.service.DatabaseSQLite.database;

public class StoryDetailActivity extends AppCompatActivity {
    private Story story;
    private TextView txtNameStoryDetail, txtContentStory, txtCountCategoriesDetail, txtAuthorDetail;
    private ImageView imgStoryDetail, imgLike, imgBack, imgDownload;
    private LinearLayout linearLayout;
    private SharedPreferences_Utils sharedPreferences_utils;
    private ChapterWatchedAdapter chapterWatchedAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Story> storyArrayList;
    private ArrayList<Chapter> chapterList;
    private List<Chapter> chapterListFromSharePre;
    public static int nID;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    BlurImageView blurImageView;
    private Button btnRead;
    String story_title, sIMG, author, sLinkImg, sDescription, sTitleChapter, sContent, sDate;
    private int nLike, rating_count, ratting, storyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        initView();
        addTabs();
        CheckInternet();
        setBack();
    }

    private void CheckInternet() {
        if (Commons.isConnectedtoInternet(this)) {
            showDetailfromID();
            addLike();
            checkLike();
            setEvent();
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

    @Override
    protected void onResume() {
        chapterListFromSharePre = sharedPreferences_utils.getDataSharePreferences_From_ChapterWatched(chapterList, nID);
        if (chapterListFromSharePre != null && chapterListFromSharePre.size() > 0) {
            String sTitle[] = chapterListFromSharePre.get(0).getTitle().split("-");
            btnRead.setText("Đọc tiếp: " + sTitle[1].split(":")[0]);
        }
        super.onResume();
    }

    private void checkLike() {
        List<Story> storyList = sharedPreferences_utils.get_SharedPreferences_Story_FollowFragment();
        if (storyList.size() > 0) {
            for (int i = 0; i < storyList.size(); i++) {
                if (storyList.get(i).getId() == nID) {
//                                                nLike = 0;
//                                                SharedPreferences_Utils.setSharedPreferences_Int("LIKE", nLike);
                    imgLike.setImageResource(R.drawable.like1);
                }
            }
        }
    }

    private void addLike() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (imgLike.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.like).getConstantState()) {

                    //thêm theo dõi
                    getIntentFormListStory();
                    DataService dataService = APIService.getService();
                    dataService.getStory("stories/" + nID + "/detail").enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response != null && response.isSuccessful()) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response.body());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject fatherJSON = jsonArray.getJSONObject(i);
                                        String story_title = (fatherJSON.getString("story_title"));
                                        String sIMG = (fatherJSON.getString("thumbnail_image"));
                                        sharedPreferences_utils.addStoriesToFollow(new Story(nID, story_title, sIMG));
                                        List<Story> storyList = sharedPreferences_utils.get_SharedPreferences_Story_FollowFragment();
                                        if (storyList.size() > 0) {
                                            for (int j = 0; j < storyList.size(); j++) {
                                                if (storyList.get(j).getId() == nID) {
                                                    imgLike.setImageResource(R.drawable.like1);
                                                    Toast.makeText(StoryDetailActivity.this, "Đã thêm vào ưa thích", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
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
                } else if (imgLike.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.like1).getConstantState()) {
                    sharedPreferences_utils.deleteStoryFromFollow(nID);
                    nLike = 1;
                    SharedPreferences_Utils.setSharedPreferences_Int("LIKE", nLike);
                    imgLike.setImageResource(R.drawable.like);
                }
            }
        });
    }

    private void setBack() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
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
    }

    public void addTabs() {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.add(new Fragment_Detail(), "Chi tiết");
        fragmentAdapter.add(new FragmentListActivity(), "Danh sách chương");
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackground(null);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void getIntentFormListStory() {
        Intent intent = getIntent();
        if (intent != null) {
            nID = intent.getIntExtra("NEWSTORY", 0);
        }
    }

    private void setEvent() {
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInHistory();
                chapterListFromSharePre = sharedPreferences_utils.getDataSharePreferences_From_ChapterWatched(chapterList, nID);
                if (chapterListFromSharePre != null && chapterListFromSharePre.size() > 0) {
                    Intent intent = new Intent(StoryDetailActivity.this, ReadStoryActivity.class);
                    intent.putExtra(Commons.Chapter, chapterListFromSharePre.get(0).getIdStory());
                    intent.putExtra("CHAPTER_ID", chapterListFromSharePre.get(0).getChapter_id());
                    Log.d("huhuh", chapterListFromSharePre.get(0).getIdStory() + "");
                    startActivity(intent);
                } else {
                    //Lần đầu đọc chapter 1- lần sau lưu đọc tiếp
                    Intent intent = new Intent(StoryDetailActivity.this, ReadStoryActivity.class);
                    intent.putExtra(Commons.Chapter, nID);
                    startActivity(intent);
                }
            }
        });

        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveStoryDetail();
//                saveContents();
//                Toast.makeText(StoryDetailActivity.this, "Đã thêm vào danh sách tải xuống", Toast.LENGTH_SHORT).show();
                checkDownloaded();
            }
        });
    }

    private void checkDownloaded() {
        getIntentFormListStory();
        database = new DatabaseSQLite(getApplicationContext(), "Story.sqlite", null, 1);
        database.QueryData(
                "CREATE TABLE IF NOT EXISTS Stories(Id INTEGER PRIMARY KEY AUTOINCREMENT, story_id INTEGER, story_title VARCHAR(200)," +
                        " author VARCHAR(200), rating DOUBLE, rating_count DOUBLE, description LONGTEXT, thumbnail_image VARCHAR(1000))");
        database.QueryData(
                "CREATE TABLE IF NOT EXISTS Chapter(Id INTEGER PRIMARY KEY AUTOINCREMENT, chapter_id INTEGER, " +
                        "story_id INTEGER, title VARCHAR(200), created_at VARCHAR(100), contents LONGTEXT)");

        Cursor cursor = database.GetData("SELECT * FROM Stories WHERE story_id = " + nID);

        if (cursor.getCount() == 0) {
            saveStoryDetail();
            saveContents();
            Toast.makeText(StoryDetailActivity.this, "Đã thêm vào danh sách tải xuống", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("story_id"));
                Log.d("koklkasd", id + "");
                if (id == nID)
                    Toast.makeText(this, "Truyện này đã nằm trong danh sách tải về!!!", Toast.LENGTH_SHORT).show();
                else {
                    saveStoryDetail();
                    saveContents();
                    Toast.makeText(StoryDetailActivity.this, "Đã thêm vào danh sách tải xuống", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveStoryDetail() {
        getIntentFormListStory();
        DataService dataService = APIService.getService();
        dataService.getStory("stories/" + nID + "/detail").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fatherJSON = jsonArray.getJSONObject(i);
                            storyID = (fatherJSON.getInt("story_id"));
                            story_title = (fatherJSON.getString("story_title"));
                            author = (fatherJSON.getString("author"));
                            ratting = (fatherJSON.getInt("rating"));
                            rating_count = (fatherJSON.getInt("rating_count"));
                            sLinkImg = fatherJSON.getString("thumbnail_image");
                            sDescription = (fatherJSON.getString("description"));
                            database.insertStory(storyID, story_title, author, ratting, rating_count, sDescription, sLinkImg);

//                            database.insertStory(1, "story_title", "author", 1, 1, "sDescription", "sLinkImg");

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

    private void saveContents() {
        DataService dataService = APIService.getService();
        dataService.getChapterList(nID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        storyID = object.getInt("story_id");
                        int idChapter = object.getInt("chapter_id");
                        sContent = object.getString("contents");
                        sDate = object.getString("created_at");
                        sTitleChapter = object.getString("title");
                        database.insertChapter(idChapter, storyID, sTitleChapter, sDate, sContent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void saveInHistory() {
        getIntentFormListStory();
        DataService dataService = APIService.getService();
        dataService.getStory("stories/" + nID + "/detail").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fatherJSON = jsonArray.getJSONObject(i);
                            story_title = (fatherJSON.getString("story_title"));
                            sIMG = (fatherJSON.getString("thumbnail_image"));
                            sharedPreferences_utils.setDataSharePreferences_From_StoryWatched(new Story(nID, story_title, sIMG));
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

    //Lấy id từ intent (adaper: NewStoriesAdapter)->truy vấn theo id, lấy chi tiết truyện
    private void showDetailfromID() {
        getIntentFormListStory();
        DataService dataService = APIService.getService();
        dataService.getStory("stories/" + nID + "/detail").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        story_title = (jsonObject.getString("story_title"));
                        author = (jsonObject.getString("author"));
                        rating_count = (jsonObject.getInt("rating_count"));
                        sLinkImg = jsonObject.getString("thumbnail_image");
                        Picasso.with(getApplication())
                                .load(sLinkImg)
                                .into(imgStoryDetail);
                        txtNameStoryDetail.setText(story_title);
                        txtAuthorDetail.setText("Tác giả: " + author);
                        txtCountCategoriesDetail.setText(rating_count + " Chương");

                        Picasso.with(getApplication())
                                .load(sLinkImg)
                                .into(blurImageView);
                        blurImageView.setBlur(15);
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

}