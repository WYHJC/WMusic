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

package com.example.wyhjc.musicplayer.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.adapter.RecyclerItemClickListener;
import com.example.wyhjc.musicplayer.adapter.SongAdapter;
import com.example.wyhjc.musicplayer.util.MusicUtil;
import com.example.wyhjc.musicplayer.view.ProgressView;

public class PlayActivity extends BaseActivity {

    private View mCoverView;
    private View mTitleView;
    private TextView mTimeView;
    private TextView mDurationView;
    private ProgressView mProgressView;
    private View mFabView;
    private final Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int position = mService.getTime();
            final int duration = mService.getDuration();
            onUpdateProgress(position, duration);
            sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS);
        }
    };

    private PlayBroadcast playBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);
        bindPlayerService();
        //
        mCoverView = findViewById(R.id.cover);
        mTitleView = findViewById(R.id.title);
        mTimeView = (TextView) findViewById(R.id.time);
        mDurationView = (TextView) findViewById(R.id.duration);
        mProgressView = (ProgressView) findViewById(R.id.progress);
        mFabView = findViewById(R.id.fab);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wyhjc.play");
        playBroadcast = new PlayBroadcast();
        registerReceiver(playBroadcast, intentFilter);

        //mService.setDURATIONView();

        Intent intent = new Intent();
        intent.setAction("com.wyhjc.service");
        sendBroadcast(intent);

        intent = new Intent();
        intent.setAction("com.wyhjc.play");
        sendBroadcast(intent);

        // Set the recycler adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tracks);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SongAdapter songAdapter = new SongAdapter(MusicUtil.getSongsList());
        recyclerView.setAdapter(songAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mService.start(position);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(PlayActivity.this,
                                new Pair<>(mCoverView, ViewCompat.getTransitionName(mCoverView)),
                                new Pair<>(mTitleView, ViewCompat.getTransitionName(mTitleView)),
                                new Pair<>((View)mTimeView, ViewCompat.getTransitionName(mTimeView)),
                                new Pair<>((View)mDurationView, ViewCompat.getTransitionName(mDurationView)),
                                new Pair<>((View)mProgressView, ViewCompat.getTransitionName(mProgressView)),
                                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
                        ActivityCompat.startActivity(PlayActivity.this, new Intent(PlayActivity.this, DetailActivity.class), options.toBundle());
                    }

                    @Override
                    public void onLongClick(View view, int posotion) {

                    }
        }));
    }

    private void onUpdateProgress(int position, int duration) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.stopPlay();
        unbindPlayerService();
        mUpdateProgressHandler.removeMessages(0);
        unregisterReceiver(playBroadcast);
        //Toast.makeText(this, "Play onDestroy", Toast.LENGTH_SHORT).show();
    }

    public void onFabClick(View view) {
        //noinspection unchecked
        mService.play();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(PlayActivity.this,
                new Pair<>(mCoverView, ViewCompat.getTransitionName(mCoverView)),
                new Pair<>(mTitleView, ViewCompat.getTransitionName(mTitleView)),
                new Pair<>((View) mTimeView, ViewCompat.getTransitionName(mTimeView)),
                new Pair<>((View) mDurationView, ViewCompat.getTransitionName(mDurationView)),
                new Pair<>((View) mProgressView, ViewCompat.getTransitionName(mProgressView)),
                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
        ActivityCompat.startActivity(PlayActivity.this, new Intent(PlayActivity.this, DetailActivity.class), options.toBundle());
    }

    private class PlayBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mUpdateProgressHandler.sendEmptyMessage(0);
        }
    }
}
