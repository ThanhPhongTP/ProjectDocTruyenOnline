package com.example.truyen.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.truyen.R;
import com.example.truyen.service.DatabaseSQLite;


import static com.example.truyen.service.DatabaseSQLite.database;


public class Fragment_Detail_offline extends Fragment {
    private TextView txtContentStory;
    int nID;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setControl(view);
        ShowData();
        return view;
    }

    private void getDataIntent() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            nID = intent.getIntExtra("NEWSTORY", 0);
            Log.d("NEWSTORY", nID + "");
        }
    }

    private void ShowData(){
        getDataIntent();
        database = new DatabaseSQLite(getActivity(),"Story.sqlite", null, 1);
        Cursor cursor = database.GetData("SELECT * FROM Stories WHERE story_id = " + nID);
        while (cursor.moveToNext()) {
            String sDescription = cursor.getString(cursor.getColumnIndex("description") );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtContentStory.setText(Html.fromHtml(sDescription, Html.FROM_HTML_MODE_COMPACT));
            } else {
                txtContentStory.setText(Html.fromHtml(sDescription));
            }
        }
        cursor.close();
    }


    private void setControl(View view) {
        txtContentStory = view.findViewById(R.id.txtContentStory);
    }

}
