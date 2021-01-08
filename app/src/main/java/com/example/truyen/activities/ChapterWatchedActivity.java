package com.example.truyen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.adapter.ViewPagerReadStoryAdapter;
import com.example.truyen.models.Chapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChapterWatchedActivity extends AppCompatActivity implements View.OnClickListener {
    public Chapter chapter;
    private TextView textviewReadStory;
    private TextView txtReadStory;
    private LinearLayout linearLayout_Visible;
    private ObjectAnimator objectAnimator;
    private RelativeLayout relativeLayout;
    private static final String idAdView = "723868771524475_723869634857722";
    private RelativeLayout relativeLayoutReadStory;
    private SharedPreferences_Utils sharedPreferencesUtils;
    private Typeface typeface;
    private NestedScrollView scrollView;
    private ImageView img_MenuStory,image_Pause,img_SettingReadStory;
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
    private String title ="";
    private String url = "";
    private int page = 1;
    private Toolbar toolbar_ChapterW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_watched);
        initView();
        getDataIntentFromChapter();
        setEventScrollProgress();
        eventSeekBarReadStory();
    }
    private void setEventScrollProgress() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                int textViewHeight = txtReadStory.getHeight();
                seekBar_ReadStory.setProgress(scrollY*100/textViewHeight);
            }
        });

    }
    private void initView() {
        toolbar_ChapterW = findViewById(R.id.toolbar_ChapterW);
        textviewReadStory = findViewById(R.id.textviewReadStory);
        sharedPreferencesUtils = new SharedPreferences_Utils(this);
        txtReadStory = findViewById(R.id.textviewReadStory);
        relativeLayoutReadStory = findViewById(R.id.relativeLayoutRedStory);
        scrollView = findViewById(R.id.scrollView_ReadStory);
        seekBar_ReadStory = findViewById(R.id.seekBar_ReadStoryActivity);
        image_Pause = findViewById(R.id.image_Pause);
        seekBar_ReadStory = findViewById(R.id.seekBar_ReadStoryActivity);
        linearLayout_Visible = findViewById(R.id.linearLayout_Visible);
        findViewById(R.id.img_SettingReadStory).setOnClickListener(this);
        findViewById(R.id.image_Pause).setOnClickListener(this);
        findViewById(R.id.img_MenuStory).setOnClickListener(this);
        findViewById(R.id.relativeLayoutLayoutClick).setOnClickListener(this);
    }
    private  void setScreenTimeout(int millisecounds){
//        android.provider.Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,millisecounds);
        Settings.System.getInt(this.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,millisecounds);
    }
    private void getDataIntentFromChapter() {
        Intent intent =getIntent();
        if (intent != null){
            if (intent.hasExtra(Commons.Chapter)) {
                chapter = (Chapter)intent.getSerializableExtra(Commons.Chapter);
                textviewReadStory.setText(chapter.getContents());
            }
        }
    }

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
        typeface = ResourcesCompat.getFont(this, sharedPreferencesUtils.getFontReadStory());
        txtReadStory.setTypeface(typeface);
        ///setLineHeight
        txtReadStory.setLineSpacing(sharedPreferencesUtils.getLineHeight(),1);
        ////setTextcolor
        txtReadStory.setTextColor(sharedPreferencesUtils.getChangeTextColor());
//        image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_SettingReadStory:
                goToSettingActivity();
                break;
            case R.id.image_Pause:
                eventPlayorPause();
                break;
            case R.id.img_MenuStory:
                finish();
                break;
            case R.id.relativeLayoutLayoutClick:
                eventVisibleViewSetting();
                break;
        }
    }
    private void eventPlayorPause() {
//        play = !play;
        if (play){
            image_Pause.setImageResource(R.drawable.ic_baseline_play_24);
            autoScrollViewVersiton2();
            play = false;
        }else {
            play = true;
            image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);
            if (timerTask !=null){
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
        Intent intent = new Intent(this, SettingActivity.class);
        if (timerTask !=null){
            timerTask.cancel();
        }
        image_Pause.setImageResource(R.drawable.ic_baseline_pause_24);
        startActivity(intent);
    }
    private void eventSeekBarReadStory() {
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
                Log.d("QQQQQQQ",seekBarProgress+"");
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
                        int scrollViewForText =  textViewHeight *seekBarProgress/100;
                        scrollView.scrollTo(0,scrollViewForText);
                        if (timerTask !=null){
                            timerTask.cancel();
                        }
                    }
                });
            }
        });
    }
    private void eventVisibleViewSetting() {
        visible_relativeLayoutLayoutClick = !visible_relativeLayoutLayoutClick;
        if (visible_relativeLayoutLayoutClick){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbar_ChapterW.setVisibility(View.VISIBLE);
            linearLayout_Visible.setVisibility(View.VISIBLE);
//            adView.setVisibility(View.GONE);
        }else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbar_ChapterW.setVisibility(View.GONE);
            linearLayout_Visible.setVisibility(View.GONE);
//            adView.setVisibility(View.VISIBLE);
        }
    }
}