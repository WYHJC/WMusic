package com.example.wyhjc.musicplayer.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.service.PlayerService;
import com.example.wyhjc.musicplayer.view.ProgressView;

public class BaseActivity extends AppCompatActivity {
    protected PlayerService mService;
    private boolean mBound = false;  //是否绑定
    private TextView mTrackTitleName;
    private TextView mTrackTitleArtist;
    private TextView mTimeView;
    private TextView mDurationView;
    private ProgressView mProgressView;
    private final Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int position = mService.getTime();
            final int duration = mService.getDuration();
            final String title = mService.getSongName();
            final String artist = mService.getSongArtist();
            onUpdateProgress(title, artist, position, duration);
            sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS);
        }
    };

    private void onUpdateProgress(String title, String artist, int position, int duration) {
        if (mTrackTitleName != null){
            mTrackTitleName.setText(title);
        }
        if (mTrackTitleArtist != null){
            mTrackTitleArtist.setText(artist);
        }
        if (mTimeView != null) {
            mTimeView.setText(DateUtils.formatElapsedTime(position));
        }
        if (mDurationView != null) {
            mDurationView.setText(DateUtils.formatElapsedTime(duration));
        }
        if (mProgressView != null) {
            mProgressView.setProgress(position);
        }
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            //mBound = true;
            displaySongView();
        }

        @Override
        public void onServiceDisconnected(ComponentName classname) {
            mBound = false;
            removeSongView();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mTrackTitleName = (TextView)findViewById(R.id.track_title_name);
        mTrackTitleArtist = (TextView)findViewById(R.id.track_title_artist);
        mTimeView = (TextView) findViewById(R.id.time);
        mDurationView = (TextView) findViewById(R.id.duration);
        mProgressView = (ProgressView) findViewById(R.id.progress);
    }

    /**
     * 设置播放列表界面和播放界面的歌曲显示信息
     */
    private void displaySongView() {
        mUpdateProgressHandler.sendEmptyMessage(0);
    }

    /**
     * 去除播放列表界面和播放界面的歌曲显示信息
     */
    private void removeSongView() {
        mUpdateProgressHandler.removeMessages(0);
    }

    //绑定服务
    public void bindPlayerService(){
        if(!mBound) {
            Intent intent = new Intent(this, PlayerService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            mBound = true;
        }
    }
    //解除绑定服务
    public void unbindPlayerService(){
        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
