package com.example.wyhjc.musicplayer.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import com.example.wyhjc.musicplayer.service.PlayerService;
import com.example.wyhjc.musicplayer.util.HandlerUtil;
import com.example.wyhjc.musicplayer.view.SplashScreen;
import com.example.wyhjc.musicplayer.viewPager.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;
    private ImageView mMusicImgView, mCloudImgView;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private TimeFragment mTimeFragment;
    private SplashScreen splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        splashScreen = new SplashScreen(this);
        splashScreen.show(R.drawable.splash_screen,
                SplashScreen.SLIDE_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "e2dd9e79251342b7bc5ac1352375e194");

        requestPower(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE});
        startService(new Intent(this, PlayerService.class));

        mMusicImgView = (ImageView)findViewById(R.id.main_toolbar_music);
        mCloudImgView = (ImageView)findViewById(R.id.main_toolbar_cloud);

        initToolbar();
        initDrawer();
        initViewPager();

        HandlerUtil.getInstance(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.removeSplashScreen();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashScreen.removeSplashScreen();
    }

    private void requestPower(String[] permissions) {
        for(String permission: permissions){
            //判断是否已经赋予权限
            if (ContextCompat.checkSelfPermission(this,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                } else {
                    //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission}, 1);
                }
            }
        }
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
