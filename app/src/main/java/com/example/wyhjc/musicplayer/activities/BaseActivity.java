package com.example.wyhjc.musicplayer.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wyhjc.musicplayer.music.PlayerService;

public class BaseActivity extends AppCompatActivity {
    protected PlayerService mService;
    private boolean mBound = false;  //是否绑定

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            //mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName classname) {
            mBound = false;
        }
    };

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
