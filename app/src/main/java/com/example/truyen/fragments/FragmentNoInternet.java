package com.example.truyen.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.truyen.Commons;
import com.example.truyen.MainActivity;
import com.example.truyen.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FragmentNoInternet extends Fragment {

    ImageView img_NoInternet;
    Button btn_Offline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_internet, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setControl(View view){
        img_NoInternet = view.findViewById(R.id.img_NoInternet);
        btn_Offline = view.findViewById(R.id.btn_offline);
    }

    private void setEvent(){
        btn_Offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).setFrmDownLoad();
            }
        });
        img_NoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



//    private void CheckInternet() {
//        if (Commons.isConnectedtoInternet(getActivity())) {
//
//        } else {
//            Commons.showDialogError(getActivity());
//        }
//    }

}