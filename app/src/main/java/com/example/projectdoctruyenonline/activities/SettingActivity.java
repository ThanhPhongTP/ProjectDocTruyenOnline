package com.example.projectdoctruyenonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.projectdoctruyenonline.Commons;
import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.SharedPreferences_Utils.SharedPreferences_Utils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener  {
    private Toolbar toolbar_Setting;
    private NestedScrollView nestedScrollView;
    private SharedPreferences_Utils sharedPreferencesUtils;


    private Button[] btn_position_change_background = new Button[3];
    private Button button_change_background;
    private int[] btn_id_change_background = {
            R.id.btntrang,
            R.id.btnden,
            R.id.btnvang};


    private Button[] btn_position_change_screen_time_out = new Button[4];
    private Button button_change_screen_time_out;
    private int[] btn_id_change_screen_time_out = {
            R.id.btnmanghinhsang30s,
            R.id.btnmanghinhsang1p,
            R.id.btnmanghinhsang10phut,
            R.id.btnmanghinhsang30phut
    };

    private Button[] btn_position_change_text_size = new Button[6];
    private Button button_change_text_size;
    private int[] btn_id_change_text_size = {
            R.id.btnSize12,
            R.id.btnSize14,
            R.id.btnSize16,
            R.id.btnSize18,
            R.id.btnSize20,
            R.id.btnSize22
    };
    private Button[] btn_position_change_font_style = new Button[5];
    private Button button_change_font_style;
    private int[] btn_id_change_font_style = {
            R.id.btnFontimesnewroman,
            R.id.btnFontserif,
            R.id.btnFontbookerly,
            R.id.btnFonthelvetica,
            R.id.btnFontlora
    };
    private Button[] btn_position_change_read_style = new Button[3];
    private Button button_change_read_style;
    private int[] btn_id_change_read_style = {
            R.id.btncuondoc,
            R.id.btnlattrang,
            R.id.btnkkethop,

    };
    private Button[] btn_position_change_line_height = new Button[6];
    private Button button_change_line_height;
    private int[] btn_id_change_line_height = {
            R.id.btnlineHeight_1,
            R.id.btnlineHeight_2,
            R.id.btnlineHeight_3,
            R.id.btnlineHeight_4,
            R.id.btnlineHeight_5,
            R.id.btnlineHeight_6,

    };
    private Button[] btn_position_change_auto_scroll = new Button[2];
    private Button button_change_auto_scroll;
    private int[] btn_id_change_auto_scroll = {
            R.id.btnautoscroll_No,
            R.id.btnautoscroll_Yes,


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        actionToolBar();
        sharedPreferencesUtils = new SharedPreferences_Utils(this);
        eventSeeBarSetting();

        initGroupButtonChangeBackground();
        getIdButtonChangeBackgroundClick();
        ///////////////////////////////////////////
        initGroupButtonChangeScreenTimeOut();
        getIdButtonChangeScreenTimeOutClick();
///////////////////////////////////////////////////
        initGroupButtonChangeTextSize();
        getIdButtonChangeTextSize();
        /////////////////////////
        initGroupButtonChangeFontStyle();
        getIdButtonChangeFontStyle();
        /////////////////////////

        initGroupButtonChangeReadStyle();
        getIdButtonChangeReadStyle();

        //////////////////
        initGroupButtonChangeLineHeight();
        getIdButtonChangeLineHeight();

        /////////////////////

        initGroupButtonChangeAutoScroll();
        getIdButtonChangeAutoScroll();

    }



    private void initGroupButtonChangeAutoScroll() {
        for(int i = 0; i < btn_position_change_auto_scroll.length; i++){
            btn_position_change_auto_scroll[i] = (Button) findViewById(btn_id_change_auto_scroll[i]);
            btn_position_change_auto_scroll[i].setOnClickListener(this);
        }
        button_change_auto_scroll = btn_position_change_auto_scroll[0];
    }


    private void initGroupButtonChangeLineHeight() {
        for(int i = 0; i < btn_position_change_line_height.length; i++){
            btn_position_change_line_height[i] = (Button) findViewById(btn_id_change_line_height[i]);
            btn_position_change_line_height[i].setOnClickListener(this);
        }
        button_change_line_height = btn_position_change_line_height[0];
    }


    private void initGroupButtonChangeReadStyle() {
        for(int i = 0; i < btn_position_change_read_style.length; i++){
            btn_position_change_read_style[i] = (Button) findViewById(btn_id_change_read_style[i]);
            btn_position_change_read_style[i].setOnClickListener(this);
        }
        button_change_read_style = btn_position_change_read_style[0];

    }


    private void initGroupButtonChangeFontStyle() {
        for(int i = 0; i < btn_position_change_font_style.length; i++){
            btn_position_change_font_style[i] = (Button) findViewById(btn_id_change_font_style[i]);
            btn_position_change_font_style[i].setOnClickListener(this);
        }
        button_change_font_style = btn_position_change_font_style[0];
    }

    private void initGroupButtonChangeTextSize() {
        for(int i = 0; i < btn_position_change_text_size.length; i++){
            btn_position_change_text_size[i] = (Button) findViewById(btn_id_change_text_size[i]);
            btn_position_change_text_size[i].setOnClickListener(this);
        }
        button_change_text_size = btn_position_change_text_size[0];
    }


    private void initGroupButtonChangeScreenTimeOut() {
        for(int i = 0; i < btn_position_change_screen_time_out.length; i++){
            btn_position_change_screen_time_out[i] = (Button) findViewById(btn_id_change_screen_time_out[i]);
            btn_position_change_screen_time_out[i].setOnClickListener(this);
        }
        button_change_screen_time_out = btn_position_change_screen_time_out[0];
    }

    private void initGroupButtonChangeBackground() {
        for(int i = 0; i < btn_position_change_background.length; i++){
            btn_position_change_background[i] = (Button) findViewById(btn_id_change_background[i]);
            btn_position_change_background[i].setOnClickListener(this);
        }
        button_change_background = btn_position_change_background[0];
    }
    private void getIdButtonChangeBackgroundClick() {
        int idButton = SharedPreferences_Utils.getButtonChangeColorBackgroundSetting();
        if (idButton  != 0){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonChangeBackground(button_change_background,btnColorF);
        }
    }
    private void getIdButtonChangeScreenTimeOutClick() {
        int idButton = SharedPreferences_Utils.getButtonChangeScreenTimeOut();
        if (idButton  != 0){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonChangeScreenTimeOut(button_change_screen_time_out,btnColorF);
        }
    }
    private void getIdButtonChangeTextSize() {
        int idButton = SharedPreferences_Utils.getButtonChangeTextSize();
        if (idButton  != 0){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonTextSize(button_change_text_size,btnColorF);
        }
    }
    private void getIdButtonChangeFontStyle() {
        int idButton = SharedPreferences_Utils.getButtonChangeFontStyle();
        if (idButton  != 0){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonFontStyle(button_change_font_style,btnColorF);
        }
    }
    private void getIdButtonChangeLineHeight() {
        int idButton = SharedPreferences_Utils.getButtonChangeLineHeight();
        if (idButton  != 60){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonLineHeight(button_change_line_height,btnColorF);
        }
    }
    private void getIdButtonChangeReadStyle() {
        int idButton = SharedPreferences_Utils.getButtonChangeReadStyle();
        if (idButton  != 0){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonReadStyle(button_change_read_style,btnColorF);
        }
    }
    private void getIdButtonChangeAutoScroll() {
        int idButton = SharedPreferences_Utils.getButtonChangeAutoScroll();
        if (idButton  != 0){
            Button btnColorF = (Button) findViewById(idButton);
            setFocusToGroupButtonAutoScroll(button_change_auto_scroll,btnColorF);
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btntrang:
                SharedPreferences_Utils.setColorBackgroundReadStory(getResources().getColor(R.color.colorWhite));
                SharedPreferences_Utils.setChangeTextColor(getResources().getColor(R.color.colorBlack));
                setFocusToGroupButtonChangeBackground(button_change_background, btn_position_change_background[0]);
                break;
            case R.id.btnden:
                SharedPreferences_Utils.setColorBackgroundReadStory(getResources().getColor(R.color.colorBlack));
                setFocusToGroupButtonChangeBackground(button_change_background, btn_position_change_background[1]);
                SharedPreferences_Utils.setChangeTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.btnvang:
                SharedPreferences_Utils.setColorBackgroundReadStory(getResources().getColor(R.color.colorYellowReadStory));
                SharedPreferences_Utils.setChangeTextColor(getResources().getColor(R.color.colorBlack));
                setFocusToGroupButtonChangeBackground(button_change_background, btn_position_change_background[2]);
                break;
            case R.id.btnmanghinhsang30s:
                SharedPreferences_Utils.setScreenTimeOutReadStory(Commons.THIRTY_SECOUND);
                setFocusToGroupButtonChangeScreenTimeOut(button_change_screen_time_out, btn_position_change_screen_time_out[0]);
                break;
            case R.id.btnmanghinhsang1p:
                SharedPreferences_Utils.setScreenTimeOutReadStory(Commons.ONE_MINUTE);
                setFocusToGroupButtonChangeScreenTimeOut(button_change_screen_time_out, btn_position_change_screen_time_out[1]);
                break;
            case R.id.btnmanghinhsang10phut:
                SharedPreferences_Utils.setScreenTimeOutReadStory(Commons.TEN_MINUTE);
                setFocusToGroupButtonChangeScreenTimeOut(button_change_screen_time_out, btn_position_change_screen_time_out[2]);
                break;
            case R.id.btnmanghinhsang30phut:
                SharedPreferences_Utils.setScreenTimeOutReadStory(Commons.THIRTY_MINUTE);
                setFocusToGroupButtonChangeScreenTimeOut(button_change_screen_time_out, btn_position_change_screen_time_out[3]);
                break;
            case R.id.btnSize12:
                SharedPreferences_Utils.setTextSizeReadStory(Commons.size_12);
                setFocusToGroupButtonTextSize(button_change_text_size, btn_position_change_text_size[0]);
                break;
            case R.id.btnSize14:
                SharedPreferences_Utils.setTextSizeReadStory(Commons.size_14);
                setFocusToGroupButtonTextSize(button_change_text_size, btn_position_change_text_size[1]);
                break;
            case R.id.btnSize16:
                SharedPreferences_Utils.setTextSizeReadStory(Commons.size_16);
                setFocusToGroupButtonTextSize(button_change_text_size, btn_position_change_text_size[2]);
                break;
            case R.id.btnSize18:
                SharedPreferences_Utils.setTextSizeReadStory(Commons.size_18);
                setFocusToGroupButtonTextSize(button_change_text_size, btn_position_change_text_size[3]);
                break;
            case R.id.btnSize20:
                SharedPreferences_Utils.setTextSizeReadStory(Commons.size_20);
                setFocusToGroupButtonTextSize(button_change_text_size, btn_position_change_text_size[4]);
                break;
            case R.id.btnSize22:
                SharedPreferences_Utils.setTextSizeReadStory(Commons.size_22);
                setFocusToGroupButtonTextSize(button_change_text_size, btn_position_change_text_size[5]);
                break;
            case R.id.btnFontimesnewroman:
                SharedPreferences_Utils.setFontReadStory(R.font.timesnewroman);
                setFocusToGroupButtonFontStyle(button_change_font_style, btn_position_change_font_style[0]);
                break;
            case R.id.btnFontserif:
                SharedPreferences_Utils.setFontReadStory(R.font.serif);
                setFocusToGroupButtonFontStyle(button_change_font_style, btn_position_change_font_style[1]);
                break;
            case R.id.btnFontbookerly:
                SharedPreferences_Utils.setFontReadStory(R.font.bookerly_regular);
                setFocusToGroupButtonFontStyle(button_change_font_style, btn_position_change_font_style[2]);
                break;
            case R.id.btnFonthelvetica:
                SharedPreferences_Utils.setFontReadStory(R.font.helvetica_world_regular);
                setFocusToGroupButtonFontStyle(button_change_font_style, btn_position_change_font_style[3]);
                break;
            case R.id.btnFontlora:
                SharedPreferences_Utils.setFontReadStory(R.font.lora_regular);
                setFocusToGroupButtonFontStyle(button_change_font_style, btn_position_change_font_style[4]);
                break;
            case R.id.btncuondoc:
                setFocusToGroupButtonReadStyle(button_change_read_style, btn_position_change_read_style[0]);
                break;
            case R.id.btnlattrang:
                setFocusToGroupButtonReadStyle(button_change_read_style, btn_position_change_read_style[1]);
                break;
            case R.id.btnkkethop:
                setFocusToGroupButtonReadStyle(button_change_read_style, btn_position_change_read_style[2]);
                break;
            case R.id.btnlineHeight_1:
                setFocusToGroupButtonLineHeight(button_change_line_height, btn_position_change_line_height[0]);
                SharedPreferences_Utils.setLineHeight(Commons.line_Height_1);
                break;
            case R.id.btnlineHeight_2:
                setFocusToGroupButtonLineHeight(button_change_line_height, btn_position_change_line_height[1]);
                SharedPreferences_Utils.setLineHeight(Commons.line_Height_2);
                break;
            case R.id.btnlineHeight_3:
                setFocusToGroupButtonLineHeight(button_change_line_height, btn_position_change_line_height[2]);
                SharedPreferences_Utils.setLineHeight(Commons.line_Height_3);
                break;
            case R.id.btnlineHeight_4:
                setFocusToGroupButtonLineHeight(button_change_line_height, btn_position_change_line_height[3]);
                SharedPreferences_Utils.setLineHeight(Commons.line_Height_4);
                break;
            case R.id.btnlineHeight_5:
                setFocusToGroupButtonLineHeight(button_change_line_height, btn_position_change_line_height[4]);
                SharedPreferences_Utils.setLineHeight(Commons.line_Height_5);
                break;
            case R.id.btnlineHeight_6:
                setFocusToGroupButtonLineHeight(button_change_line_height, btn_position_change_line_height[5]);
                SharedPreferences_Utils.setLineHeight(Commons.line_Height_6);
                break;
            case R.id.btnautoscroll_No:
                setFocusToGroupButtonAutoScroll(button_change_auto_scroll, btn_position_change_auto_scroll[0]);
                break;

            case R.id.btnautoscroll_Yes:
                setFocusToGroupButtonAutoScroll(button_change_auto_scroll, btn_position_change_auto_scroll[1]);
                break;
            case R.id.seekBarSettingAutoScroll:
                eventSeeBarSetting();
                break;
//            case R.id.btnAutoChangePage10:
//                Toast.makeText(this, "10", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btnAutoChangePage20:
//                Toast.makeText(this, "20", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btnAutoChangePage30:
//                Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btnAutoChangePage40:
//                Toast.makeText(this, "40", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btnAutoChangePage50:
//                Toast.makeText(this, "50", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btnAutoChangePage60:
//                Toast.makeText(this, "60", Toast.LENGTH_SHORT).show();
//                break;

        }
    }


    private void eventSeeBarSetting() {
        SeekBar seekBar = findViewById(R.id.seekBarSettingAutoScroll);
        seekBar.setProgress(4);
        if (!SharedPreferences_Utils.sharedPreferences.contains("see_bar_Key"))
            seekBar.setProgress(1);
        else
            seekBar.setProgress(sharedPreferencesUtils.getSeeBar());
//        seekBar.incrementProgressBy(1);
        seekBar.setMax(4);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            seekBar.setMin(1);
//        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 1;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekBarProgress = progress;
                Log.d("GGGGG",seekBarProgress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences_Utils.setSeeBar(seekBarProgress);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIdButtonChangeBackgroundClick();
        getIdButtonChangeScreenTimeOutClick();
        getIdButtonChangeTextSize();
        getIdButtonChangeFontStyle();
        getIdButtonChangeLineHeight();
    }
    private void setFocusToGroupButtonChangeBackground(Button btn_unfocus, Button btn_focus){
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeColorBackgroundSetting(btn_focus.getId());
        this.button_change_background = btn_focus;
    }

    private void setFocusToGroupButtonChangeScreenTimeOut(Button btn_unfocus, Button btn_focus){
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeScreenTimeOut(btn_focus.getId());
        this.button_change_screen_time_out = btn_focus;
    }

    private void setFocusToGroupButtonTextSize(Button btn_unfocus, Button btn_focus){
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeTextSize(btn_focus.getId());
        this.button_change_text_size = btn_focus;
    }
    private void setFocusToGroupButtonFontStyle(Button btn_unfocus, Button btn_focus) {
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeFontStyle(btn_focus.getId());
        this.button_change_font_style = btn_focus;

    }
    private void setFocusToGroupButtonReadStyle(Button btn_unfocus, Button btn_focus) {
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeReadStyle(btn_focus.getId());
        this.button_change_read_style = btn_focus;
    }
    private void setFocusToGroupButtonLineHeight(Button btn_unfocus, Button btn_focus) {
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeLineHeight(btn_focus.getId());
        this.button_change_line_height = btn_focus;

    }
    private void setFocusToGroupButtonAutoScroll( Button btn_unfocus, Button btn_focus) {
        setFocus(btn_unfocus,btn_focus);
        SharedPreferences_Utils.setButtonChangeAutoScroll(btn_focus.getId());
        this.button_change_auto_scroll = btn_focus;

    }
    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setBackgroundResource(R.drawable.btn_unfocus);
        btn_unfocus.setTextColor(getResources().getColor(R.color.colorBlack));

        btn_focus.setBackgroundResource(R.drawable.btn_focus);
        btn_focus.setTextColor(getResources().getColor(R.color.colorWhite));
    }
    private void init() {
        nestedScrollView = findViewById(R.id.nestedScrollViewSetting);
        findViewById(R.id.btntrang).setOnClickListener(this);
        findViewById(R.id.btnden).setOnClickListener(this);
        findViewById(R.id.btnvang).setOnClickListener(this);
        findViewById(R.id.btnSize12).setOnClickListener(this);
        findViewById(R.id.btnSize14).setOnClickListener(this);
        findViewById(R.id.btnSize16).setOnClickListener(this);
        findViewById(R.id.btnSize18).setOnClickListener(this);
        findViewById(R.id.btnSize20).setOnClickListener(this);
        findViewById(R.id.btnSize22).setOnClickListener(this);
        findViewById(R.id.btnFontimesnewroman).setOnClickListener(this);
        findViewById(R.id.btnFontserif).setOnClickListener(this);
        findViewById(R.id.btnFontbookerly).setOnClickListener(this);
        findViewById(R.id.btnFonthelvetica).setOnClickListener(this);
        findViewById(R.id.btnFontlora).setOnClickListener(this);
        findViewById(R.id.btnmanghinhsang30s).setOnClickListener(this);
        findViewById(R.id.btnmanghinhsang1p).setOnClickListener(this);
        findViewById(R.id.btnmanghinhsang10phut).setOnClickListener(this);
        findViewById(R.id.btnmanghinhsang30phut).setOnClickListener(this);
        findViewById(R.id.btnautoscroll_Yes).setOnClickListener(this);
        findViewById(R.id.btnautoscroll_No).setOnClickListener(this);
        findViewById(R.id.seekBarSettingAutoScroll).setOnClickListener(this);

    }

    private void actionToolBar() {
        toolbar_Setting = findViewById(R.id.toolbar_Setting);
        setSupportActionBar(toolbar_Setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_Setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this,ReadStoryActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}