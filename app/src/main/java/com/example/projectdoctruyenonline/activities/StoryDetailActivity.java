package com.example.projectdoctruyenonline.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.projectdoctruyenonline.adapter.ChapterWatchedAdapter;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.models.Story;
import com.example.projectdoctruyenonline.service.APIService;
import com.example.projectdoctruyenonline.service.DataService;
import com.example.projectdoctruyenonline.service.Decrypt;
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

public class StoryDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Story story;
    private TextView txtNameStoryDetail, txtContentStory, txtCountCategoriesDetail, txtAuthorDetail;
    private Button btnListChapters;
    private ImageView imgStoryDetail, imgLike, imgBack;
    private Toolbar toolbar_StoryDetail;
    private RecyclerView recyclerView_ChapterWatched;
    private ArrayList<Chapter> chapterList;
    private SharedPreferences_Utils sharedPreferences_utils;
    private ChapterWatchedAdapter chapterWatchedAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Story> storyArrayList;
    private List<Chapter> chapterListFromSharePre;
    int nID;


    BlurImageView blurImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
//        getIntentFormListStory();
        initView();
        if (Commons.isConnectedtoInternet(this)) {
            showDetailfromID();
        } else {
            Commons.showDialogError(this);
        }
        ///
//        showDetailfromID();
        addLike();
        checkLike();
        setBack();

//        blurImageView.setImageResource(R.drawable.i3);
//        blurImageView.setBlur(25);
    }

    private void getAPI() {
        DataService dataService = APIService.getService();
//        String uttt = "categories/"+categories.getId()+"/stories?page="+page";
        dataService.getStory("rates/" + story.getId() + "").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONObject fatherJSON = new JSONObject(response.body());
                        String story_title = (fatherJSON.getString("story_title"));
                        String author = (fatherJSON.getString("author"));
                        int rating_count = (fatherJSON.getInt("rating_count"));
                        String description = (fatherJSON.getString("description"));
                        txtNameStoryDetail.setText(story_title);
                        txtContentStory.setText(description);
                        txtAuthorDetail.setText("Tác giả: " + author);
                        txtCountCategoriesDetail.setText(rating_count + " Chương");
                        Log.d("ASAS", description + "");

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

    int nlike;

    private void checkLike() {
        List<Story> storyList = sharedPreferences_utils.get_SharedPreferences_Story_FollowFragment();
        if (storyList.size() > 0) {
            for (int i = 0; i < storyList.size(); i++) {
                if (storyList.get(i).getId() == nID) {
//                                                nlike = 0;
//                                                SharedPreferences_Utils.setSharedPreferences_Int("LIKE", nlike);
                    imgLike.setImageResource(R.drawable.like1);
                }
            }
        }
    }

    private void addLike() {
        imgLike.setOnClickListener(new View.OnClickListener() {
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
                    nlike = 1;
                    SharedPreferences_Utils.setSharedPreferences_Int("LIKE", nlike);
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
        recyclerView_ChapterWatched = findViewById(R.id.recyclerView_ChapterWatched);
//        toolbar_StoryDetail = findViewById(R.id.toolbar_StoryDetail);
        txtNameStoryDetail = findViewById(R.id.txtNameStoryDetail);
        imgLike = findViewById(R.id.imglike);
//        txtNameStoryDetail.setText(story.getName());
        findViewById(R.id.btnListChapters).setOnClickListener(this);
        findViewById(R.id.btnFeedBackChapter).setOnClickListener(this);
        findViewById(R.id.btnChapterRedStoryLimit1).setOnClickListener(this);
        imgStoryDetail = findViewById(R.id.imgStoryDetail);
        blurImageView = findViewById(R.id.imgBackground);
        imgBack = findViewById(R.id.imgback);
//        int kq = story.getId() %19;
//        int id = getResources().getIdentifier("com.example.projectdoctruyenonline:drawable/s" + kq, null, null);
//        imgStoryDetail.setImageResource(id);

//        actionToolBar(toolbar_StoryDetail);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListChapterListFromSharePre();

    }

    private void getListChapterListFromSharePre() {
        chapterListFromSharePre = sharedPreferences_utils.getDataSharePreferences_From_ChapterWatched(chapterList, nID);
        if (chapterListFromSharePre != null) {
            chapterWatchedAdapter = new ChapterWatchedAdapter(this, chapterListFromSharePre);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView_ChapterWatched.setLayoutManager(linearLayoutManager);
            recyclerView_ChapterWatched.setHasFixedSize(true);
            recyclerView_ChapterWatched.setAdapter(chapterWatchedAdapter);
            chapterWatchedAdapter.notifyDataSetChanged();
        }
    }

    private void actionToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentFormListStory() {
        Intent intent = getIntent();
        if (intent != null) {
            nID = intent.getIntExtra("NEWSTORY", 0);
            Log.d("NEWSTORY", nID + "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnListChapters:
                Intent intent1 = new Intent(StoryDetailActivity.this, ChapterListActivity.class);
                intent1.putExtra(Commons.Chapter, nID);
                startActivity(intent1);
                break;
            case R.id.btnChapterRedStoryLimit1:
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
                Intent intent = new Intent(StoryDetailActivity.this, ChapterListActivity.class);
                intent.putExtra(Commons.Chapter, nID);
                startActivity(intent);
                break;
            case R.id.btnFeedBackChapter:
                Intent intentACTION_VIEW = new Intent(Intent.ACTION_VIEW);
                intentACTION_VIEW.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zing.mp3&hl=vi"));
                startActivity(intentACTION_VIEW);
                break;
        }
    }

    //Lấy id từ intent (adaper: NewStoriesAdapter)->truy vấn theo id, lấy chi tiết truyện
    private void showDetailfromID() {
        getIntentFormListStory();
        DataService dataService = APIService.getService();
//        String uttt = "categories/"+categories.getId()+"/stories?page="+page";
        dataService.getStory("stories/" + nID + "/detail").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//                        JSONObject fatherJSON = new JSONObject(response.body());
                        String story_title = (jsonObject.getString("story_title"));
                        String author = (jsonObject.getString("author"));
                        int rating_count = (jsonObject.getInt("rating_count"));
                        String description = (jsonObject.getString("description"));
                        String sLinkImg = jsonObject.getString("thumbnail_image");
                        Picasso.with(getApplication())
                                .load(sLinkImg)
                                .into(imgStoryDetail);
                        txtNameStoryDetail.setText(story_title);
//                        txtContentStory.setText(description);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtContentStory.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtContentStory.setText(Html.fromHtml(description));
                        }
                        txtAuthorDetail.setText("Tác giả: " + author);
                        txtCountCategoriesDetail.setText(rating_count + " Chương");
//                        //Lưu truyện vào lịch sử

//                        Picasso.with(getApplication())
//                                .load(sLinkImg)
//                                .into(blurImageView);

                        Log.d("tetete", imgStoryDetail.getDrawable() + "");
//                        blurImageView.setImageDrawable(imgStoryDetail.getDrawable());


//                        blurImageView.setBlur(3);
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