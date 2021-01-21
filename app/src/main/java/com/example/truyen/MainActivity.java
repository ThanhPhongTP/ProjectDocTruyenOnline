    package com.example.truyen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.truyen.fragments.AboutFragment;
import com.example.truyen.fragments.CategoriesListFragment;
import com.example.truyen.fragments.FragmentDiscover;
import com.example.truyen.fragments.FragmentHome;
import com.example.truyen.fragments.FragmentNoInternet;
import com.example.truyen.service.BottomNavigationBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static BottomNavigationView bottomNavigationView;
    private static MainActivity instance;
    private FragmentTransaction fragmentTransaction;
    FragmentHome f1 = new FragmentHome();
    CategoriesListFragment f2 = new CategoriesListFragment();
    FragmentDiscover f3 = new FragmentDiscover();
    AboutFragment f4 = new AboutFragment();
    FragmentNoInternet f5 = new FragmentNoInternet();
    Fragment f0 = f1;

    Boolean aIsActive = false;
    Boolean bIsActive = false;
    Boolean cIsActive = false;
    Boolean dIsActive = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        bottomNavigationView = findViewById(R.id.nav_views);
//        flag=true;
//        setFragment(new FragmentHome());
        checkInternet(f1);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            setTheme(nID);
        }
    }

    private void getFragment(){
        if (bottomNavigationView.getSelectedItemId() == R.id.nav_home) {
            setFragment(f1);
            f0 = f1;
        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_home) {
            setFragment(f2);
            f0 = f2;
        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_history) {
            setFragment(f3);
            f0 = f3;
        } else if (bottomNavigationView.getSelectedItemId() == R.id.navigation_about) {
            setFragment(f4);
            f0 =f4;
        }
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

    public void setFrmDownLoad() {
        if (!cIsActive) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f3).commit();
            cIsActive = true;
        } else
            getSupportFragmentManager().beginTransaction().hide(f0).show(f3).commit();
        bottomNavigationView.setSelectedItemId(R.id.navigation_history);
        f0 = f3;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void setFragmentAbout(){
        if (!dIsActive) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, f4).commit();
            dIsActive = true;
        } else
            getSupportFragmentManager().beginTransaction().hide(f0).show(f4).commit();
        bottomNavigationView.setSelectedItemId(R.id.navigation_about);
        f0 = f4;
    }

    private void checkInternet(Fragment fragment){
        if (Commons.isConnectedtoInternet(this)) {
            setFragment(fragment);
        } else {
            setFragment(f5);
        }
    }

    boolean flag=false;
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                checkInternet(f1);
//                setFragment(f1);
                break;
            case R.id.navigation_home:
                checkInternet(f2);
//                setFragment(f2);
                break;
            case R.id.navigation_history:
//                checkInternet(f3);
                setFragment(f3);
                break;
            case R.id.navigation_about:
//                checkInternet(f4);
                setFragment(f4);
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