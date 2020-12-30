package com.example.projectdoctruyenonline.fragments;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.projectdoctruyenonline.activities.ReadStoryActivity;
import com.example.projectdoctruyenonline.activities.SettingActivity;
import com.example.projectdoctruyenonline.adapter.ViewPagerReadStoryAdapter;
import com.example.projectdoctruyenonline.models.Chapter;
import com.example.projectdoctruyenonline.service.APIService;
import com.example.projectdoctruyenonline.service.DataService;
import com.example.projectdoctruyenonline.service.Decrypt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadFragment extends Fragment implements View.OnClickListener {
    private TextView txtReadStory;
    public Chapter chapter;
    private LinearLayout linearLayout_Visible;
    private ObjectAnimator objectAnimator;
    private RelativeLayout relativeLayout;
    private static final String idAdView = "723868771524475_723869634857722";
    private RelativeLayout relativeLayoutReadStory;
    private SharedPreferences_Utils sharedPreferencesUtils;
    private Typeface typeface;
    private NestedScrollView scrollView;
    private ImageView img_MenuStory, image_Pause, img_SettingReadStory;
    private SeekBar seekBar_ReadStory;
    private List<Chapter> chapterArrayList;
    private View view;
    private ViewPagerReadStoryAdapter viewPagerReadStoryAdapter;
    boolean visible_relativeLayoutLayoutClick;
    boolean play = true;
    private Handler handler;
    private TimerTask timerTask;
    private Timer scrollTimer;
    private int id = 0;
    private int storyId = 0;
    private String title = "";
    private String url = "";
    private int page = 1;
    private String sIV, sValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_read, container, false);
        initView();
        getDataIntentFromChapter();
//        sharedPreferencesUtils.setDataSharePreferences_From_Chapter((ArrayList<Chapter>) chapterArrayList, chapter);
        if (Commons.isConnectedtoInternet(getActivity())) {
            getAPI();
            eventSeekBarReadStory();
            setEventScrollProgress();
        } else {
            Commons.showDialogError(getActivity());
        }

//        loadAdsBannerView();
        return view;
    }

    private void getAPI() {
        DataService dataService = APIService.getService();
        dataService.getChapterList(chapter.getIdStory()).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response != null && response.isSuccessful()) {
                    chapterArrayList = response.body();
                    int compareNumber;
                    if (chapterArrayList != null) {
                        for (int i = 0; i < chapterArrayList.size(); i++) {
                            compareNumber = i + 1;
                            if (getArguments().getInt(Commons.ARG_SECTION_NUMBER) == compareNumber) {
                                chapter = chapterArrayList.get(i);
                                //Decryption content
                                String sDe = Decrypt.Base64Decode(chapter.getContents());
                                try {
                                    JSONObject object = new JSONObject(sDe);
                                    sIV = object.getString("iv");
                                    sValue = object.getString("value");
                                    String sContent = Decrypt.decrypt(Decrypt.key.getBytes(), sIV, sValue);
                                    txtReadStory.setText(sContent);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        txtReadStory.setText(Html.fromHtml(sContent, Html.FROM_HTML_MODE_COMPACT));
                                    } else {
                                        txtReadStory.setText(Html.fromHtml(sContent));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
        viewPagerReadStoryAdapter = new ViewPagerReadStoryAdapter(getActivity().getSupportFragmentManager(), chapterArrayList, chapter);
//        databaseHelper = new DatabaseHelper(getActivity());
        txtReadStory = view.findViewById(R.id.textviewReadStory);
        relativeLayoutReadStory = view.findViewById(R.id.relativeLayoutRedStory);
        scrollView = view.findViewById(R.id.scrollView_ReadStory);
        seekBar_ReadStory = view.findViewById(R.id.seekBar_ReadStoryActivity);
        image_Pause = view.findViewById(R.id.image_Pause);
        linearLayout_Visible = view.findViewById(R.id.linearLayout_Visible);
        view.findViewById(R.id.img_SettingReadStory).setOnClickListener(this);
        view.findViewById(R.id.image_Pause).setOnClickListener(this);
        view.findViewById(R.id.img_MenuStory).setOnClickListener(this);
        view.findViewById(R.id.relativeLayoutLayoutClick).setOnClickListener(this);
//        chapterArrayList = new ArrayList<>();
    }

//    private void loadAdsBannerView() {
//        adView = view.findViewById(R.id.adViewReadFragment);
//        MobileAds.initialize(getActivity(),"ca-app-pub-5478028523793903/9984628664");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//    }


    private void setEventScrollProgress() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if (scrollY >= 100) {
                    sharedPreferencesUtils.setDataSharePreferences_From_Chapter(chapter);
                }
                int textViewHeight = txtReadStory.getHeight();
                seekBar_ReadStory.setProgress(scrollY * 100 / textViewHeight);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_SettingReadStory:
                goToSettingActivity();
                break;
            case R.id.image_Pause:
                eventPlayorPause();
                break;
            case R.id.img_MenuStory:
                getActivity().finish();
                break;
            case R.id.relativeLayoutLayoutClick:
                eventVisibleViewSetting();
                break;
        }
    }

    private void getDataIntentFromChapter() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(Commons.Chapter)) {
                chapter = (Chapter) intent.getSerializableExtra(Commons.Chapter);
                Log.d("RTYUYTRE", chapter.getTitle());
//                Log.d("getContentsgetContents",chapter.getContents().getChapter_id()+"");

            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        ////////setBackground
        relativeLayoutReadStory.setBackgroundColor(sharedPreferencesUtils.getColorBackgroundReadStory());
        ////////setScreenTimeOut
        setScreenTimeout(sharedPreferencesUtils.getScreenTimeOutReadStory());
        ////setTextSize
        txtReadStory.setTextSize(sharedPreferencesUtils.getTextSizeReadStory());
        //////setFontStyle
//        typeface = getResources().getFont(settingSharedPreferences.getFontReadStory());
        typeface = ResourcesCompat.getFont(getActivity(), sharedPreferencesUtils.getFontReadStory());
        txtReadStory.setTypeface(typeface);
        ///setLineHeight
        txtReadStory.setLineSpacing(sharedPreferencesUtils.getLineHeight(), 1);
        ////setTextcolor
        txtReadStory.setTextColor(sharedPreferencesUtils.getChangeTextColor());
//        image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setScreenTimeout(int milliseconds) {
        Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,milliseconds);
    }

    public static ReadFragment newInstance(int sectionNumber) {
        ReadFragment fragment = new ReadFragment();
        Bundle args = new Bundle();
        args.putInt(Commons.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private void eventVisibleViewSetting() {
        visible_relativeLayoutLayoutClick = !visible_relativeLayoutLayoutClick;
        if (visible_relativeLayoutLayoutClick) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((ReadStoryActivity) getActivity()).testToolBarId.setVisibility(View.VISIBLE);
            linearLayout_Visible.setVisibility(View.VISIBLE);
//            adView.setVisibility(View.GONE);
        } else {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((ReadStoryActivity) getActivity()).testToolBarId.setVisibility(View.GONE);
            linearLayout_Visible.setVisibility(View.GONE);
//            adView.setVisibility(View.VISIBLE);
        }
    }

    private void eventPlayorPause() {
//        play = !play;
        if (play) {
            image_Pause.setImageResource(R.drawable.ic_baseline_play_24);
            autoScrollViewVersiton2();
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((ReadStoryActivity) getActivity()).testToolBarId.setVisibility(View.GONE);
            linearLayout_Visible.setVisibility(View.GONE);
            play = false;
        } else {
            play = true;
            image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);
            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

    private void autoScrollViewVersiton2() {
        scrollTimer = new Timer();
        handler = new Handler();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MoveScrollView moveScrollView = new MoveScrollView();
                        moveScrollView.execute();
                    }
                });
            }
        };
        scrollTimer.schedule(timerTask, 0, 10);
    }

    private class MoveScrollView extends AsyncTask<Void, Void, Void> {
        protected void onProgressUpdate(Void... progress) {
        }

        protected Void doInBackground(Void... params) {
            int seekBar_UserClick = SharedPreferences_Utils.getSeeBar();
            int scrollPos = (int) (scrollView.getScrollY() + seekBar_UserClick);
            scrollView.smoothScrollTo(0, scrollPos);
            return null;
        }

    }

    private void goToSettingActivity() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        if (timerTask != null) {
            timerTask.cancel();
        }
        image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);
        startActivity(intent);
    }

    private void eventSeekBarReadStory() {
        seekBar_ReadStory = view.findViewById(R.id.seekBar_ReadStoryActivity);
        seekBar_ReadStory.setProgress(0);
        seekBar_ReadStory.setMax(95);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar_ReadStory.setMin(0);
        }
        seekBar_ReadStory.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekBarProgress = progress;
                Log.d("QQQQQQQ", seekBarProgress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int textViewHeight = txtReadStory.getHeight();
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);
                        int scrollViewForText = textViewHeight * seekBarProgress / 100;
                        scrollView.scrollTo(0, scrollViewForText);
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                    }
                });
            }
        });
    }
}