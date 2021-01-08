package com.example.truyen.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.truyen.R;
import com.example.truyen.adapter.ViewPagerAdapter;
import com.example.truyen.fragments.AboutFragment;
import com.example.truyen.fragments.HistoryFragment;
import com.example.truyen.fragments.CategoriesListFragment;
import com.example.truyen.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView bottomNavigationView;
    private CategoriesListFragment categoriesListFragment;
    private HistoryFragment historyFragment;
    private AboutFragment aboutFragment;
    private SearchFragment searchFragment;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);


    }
    private void setupViewPager (ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        categoriesListFragment = new CategoriesListFragment();
        historyFragment = new HistoryFragment();
        aboutFragment = new AboutFragment();
        adapter.addFragment(categoriesListFragment);
        adapter.addFragment(historyFragment);
        adapter.addFragment(aboutFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_history:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_about:
                viewPager.setCurrentItem(2);
                return true;
            default:
                return false;
        }
    }
}












