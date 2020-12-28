package com.example.projectdoctruyenonline.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.projectdoctruyenonline.adapter.HistoryFragmentsAdapter;
import com.example.projectdoctruyenonline.models.Ratting;
import com.example.projectdoctruyenonline.models.Story;

import java.util.List;

public class FragmentHistory extends Fragment {

    private View view;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private HistoryFragmentsAdapter adapter;
    private List<Story> storyList;
    private TextView txtNodataHistory;
    private SharedPreferences_Utils sharedPreferencesUtils;
    //    private TemplateView templateView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        initView();
        storyList =  sharedPreferencesUtils.get_SharedPreferences_Story_HistoryFragment();

        buildRecyclerview();
//        checkConnectionAds();
        return view;
    }
    private void initView() {
        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
        txtNodataHistory = view.findViewById(R.id.txtNodataHistory);
        recyclerView = view.findViewById(R.id.recyclerView_Docganday);
    }

    @Override
    public void onResume() {
        buildRecyclerview();
        super.onResume();
    }

    private void buildRecyclerview() {
        if (storyList.size()>0) {
            adapter = new HistoryFragmentsAdapter(getActivity(), storyList);
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            txtNodataHistory.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            txtNodataHistory.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

}