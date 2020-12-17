package com.example.projectdoctruyenonline.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.projectdoctruyenonline.MainActivity;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.adapter.HomeStoriesAdapter;
import com.example.projectdoctruyenonline.models.Ratting;
import com.example.projectdoctruyenonline.service.APIService;
import com.example.projectdoctruyenonline.service.DataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private FragmentTransaction fragmentTransaction;
    private LinearLayout linearLayout;
    private Ratting ratting;
    private SliderLayout sliderLayout;
    private HashMap<String, Integer> HashMapForLocalRes;
    private HashMap<String, String> HashMapForURL;
    private RecyclerView recycleHot, recycleNew, recycleNominations;
    private HomeStoriesAdapter homeStoriesAdapter, nominationStoriesAdapter, newStoriesAdapter;
    private ArrayList<Ratting> listRating_Hot, listRating_Nomination, listRating_New;
    private int page = 1;
    private NestedScrollView nestedScrollViewStory;
    private SwipeRefreshLayout pullToRefresh;
    int story_id;
    String story_title;
    String author;
    Double rating;
    int rating_count;
    String description;
    String thumbnail_image;

    public FragmentHome() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setControl(view);
        setSliderLayout();
//        loadMoreData();
        addHotStory();
        setSeenMore();
        pullToRefresh();



        return view;
    }

    private void setSliderLayout() {
                AddImageUrlFormLocalRes();
//        AddImagesUrlOnline();

        for (String name : HashMapForLocalRes.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(HashMapForLocalRes.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default); //Hiệu ứng chuyển slide
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom); // Vị trí indicator
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(5000);
        sliderLayout.addOnPageChangeListener(FragmentHome.this);
    }

    public void AddImagesUrlOnline() {
        HashMapForURL = new HashMap<String, String>();
        HashMapForURL.put("Hinh1", "https://static.8cache.com/cover/eJzLyTDWLzdK0jXQjTQycHItNXPN8ct3CUvTNSwo8TQP9Q7LsghwjE_OdKzyNUsJswhJcc3NTdYN9TU2j8_0LHYuySo3NzbK8HEONw1IDyotTE0N98qvLHS0LTcyNNXNMDYyAgDKHx6H/yeu-nu-xin-tu-trong.jpg");
        HashMapForURL.put("Hinh2", "https://static.8cache.com/cover/o/eJzLyTDW1_WKz4tyMvELMHdP1g_LcipLCYqPzPH01HeEAuciT_2A8qycvEg3C8dQV_1yI0NT3QxjIyNdz2QTIwCzaROn/chang-kho.jpg");
        HashMapForURL.put("Hinh3", "https://static.8cache.com/cover/eJzLyTDWT88xNXMNDTLzzDAyc0sJ8TPKNDAzM8gsDgovqkr2KvTXzc0oSfeNCIpIysjLSi-tsigxCQ3MCHAKNCzNDAwMCzSOSLIITynKDCwJSwwpdgz0TE23LTcyNNXNMDYyAgAPvh-r/vo-dich-dai-lao-sap-xuat-the.jpg");
        HashMapForURL.put("Hinh4", "https://static.8cache.com/cover/eJzLyTDWdzP0jYw0sAj31o10NC3Lzco3q4wP8o9wKa8q80gNNkv2dMzzinRJyXMOs9TNNnJyTHVLDSvwCytPDfXIdoqPjzDx8vPyKcyqyjcpDU4sKAxPt_S1LTcyNNXNMDYyAgAFWx9g/ta-voi-su-mon-khong-hop.jpg");
        HashMapForURL.put("Hinh5", "https://static.8cache.com/cover/eJzLyTDWLzWuNHTKKHXNjQjKL3YtyCjx8k6Mj8h3zihOdokv8NEtLPR0MnYNqchPMTCyjCqP8PQNzDVKco_IMS8zCy9Jt_RxyQtLdiz2zPGJCnUMLE0t8gy0LTcyNNXNMDYyAgBa5CA0/tu-la-gioi-chi-ton.jpg");
    }

    public void AddImageUrlFormLocalRes() {
        HashMapForLocalRes = new HashMap<String, Integer>();
        HashMapForLocalRes.put("Hinh1", R.drawable.i1);
        HashMapForLocalRes.put("Hinh2", R.drawable.i2);
        HashMapForLocalRes.put("Hinh3", R.drawable.i3);
    }


    @Override
    public void onStop() {
//        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void loadMoreData() {
        nestedScrollViewStory.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
//                    progressBar.setVisibility(View.VISIBLE);
                    addHotStory();
                }
            }
        });
    }

    private void addHotStory() {
        //Truyện hay
        listRating_Hot = new ArrayList<>();
        homeStoriesAdapter = new HomeStoriesAdapter(getContext(), listRating_Hot);
        recycleHot.setAdapter(homeStoriesAdapter);
        //Truyện đề cử
        listRating_Nomination = new ArrayList<>();
        nominationStoriesAdapter = new HomeStoriesAdapter(getContext(), listRating_Nomination);
        recycleNominations.setAdapter(nominationStoriesAdapter);
        //Truyện mới
        listRating_New = new ArrayList<>();
        newStoriesAdapter = new HomeStoriesAdapter(getContext(), listRating_New);
        recycleNew.setAdapter(newStoriesAdapter);

        DataService dataService = APIService.getService();
        dataService.getNewStory().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject fatherJSON = new JSONObject(response.body().toString());
                        JSONArray arrayData = fatherJSON.getJSONArray("data");
                        for (int i = 0; i < arrayData.length(); i++) {
                            JSONObject item = arrayData.getJSONObject(i);
                            story_id = item.getInt("story_id");
                            story_title = item.getString("story_title");
                            author = item.getString("author");
                            rating = item.getDouble("rating");
                            rating_count = item.getInt("rating_count");
                            description = item.getString("description");
                            thumbnail_image = item.getString("thumbnail_image");
//                            Log.d("TESTTT", item.getDouble("rating") + "");
                            //hay
                            if (item.getDouble("rating") >= 7)
                                listRating_Hot.add(new Ratting(story_id, story_title, author, rating, rating_count, description, thumbnail_image));
                            homeStoriesAdapter.notifyDataSetChanged();
                            recycleHot.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            //đề cử ratting_count > 30 và lấy 6 truyền đề cử
                            if (item.getInt("rating_count") > 30)
                                if (listRating_Nomination.size() <= 5)
                                    listRating_Nomination.add(new Ratting(story_id, story_title, author, rating, rating_count, description, thumbnail_image));
                            nominationStoriesAdapter.notifyDataSetChanged();
                            recycleNominations.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                        }
                        //mới
                        for (int j = arrayData.length() - 1; j >= arrayData.length() - 6; j--) {
                            JSONObject item = arrayData.getJSONObject(j);
                            story_id = item.getInt("story_id");
                            story_title = item.getString("story_title");
                            author = item.getString("author");
                            rating = item.getDouble("rating");
                            rating_count = item.getInt("rating_count");
                            description = item.getString("description");
                            thumbnail_image = item.getString("thumbnail_image");
                            listRating_New.add(new Ratting(story_id, story_title, author, rating, rating_count, description, thumbnail_image));
                            newStoriesAdapter.notifyDataSetChanged();
                            recycleNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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

    private void setSeenMore(){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFrm();

            }
        });
    }


    @SuppressLint("ResourceAsColor")
    private void pullToRefresh() {

//        pullToRefresh.setProgressBackgroundColorSchemeColor(R.color.clmain);
//        pullToRefresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#0066FF"));
        pullToRefresh.setColorScheme(R.color.clmain);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addHotStory();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void setControl(View view) {
        sliderLayout = view.findViewById(R.id.slider);
        recycleHot = view.findViewById(R.id.rcv_truyen_hay);
        recycleNew = view.findViewById(R.id.rcv_truyen_moi);
        recycleNominations = view.findViewById(R.id.rcv_truyen_decu);
        linearLayout = view.findViewById(R.id.lnxemthem);
        pullToRefresh = view.findViewById(R.id.pull_to_refresh);
//        nestedScrollViewStory = view.findViewById(R.id.nestedScrollViewHome);

    }
}