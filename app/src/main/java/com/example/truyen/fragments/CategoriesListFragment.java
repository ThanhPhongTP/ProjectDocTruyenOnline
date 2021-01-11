package com.example.truyen.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.adapter.CategoriesAdapter;
import com.example.truyen.adapter.StoryAdapter;
import com.example.truyen.models.Categories;
import com.example.truyen.models.Post;
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


public class CategoriesListFragment extends Fragment {

    private int id = 0;
    private String name = "";
    private String url = "";
    private List<Categories> categoriesList;
    private ArrayList<Story> listStory;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private StoryAdapter adapter;
    private boolean notLoading = true;
    private View footerView;
    private View view;
    public ProgressBar progressBar;
    private AlertDialog alertDialog;
    private int totalItemCount, firstVisibleItemCount, visibleItemCount, previousTotal;
    private boolean isLoadDing = true;
    private int page = 1;
    private GridLayoutManager gridLayoutManager;
    private Toolbar toolbar_Categories;
    private NestedScrollView nestedScrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkTheme();
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        initView();
        setHasOptionsMenu(true);
        CheckInternet();
        return view;
    }

    private void CheckInternet() {
        if (Commons.isConnectedtoInternet(getActivity())) {
            getAPI();
        } else {
            Commons.showDialogError(getActivity());
        }
    }

    @Override
    public void onResume() {
        getAPI();
        super.onResume();
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            getActivity().setTheme(nID);
        }
    }

    private void loadMoreData() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
//                    progressBar.setVisibility(View.VISIBLE);
                    getAPI();
                }
            }
        });
    }

    private void initView() {
        nestedScrollView = view.findViewById(R.id.nestedScrollViewCategories);
        progressBar = view.findViewById(R.id.progressbarLoadCategories);
        recyclerView = view.findViewById(R.id.recyclerView_Categories);
        toolbar_Categories = view.findViewById(R.id.toolbar_Categories);
        actionToolBar(toolbar_Categories);
        categoriesList = new ArrayList<>();
//        linearLayoutManager = new LinearLayoutManager(getActivity());

    }

    private void getAPI() {
        DataService dataService = APIService.getService();
        dataService.getCategories().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                categoriesList = response.body();
                categoriesAdapter = new CategoriesAdapter(getContext(), categoriesList);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setHasFixedSize(true);
                categoriesAdapter = new CategoriesAdapter(getActivity(), categoriesList);
                recyclerView.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });

    }


    private void parseResult(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Categories categories = new Categories();
                categories.setId(jsonObject.getInt("id"));
                categories.setName(jsonObject.getString("name"));
                categories.setTotal(jsonObject.getInt("total story"));
                categoriesList.add(categories);
                categoriesAdapter.notifyDataSetChanged();
                for (int j = 0; j < categoriesList.size(); j++) {
                    Log.d("123213", categoriesList.get(j).getName());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            categoriesAdapter.notifyDataSetChanged();
            Log.d("sizeStoryList", categoriesList.size() + "");
        }

    }

    private void actionToolBar(androidx.appcompat.widget.Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_item, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listStory = new ArrayList<>();
                DataService dataService = APIService.getService();
                dataService.savePost(query).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject item = jsonArray.getJSONObject(i);
                                    int nID = item.getInt("id");
                                    String sName = item.getString("name");
                                    String sAuthor = item.getString("author");
                                    int nTotalChapter = item.getInt("total chapter");
                                    String sIMG = item.getString("image");
                                    String sDate = item.getString("created_date");
                                    listStory.add(new Story(nID, sName, nTotalChapter, sAuthor, sDate, sIMG, 0));

                                    adapter = new StoryAdapter(getContext(), listStory);
                                    linearLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listStory.size() == 0)
                            Toast.makeText(getContext(), "Không có kết quả với từ khóa \"" + query + "\""       , Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                newText = newText.toLowerCase();
              listStory = new ArrayList<>();
//                for (Categories categories : categoriesList) {
//                    String name = categories.getName().toLowerCase();
//                    if (name.contains(newText)) {
//                        newList.add(categories);
//                    }
//                }
                adapter = new StoryAdapter(getContext(), listStory);
                adapter.setFilter(listStory);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    //
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("lifecyclerFragment", "onResume");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d("lifecyclerFragment", "onPause");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d("lifecyclerFragment", "onStop");
////        getActivity().getFragmentManager().popBackStack();
//
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d("lifecyclerFragment", "onDestroyView");
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("lifecyclerFragment", "onDestroy");
//
//    }
}