/*
 * Copyright (c) 2016. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.wyhjc.musicplayer.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.wyhjc.musicplayer.model.Song;
import com.example.wyhjc.musicplayer.util.MusicUtil;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private static ArrayList<Song> localMusicPlaylist;
    private static MediaPlayer mMediaPlayer;
    private static int DURATION = 0;
    private int position = 0;
    private static boolean paused = false;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private Worker mWorker;

    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        localMusicPlaylist = MusicUtil.getSongsList();
        Song song = localMusicPlaylist.get(getPosition());;
        DURATION = (int) song.getDuration() / 1000;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mWorker != null) {
            mWorker.interrupt();
            mWorker = null;
        }
        paused = false;
        return super.onUnbind(intent);
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

    public void start(int position){
        Song song = localMusicPlaylist.get(position);
        DURATION = (int) song.getDuration() / 1000;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(this, Uri.parse(song.getUrl()));//资源解析,Mp3地址
            mMediaPlayer.prepare();//准备
            mMediaPlayer.start();//开始(播放)
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
}
