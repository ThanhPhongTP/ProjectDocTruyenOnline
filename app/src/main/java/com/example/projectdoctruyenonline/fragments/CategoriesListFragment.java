package com.example.projectdoctruyenonline.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.adapter.CategoriesAdapter;
import com.example.projectdoctruyenonline.models.Categories;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.service.APIService;
import com.example.projectdoctruyenonline.service.DataService;
import com.example.projectdoctruyenonline.service.Decrypt;

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
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
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
    private String sIV, sValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        initView();
        setHasOptionsMenu(true);
        if (Commons.isConnectedtoInternet(getActivity())) {
            getAPI();
//            loadMoreData();

        } else {
            Commons.showDialogError(getActivity());
        }
        return view;
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
//                if (response.isSuccessful()) {
//                    try {
//                        JSONArray array = new JSONArray(response.body().toString());
//                        for (int i = 0; i < array.length(); i++) {
//
//                            JSONObject object = array.getJSONObject(i);
//                            String sName = object.getString("name");
//
//
//
//                            String sDe = Decrypt.Base64Decode(sName);
//                            JSONObject jsonObject = new JSONObject(sDe);
//                            sIV = jsonObject.getString("iv");
//                            sValue = jsonObject.getString("value");
//                            String sContent = Decrypt.decrypt(Decrypt.key.getBytes(), sIV, sValue);
////                        txtReadStory.setText(sContent);
//                            Log.d("hfjkd", jsonObject + "");
//                            Log.d("hfjkd", sContent + "");
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Categories> newList = new ArrayList<>();
                for (Categories categories : categoriesList) {
                    String name = categories.getName().toLowerCase();
                    if (name.contains(newText)) {
                        newList.add(categories);
                    }
                }
                categoriesAdapter = new CategoriesAdapter(getActivity(), categoriesList);
                categoriesAdapter.setFilter(newList);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
                return true;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    //
    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecyclerFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lifecyclerFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("lifecyclerFragment", "onStop");
//        getActivity().getFragmentManager().popBackStack();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("lifecyclerFragment", "onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("lifecyclerFragment", "onDestroy");

    }
}