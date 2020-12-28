//package com.example.projectdoctruyenonline.fragments;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.projectdoctruyenonline.R;
//import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
//import com.example.projectdoctruyenonline.adapter.HistoryFragmentsAdapter;
//import com.example.projectdoctruyenonline.models.Story;
//
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HistoryFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class HistoryFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public HistoryFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HistoryFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static HistoryFragment newInstance(String param1, String param2) {
//        HistoryFragment fragment = new HistoryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    private View view;
//    private LinearLayoutManager linearLayoutManager;
//    private RecyclerView recyclerView;
//    private HistoryFragmentsAdapter adapter;
//    private List<Story> storyList;
//    private TextView txtNodataHistory;
//    private SharedPreferences_Utils sharedPreferencesUtils;
////    private TemplateView templateView;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_history, container, false);
//        initView();
//        storyList =  sharedPreferencesUtils.get_SharedPreferences_Story_HistoryFragment();
//        buildRecyclerview();
////        checkConnectionAds();
//        return view;
//    }
//    private void initView() {
//        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
//        txtNodataHistory = view.findViewById(R.id.txtNodataHistory);
//        recyclerView = view.findViewById(R.id.recyclerView_Docganday);
////        templateView =view.findViewById(R.id.template_HistoryFragment);
//    }
//
////    private void checkConnectionAds() {
////        if (CheckConnection.isConnectedtoInternet(getActivity())){
////            setAdsView();
////        }else {
////            templateView.setVisibility(View.GONE);
////        }
////    }
////
////    private void setAdsView() {
////        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
////            @Override
////            public void onInitializationComplete(InitializationStatus initializationStatus) {
////            }
////        });
////        AdLoader.Builder builder = new AdLoader.Builder(getActivity(),getString(R.string.Ads_appId));
////        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
////            @Override
////            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
////                templateView.setNativeAd(unifiedNativeAd);
////            }
////        });
////        AdLoader adLoader  = builder.build();
////        AdRequest adRequest = new AdRequest.Builder().build();
////        adLoader.loadAd(adRequest);
////    }
//    private void buildRecyclerview() {
//        if (storyList.size()>0) {
//            adapter = new HistoryFragmentsAdapter(getActivity(), storyList);
//            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setVisibility(View.VISIBLE);
//            txtNodataHistory.setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();
//        }else {
//            txtNodataHistory.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
//    }
//
//}