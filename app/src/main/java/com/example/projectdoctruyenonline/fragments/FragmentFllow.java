package com.example.projectdoctruyenonline.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.projectdoctruyenonline.adapter.FollowAdapter;
import com.example.projectdoctruyenonline.adapter.HistoryFragmentsAdapter;
import com.example.projectdoctruyenonline.models.Story;

import java.util.List;

public class FragmentFllow extends Fragment {
    private RecyclerView recyclerView;
    private FollowAdapter adapter;
    private TextView txtNodataHistory;
    private List<Story> storyList;
    private SharedPreferences_Utils sharedPreferencesUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fllow, container, false);
        setControl(view);
        buildRecyclerview();
        return view;
    }

    @Override
    public void onResume() {
        buildRecyclerview();
        super.onResume();
    }

    private void buildRecyclerview() {
        storyList =  sharedPreferencesUtils.get_SharedPreferences_Story_FollowFragment();
        if (storyList.size()>0) {
            adapter = new FollowAdapter(getActivity(), storyList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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

    private void setControl(View view) {
        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
        txtNodataHistory = view.findViewById(R.id.txtFL);
        recyclerView = view.findViewById(R.id.rcv_follow);
    }
}