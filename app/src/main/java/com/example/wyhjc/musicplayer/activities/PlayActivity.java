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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.adapter.RecyclerItemClickListener;
import com.example.wyhjc.musicplayer.adapter.SongAdapter;
import com.example.wyhjc.musicplayer.util.MusicUtil;

public class PlayActivity extends PlayerActivity {

    private View mCoverView;
    private View mTitleView;
    private View mTimeView;
    private View mDurationView;
    private View mProgressView;
    private View mFabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);

        //
        mCoverView = findViewById(R.id.cover);
        mTitleView = findViewById(R.id.title);
        mTimeView = findViewById(R.id.time);
        mDurationView = findViewById(R.id.duration);
        mProgressView = findViewById(R.id.progress);
        mFabView = findViewById(R.id.fab);

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
                                new Pair<>(mTimeView, ViewCompat.getTransitionName(mTimeView)),
                                new Pair<>(mDurationView, ViewCompat.getTransitionName(mDurationView)),
                                new Pair<>(mProgressView, ViewCompat.getTransitionName(mProgressView)),
                                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
                        ActivityCompat.startActivity(PlayActivity.this, new Intent(PlayActivity.this, DetailActivity.class), options.toBundle());
                    }

                    @Override
                    public void onLongClick(View view, int posotion) {

                    }
        }));
    }

    public void onFabClick(View view) {
        //noinspection unchecked
        mService.play();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(PlayActivity.this,
                new Pair<>(mCoverView, ViewCompat.getTransitionName(mCoverView)),
                new Pair<>(mTitleView, ViewCompat.getTransitionName(mTitleView)),
                new Pair<>(mTimeView, ViewCompat.getTransitionName(mTimeView)),
                new Pair<>(mDurationView, ViewCompat.getTransitionName(mDurationView)),
                new Pair<>(mProgressView, ViewCompat.getTransitionName(mProgressView)),
                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
        ActivityCompat.startActivity(PlayActivity.this, new Intent(PlayActivity.this, DetailActivity.class), options.toBundle());
    }
}
