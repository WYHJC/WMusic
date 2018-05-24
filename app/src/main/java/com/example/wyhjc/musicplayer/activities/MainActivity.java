package com.example.wyhjc.musicplayer.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.fragment.CloudFragment;
import com.example.wyhjc.musicplayer.fragment.MusicFragment;
import com.example.wyhjc.musicplayer.fragment.TimeFragment;
import com.example.wyhjc.musicplayer.viewPager.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;
    private ImageView mMusicImgView, mCloudImgView;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private TimeFragment mTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMusicImgView = (ImageView)findViewById(R.id.main_toolbar_music);
        mCloudImgView = (ImageView)findViewById(R.id.main_toolbar_cloud);

        initToolbar();
        initDrawer();
        initViewPager();
    }

    //初始化组件
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if(mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setHomeAsUpIndicator(R.drawable.main_toolbar_menu);
        }
    }

    private void initDrawer(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        mNavigationView = (NavigationView)mDrawerLayout.findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_view_menu_my_info:
                        break;
                    case R.id.nav_view_menu_equalizer:
                        break;
                    case R.id.nav_view_menu_dynamic_volume:
                        break;
                    case R.id.nav_view_menu_close:
                        if(mTimeFragment == null){
                            mTimeFragment = new TimeFragment();
                            mTimeFragment.show(getSupportFragmentManager(), "time");
                        }else{
                            mTimeFragment.getDialog().show();
                        }
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        Button settingsBtn = (Button)mNavigationView.findViewById(R.id.settings_btn);
        Button exitBtn = (Button)mNavigationView.findViewById(R.id.exit_btn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void initViewPager(){
        tabs.add(mMusicImgView);
        tabs.add(mCloudImgView);
        final CustomViewPager customViewPager = (CustomViewPager) findViewById(R.id.main_view_pager);
        final MusicFragment musicFragment = new MusicFragment();
        final CloudFragment cloudFragment = new CloudFragment();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(musicFragment);
        customViewPagerAdapter.addFragment(cloudFragment);
        customViewPager.setAdapter(customViewPagerAdapter);
        customViewPager.setCurrentItem(0);
        mMusicImgView.setSelected(true);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMusicImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(0);
            }
        });

        mCloudImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customViewPager.setCurrentItem(1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home: //Menu icon
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.main_toolbar_search:
                startActivity(new Intent(MainActivity.this, PlayActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    static class CustomViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    //切换页数的选择状态
    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }
}
