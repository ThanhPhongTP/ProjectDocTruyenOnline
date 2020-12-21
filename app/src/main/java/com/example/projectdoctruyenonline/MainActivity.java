package com.example.projectdoctruyenonline;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projectdoctruyenonline.fragments.AboutFragment;
import com.example.projectdoctruyenonline.fragments.CategoriesListFragment;
import com.example.projectdoctruyenonline.fragments.FragmentDiscover;
import com.example.projectdoctruyenonline.fragments.FragmentHome;
import com.example.projectdoctruyenonline.fragments.HistoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    FragmentHome f1 = new FragmentHome();
    CategoriesListFragment f2 = new CategoriesListFragment();
    FragmentDiscover f3 = new FragmentDiscover();
    AboutFragment f4 = new AboutFragment();
    Fragment f0 = f1;

    Boolean aIsActive = false;
    Boolean bIsActive = false;
    Boolean cIsActive = false;
    Boolean dIsActive = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_views);
        setFragment(new FragmentHome());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    private void setFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        //fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.detach(fragment);
//        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    //xem them tu fragmenthome
    public void setFrm() {
//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame_container, f2);
//        fragmentTransaction.commit();
//        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        if (!bIsActive) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f2).commit();
            bIsActive = true;
        } else
            getSupportFragmentManager().beginTransaction().hide(f0).show(f2).commit();
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        f0 = f2;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (f0 == f2)
                    aIsActive = true;
                if (!aIsActive) {
                    Log.d("pro", aIsActive + "");
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f1).commit();
                    aIsActive = true;
                } else {
                    getSupportFragmentManager().beginTransaction().hide(f0).show(f1).commit();
                    Log.d("pro1", aIsActive + "");
                }

                f0 = f1;
                break;
            case R.id.navigation_home:
                if (!bIsActive) {
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f2).commit();
                    bIsActive = true;
                } else
                    getSupportFragmentManager().beginTransaction().hide(f0).show(f2).commit();
                f0 = f2;
                break;
            case R.id.navigation_history:
                if (!cIsActive) {
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f3, "tag").commit();
                    cIsActive = true;
                } else {
                    getSupportFragmentManager().beginTransaction().hide(f0).show(f3).commit();
                    //fragment chỉ tạo 1 lần trong main(k reload) -> Reload từ fragment
                    FragmentDiscover fragment = (FragmentDiscover) getSupportFragmentManager().findFragmentByTag("tag");
                    fragment.addTabs();
                }
                f0 = f3;
                break;
            case R.id.navigation_about:
                if (!dIsActive) {
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f4).commit();
                    dIsActive = true;
                } else
                    getSupportFragmentManager().beginTransaction().hide(f0).show(f4).commit();
                f0 = f4;
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn một lần nữa để thoát ra", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}