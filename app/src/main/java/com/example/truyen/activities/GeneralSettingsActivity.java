package com.example.truyen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.truyen.MainActivity;
import com.example.truyen.R;
import com.example.truyen.fragments.AboutFragment;

import java.util.Base64;

public class GeneralSettingsActivity extends AppCompatActivity {
    private Switch aSwitch;
    private Toolbar general_Setting;
    SharedPreferences sharedPreferences;
    private String sSwitch = "SWITCH_INTERFACE";
    private String sTheme = "THEME";
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkThemeFromSharedPreferences();
        checkTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        actionToolBar();
        setControl();
        setEvent();
//        checkSwitch();
        checkSwitchFromSharedPreferences();
        if (aSwitch.isChecked())
            setTheme(R.style.ProjectDocTruyenOnline_Dark);
        else
            setTheme(R.style.ProjectDocTruyenOnline_Light);
    }

    private void checkThemeFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(sTheme)) {
            int nID = sharedPreferences.getInt(sTheme, R.style.ProjectDocTruyenOnline_Light);
            setTheme(nID);
        }
    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
//            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
            setTheme(R.style.ProjectDocTruyenOnline_Light);
            editor.putInt(sTheme, R.style.ProjectDocTruyenOnline_Light);
        }
        else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.ProjectDocTruyenOnline_Dark);
            editor.putInt(sTheme,R.style.ProjectDocTruyenOnline_Dark);
        }



        editor.apply();
    }

    private void checkSwitchFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        aSwitch.setChecked(sharedPreferences.getBoolean(sSwitch, false));
    }

    private void checkSwitch() {
        aSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void setEvent() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    editor.putBoolean(sSwitch, true);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    editor.putBoolean(sSwitch, false);
//                }
//                editor.apply();
//                checkTheme();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//        });


        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aSwitch.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean(sSwitch, false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean(sSwitch, true);
                }
                editor.apply();
                checkTheme();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

                Log.d("kokokokoko", AppCompatDelegate.getDefaultNightMode() + "");
            }
        });
    }

    private void actionToolBar() {
        general_Setting = findViewById(R.id.general_Setting);
        setSupportActionBar(general_Setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        general_Setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                MainActivity.getInstance().setFragmentAbout();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        super.onBackPressed();
    }

    private void setControl() {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        aSwitch = findViewById(R.id.swTheme);
        sharedPreferences = getSharedPreferences("MyPrefsSetting1111", Context.MODE_PRIVATE);
//        btnSave = findViewById(R.id.save);
    }
}