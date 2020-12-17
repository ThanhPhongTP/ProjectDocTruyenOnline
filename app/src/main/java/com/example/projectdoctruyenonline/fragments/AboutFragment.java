package com.example.projectdoctruyenonline.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.activities.SettingActivity;
import com.example.projectdoctruyenonline.activities.StoryWatchedActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private View view;
//    private TemplateView templateView;
    private  final String url_Security = "http://simbo.xyz/privacy.html";
    private  final String url_ShareApp = "https://play.google.com/store/apps/details?id=com.zing.mp3&hl=vi";
    private  final String url_FeedBack = "https://play.google.com/store/apps/details?id=com.zing.mp3&hl=vi";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  =  inflater.inflate(R.layout.fragment_about, container, false);
        initView();
//        checkConnectionAds();
        return  view;
    }
//    private void checkConnectionAds() {
//        if (CheckConnection.isConnectedtoInternet(getActivity())){
//            setAdsView();
//        }else {
//            templateView.setVisibility(View.GONE);
//        }
//    }

    private void initView() {
//        templateView =view.findViewById(R.id.template_AboutFragment);
        view.findViewById(R.id.linearStoryWatched).setOnClickListener(this);
        view.findViewById(R.id.linearSetting).setOnClickListener(this);
        view.findViewById(R.id.linearFeedBack).setOnClickListener(this);
        view.findViewById(R.id.linearShareApp).setOnClickListener(this);
        view.findViewById(R.id.linearSecurity).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linearStoryWatched:
                Intent intentStoryWatched = new Intent(getActivity(), StoryWatchedActivity.class);
                startActivity(intentStoryWatched);
                break;
            case R.id.linearSetting:
                Intent intentSetting = new Intent(getContext(), SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.linearFeedBack:
                Intent intentFeedBack = new Intent(Intent.ACTION_VIEW);
                intentFeedBack.setData(Uri.parse(url_FeedBack));
                startActivity(intentFeedBack);
                break;
            case R.id.linearShareApp:
                final String appPackageName = getActivity().getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url_ShareApp + appPackageName);
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
                break;
            case R.id.linearSecurity:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url_Security));
                startActivity(i);
                break;
        }
    }
//    private void setAdsView() {
//        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        AdLoader.Builder builder = new AdLoader.Builder(getActivity(),getString(R.string.Ads_appId));
//        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//            @Override
//            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                templateView.setNativeAd(unifiedNativeAd);
//            }
//        });
//        AdLoader adLoader  = builder.build();
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adLoader.loadAd(adRequest);
//    }
}