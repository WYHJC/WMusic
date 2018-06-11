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
import android.widget.TextView;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.adapter.RecyclerItemClickListener;
import com.example.wyhjc.musicplayer.adapter.SongAdapter;
import com.example.wyhjc.musicplayer.dao.PlaylistManager;
import com.example.wyhjc.musicplayer.util.MusicUtil;
import com.example.wyhjc.musicplayer.view.ProgressView;

public class PlayActivity extends BaseActivity {

    private View mCoverView;
    private View mTitleView;
    private TextView mTimeView;
    private TextView mDurationView;
    private ProgressView mProgressView;
    private View mFabView;
    private PlaylistManager mPlaylistManager;

    private TextView mNameView;
    private TextView mCounterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);
        bindPlayerService();
        initView();
    }

    private void initView(){
        mCoverView = findViewById(R.id.cover);
        mTitleView = findViewById(R.id.title);
        mTimeView = (TextView) findViewById(R.id.time);
        mDurationView = (TextView) findViewById(R.id.duration);
        mProgressView = (ProgressView) findViewById(R.id.progress);
        mFabView = findViewById(R.id.fab);
        mNameView = (TextView)findViewById(R.id.name);
        mCounterView = (TextView)findViewById(R.id.counter);
        mNameView.setText(getIntent().getExtras().getString("name"));
        mCounterView.setText(String.valueOf(getIntent().getExtras().getInt("count")) + " songs");

                // Set the recycler adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tracks);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SongAdapter songAdapter = null;
        mPlaylistManager = PlaylistManager.getInstance(this);
        int type = getIntent().getExtras().getInt("type");
        switch (type){
            case 0:
                songAdapter = new SongAdapter(MusicUtil.getSongsList());
                break;
            case 1:
                songAdapter = new SongAdapter(mPlaylistManager.getRecentPlay());
                break;
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.stopPlay();
        unbindPlayerService();
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
}
