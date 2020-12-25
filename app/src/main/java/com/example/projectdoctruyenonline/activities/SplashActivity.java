package com.example.projectdoctruyenonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projectdoctruyenonline.MainActivity;
import com.example.projectdoctruyenonline.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setEvent();
    }


    private void setEvent() {
        Thread bamgio = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        bamgio.start();
    }


}