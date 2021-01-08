package com.example.truyen.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.truyen.Commons;
import com.example.truyen.R;
import com.example.truyen.SharedPreferences_Utils.SharedPreferences_Utils;
import com.example.truyen.activities.ReadStoryActivityOffline;
import com.example.truyen.activities.SettingActivity;
import com.example.truyen.models.Chapter;
import com.example.truyen.service.DatabaseSQLite;
import com.example.truyen.service.Decrypt;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.truyen.service.DatabaseSQLite.database;

public class ReadFragmentOffline extends Fragment implements View.OnClickListener {
    private TextView txtReadStory;
    public Chapter chapter;
    private LinearLayout linearLayout_Visible;
    private LinearLayout relativeLayoutReadStory;
    private SharedPreferences_Utils sharedPreferencesUtils;
    private Typeface typeface;
    private NestedScrollView scrollView;
    private ImageView image_Pause;
    private SeekBar seekBar_ReadStory;
    boolean visible_relativeLayoutLayoutClick;
    boolean play = true;
    private Handler handler;
    private TimerTask timerTask;
    private Timer scrollTimer;
    int story_id, chapter_id;
    private String sIV, sValue, sContent;
    int compareNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkTheme();
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        setControl(view);

        showData();
        eventSeekBarReadStory();
        setEventScrollProgress();

        return view;
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            getActivity().setTheme(nID);
        }
    }

    private void getDataIntentFromChapter() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(Commons.Chapter)) {
                story_id = intent.getIntExtra(Commons.Chapter, 0);
                chapter_id = intent.getIntExtra("CHAPTER_ID", 0);
            }
        }
    }

    private void showData() {

        ArrayList<Chapter> chapterArrayList = new ArrayList<Chapter>();
        getDataIntentFromChapter();
        database = new DatabaseSQLite(getContext(), "Story.sqlite", null, 1);
        Cursor cursor = database.GetData("SELECT * FROM Chapter WHERE story_id = " + story_id);
        while (cursor.moveToNext()) {
            int nChapter_id = cursor.getInt(cursor.getColumnIndex("chapter_id") );
            int nStory_id = cursor.getInt(cursor.getColumnIndex("story_id") );
            String title = cursor.getString(cursor.getColumnIndex("title") );
            String contents = cursor.getString(cursor.getColumnIndex("contents") );
            chapterArrayList.add(new Chapter(nStory_id, title, nChapter_id, contents));
            for (int i = 0; i < chapterArrayList.size(); i++){
                compareNumber = i + 1;
                if (getArguments().getInt(Commons.ARG_SECTION_NUMBER) == compareNumber) {

                    try {
                        Log.d("josjo", chapterArrayList.get(i).getTitle() + "");
                        sContent = Decrypt.Base64Decode(chapterArrayList.get(i).getContents());
                        JSONObject jsonObject = new JSONObject(sContent);
                        sIV = jsonObject.getString("iv");
                        sValue = jsonObject.getString("value");
                        String sContent1 = Decrypt.decrypt(Decrypt.key.getBytes(), sIV, sValue);
                        Log.d("josjo1", sContent1 + "");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtReadStory.setText(Html.fromHtml(sContent1, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtReadStory.setText(Html.fromHtml(sContent1));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void setEventScrollProgress() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
//                if (scrollY >= 100) {
////                    sharedPreferencesUtils.setDataSharePreferences_From_Chapter(chapter);
//                }
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


//                getActivity().finish();
                break;
            case R.id.relativeLayoutLayoutClick:
                eventVisibleViewSetting();
                break;
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
        Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, milliseconds);
    }

    public static ReadFragmentOffline newInstance(int sectionNumber) {
        ReadFragmentOffline fragment = new ReadFragmentOffline();
        Bundle args = new Bundle();
        args.putInt(Commons.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private void eventVisibleViewSetting() {
        visible_relativeLayoutLayoutClick = !visible_relativeLayoutLayoutClick;
        if (visible_relativeLayoutLayoutClick) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((ReadStoryActivityOffline) getActivity()).testToolBarId.setVisibility(View.VISIBLE);
            linearLayout_Visible.setVisibility(View.VISIBLE);
//            adView.setVisibility(View.GONE);
        } else {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((ReadStoryActivityOffline) getActivity()).testToolBarId.setVisibility(View.GONE);
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
            ((ReadStoryActivityOffline) getActivity()).testToolBarId.setVisibility(View.GONE);
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

    private void setControl(View view) {
        seekBar_ReadStory = view.findViewById(R.id.seekBar_ReadStoryActivity);
        sharedPreferencesUtils = new SharedPreferences_Utils(getActivity());
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
}
