package com.example.wyhjc.musicplayer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.wyhjc.musicplayer.dao.MusicSQLite;
import com.example.wyhjc.musicplayer.dao.PlaylistManager;
import com.example.wyhjc.musicplayer.manager.UserManager;
import com.example.wyhjc.musicplayer.model.Song;
import com.example.wyhjc.musicplayer.model.User;
import com.example.wyhjc.musicplayer.util.MusicUtil;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private ArrayList<Song> mPlaylist = new ArrayList<Song>();
    private static MediaPlayer mMediaPlayer;
    private static int DURATION = 0;
    private int position = 0;
    private static boolean paused = false;
    private ServiceBroadcast serviceBroadcast;
    private PlaylistManager mPlaylistManager;

    // Binder given to clients
    private IBinder mBinder = new LocalBinder();;
    private Worker mWorker;

    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wyhjc.service");
        serviceBroadcast = new ServiceBroadcast();
        registerReceiver(serviceBroadcast, intentFilter);
        mPlaylistManager = PlaylistManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(serviceBroadcast);
        mMediaPlayer.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        if (mWorker != null) {
//            mWorker.interrupt();
//            mWorker = null;
//        }
//        paused = false;
        return super.onUnbind(intent);
    }

    public ArrayList<Song> getPlaylist(){
        return mPlaylist;
    }

    public int getPosition(){
        return this.position;
    }

    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()){//判断当前歌曲不等于空,并且没有在播放的状态
            mMediaPlayer.start();
        }
        if (mWorker == null) {
            mWorker = new Worker();
            mWorker.start();
        } else {
            mWorker.doResume(false);
        }
    }

    public void stopPlay(){
        if (mWorker != null) {
            mWorker.interrupt();
            mWorker = null;
        }
        paused = false;
    }

    public void start(int position){
        //Song song = MusicUtil.getPlaySong(position);
        Song song = mPlaylist.get(position);
        this.position = position;
        DURATION = (int) song.getDuration() / 1000;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(this, Uri.parse(song.getUrl()));//资源解析,Mp3地址
            mMediaPlayer.prepare();//准备
            mMediaPlayer.start();//开始(播放);
            UserManager userManager = UserManager.getInstance();
            String id;
            if(userManager.isLogined())
                id = userManager.getUser().getId();
            else
                id = "";
            mPlaylistManager.addRecentPlay(song, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mWorker == null) {
            mWorker = new Worker();
            mWorker.start();
        } else {
            mWorker.doResume(true);
        }
    }

    public boolean isPlaying() {
        return mWorker != null && mWorker.isPlaying();
    }

    public void pause() {
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
        if (mWorker != null) {
            mWorker.doPause();
        }
    }

    public String getSongName(){
        return mPlaylist.get(getPosition()).getTitle();
    }

    public String getSongArtist(){
        return mPlaylist.get(getPosition()).getArtist();
    }

    public int getTime() {
        if (mWorker != null) {
            return mWorker.getTime();
        }
        return 0;
    }

    public int getDuration() {
        return DURATION;
    }

    private static class Worker extends Thread {
        private int time = 0;

        @Override
        public void run() {
            try {
                while (time < DURATION) {
                    sleep(1000);
                    if (!paused) {
                        time++;
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "Player unbounded");
            }
        }

        void doResume(boolean ifNew) {
            if(ifNew)
                time = 0;
            paused = false;
        }

        void doPause() {
            paused = true;
        }

        boolean isPlaying() {
            return !paused;
        }

        int getTime() {
            return time;
        }
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {

        public PlayerService getService() {
            // Return this instance of PlayerService so clients can call public methods
            return PlayerService.this;
        }
    }

    /**
     * 接收广播
     * 更新播放列表
     */
    private class ServiceBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getExtras().getInt("type");
            updatePlaylist(type);
        }

        /**
         * 更新播放列表
         * 0：本地音乐列表
         * 1：最近播放列表
         * 2：下载歌曲列表
         * @param type
         */
        private void updatePlaylist(int type){
            switch (type){
                case 0:
                    mPlaylist = MusicUtil.getSongsList();
                    break;
                case 1:
                    mPlaylist = mPlaylistManager.getRecentPlay();
                    break;
                case 2:
                    mPlaylist = mPlaylistManager.getDownload();
            }
            DURATION = (int)mPlaylist.get(0).getDuration()/1000;
        }
    }
}
