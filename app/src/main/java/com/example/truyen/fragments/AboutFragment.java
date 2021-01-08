package com.example.truyen.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.example.truyen.R;
import com.example.truyen.activities.AboutActivity;
import com.example.truyen.activities.GeneralSettingsActivity;
import com.example.truyen.activities.SettingActivity;
import com.example.truyen.activities.StoryWatchedActivity;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

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
    private final String url_Security = "http://simbo.xyz/privacy.html";
    private final String url_ShareApp = "https://play.google.com/store/apps/dev?id=4934522583960256568";
    private final String url_FeedBack = "https://play.google.com/store/apps/dev?id=4934522583960256568";
    private Switch aSwitch;
    SharedPreferences sharedPreferences;
    VectorDrawableCompat.VFullPath dressPath;
    private ImageView img_History, img_interface, img_feedback, img_share, img_security;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkTheme();
        view = inflater.inflate(R.layout.fragment_about, container, false);
        initView();
//        setImageColor();
        return view;
    }

//    private void setImageColor() {
////        VectorChildFinder vector = new VectorChildFinder(getContext(),
////                R.drawable.ic_baseline_history_24, img_History);
//
//
//        VectorChildFinder vector2 = new VectorChildFinder(getContext(),
//                R.drawable.ic_baseline_history_24, img_History);
//        VectorChildFinder vector3 = new VectorChildFinder(getContext(),
//                R.drawable.ic_baseline_history_24, img_History);
//        VectorChildFinder vector4 = new VectorChildFinder(getContext(),
//                R.drawable.ic_baseline_history_24, img_History);
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            VectorChildFinder vector = new VectorChildFinder(getContext(),
//                    R.drawable.ic_baseline_history_24, img_History);
//            dressPath = vector.findPathByName("img_history");
//            dressPath.setFillColor(0xFFFFFFFF);
//
//            VectorChildFinder vector1 = new VectorChildFinder(getContext(),
//                    R.drawable.ic_baseline_brightness_auto_24, img_interface);
//            dressPath = vector1.findPathByName("img_interface");
//
//            dressPath.setFillColor(0xFFFFFFFF);
//        } else {
//            VectorChildFinder vector = new VectorChildFinder(getContext(),
//                    R.drawable.ic_baseline_history_24, img_History);
//            dressPath = vector.findPathByName("img_history");
//            dressPath.setFillColor(0xFFFFFFFF);
//            VectorChildFinder vector1 = new VectorChildFinder(getContext(),
//                    R.drawable.ic_baseline_brightness_auto_24, img_interface);
//            dressPath = vector1.findPathByName("img_interface");
//
//            dressPath.setFillColor(0xFF000000);
//        }
//        img_History.invalidate();
//        img_interface.invalidate();
//    }


    private void initView() {
//        templateView =view.findViewById(R.id.template_AboutFragment);
        view.findViewById(R.id.linearStoryWatched).setOnClickListener(this);
        view.findViewById(R.id.linearSetting).setOnClickListener(this);
        view.findViewById(R.id.linearFeedBack).setOnClickListener(this);
        view.findViewById(R.id.linearShareApp).setOnClickListener(this);
        view.findViewById(R.id.linearSecurity).setOnClickListener(this);
        view.findViewById(R.id.linear_setting).setOnClickListener(this);
        view.findViewById(R.id.linearAbout).setOnClickListener(this);
//        aSwitch = view.findViewById(R.id.swTheme);
//        tv_GeneralSetting = view.findViewById(R.id.txt_setting);
//        sharedPreferences_utils = new SharedPreferences_Utils(getContext());
        sharedPreferences = getActivity().getSharedPreferences("MyPrefsSetting1111", Context.MODE_PRIVATE);
//        img_History = view.findViewById(R.id.img_history);
//        img_feedback = view.findViewById(R.id.img_feedback);
//        img_interface = view.findViewById(R.id.imgA);
//        img_share = view.findViewById(R.id.img_share);
//        img_security = view.findViewById(R.id.img_security);
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
        if (sharedPreferences.contains("THEME")) {
            getActivity().setTheme(nID);
        }

//        if (nID == R.style.ProjectDocTruyenOnline_Dark) {
//            VectorChildFinder vector = new VectorChildFinder(getContext(),
//                    R.drawable.ic_baseline_history_24, img_History);
//            dressPath = vector.findPathByName("img_history");
//            dressPath.setFillColor(0xFFFF0000);
//            img_History.invalidate();
//        } else {
//            VectorChildFinder vector = new VectorChildFinder(getContext(),
//                    R.drawable.ic_baseline_history_24, img_History);
//            dressPath = vector.findPathByName("img_history");
//            dressPath.setFillColor(0xFF000000);
//            img_History.invalidate();
//        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.linear_setting:
                startActivity(new Intent(getContext(), GeneralSettingsActivity.class));
                getActivity().finish();
                break;
            case R.id.linearAbout:
                startActivity(new Intent(getContext(), AboutActivity.class));
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