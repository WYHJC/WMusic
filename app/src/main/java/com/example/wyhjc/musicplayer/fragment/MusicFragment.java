package com.example.wyhjc.musicplayer.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.adapter.MusicFragmentAdapter;
import com.example.wyhjc.musicplayer.dao.PlaylistManager;
import com.example.wyhjc.musicplayer.manager.UserManager;
import com.example.wyhjc.musicplayer.model.MusicFragmentItem;
import com.example.wyhjc.musicplayer.model.Playlist;
import com.example.wyhjc.musicplayer.util.MusicUtil;

import java.util.ArrayList;

public class MusicFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MusicFragmentAdapter mAdapter;
    private Context mContext;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_music, container, false);
        initView();
        return view;
    }

    //初始化视图
    private void initView(){
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.music_swipe_refresh);
        //设置刷新布局的提示器颜色
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadAdapter();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView = (RecyclerView)view.findViewById(R.id.music_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        //设置recycleview的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new MusicFragmentAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        reloadAdapter();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    //初始化列表数据
    private void initAdapter(){
        ArrayList results = new ArrayList();
        ArrayList<MusicFragmentItem> items = new ArrayList<MusicFragmentItem>();
        ArrayList<Playlist> createPlaylists = new ArrayList<Playlist>();
        ArrayList<Playlist> collectPlaylists = new ArrayList<Playlist>();
        MusicUtil.initLocalAllPlaylists(mContext);
        PlaylistManager playlistManager = PlaylistManager.getInstance(mContext);
        UserManager userManager = UserManager.getInstance();
        if(userManager.isLogined()){
            playlistManager.queryRecentPlay(userManager.getUser().getId());
            playlistManager.queryDownload(userManager.getUser().getId());
        }
        else{
            playlistManager.queryRecentPlay("");
            playlistManager.queryDownload("");
        }

        items.add(new MusicFragmentItem(R.mipmap.music_fragment_local_music, "Local music", MusicUtil.getLocalMusicCount()));
        items.add(new MusicFragmentItem(R.mipmap.music_fragment_recent_play, "Recent play", playlistManager.getRecentPlaySize()));
        items.add(new MusicFragmentItem(R.mipmap.music_fragment_download, "Download", playlistManager.getDownloadSize()));
        results.addAll(items);

        createPlaylists.add(new Playlist(0, R.drawable.playlist_image1, "My favorite music", 2, "hjc"));
        createPlaylists.add(new Playlist(0, R.drawable.playlist_image2, "Relaxing music", 10, "hjc"));
        results.add("create");
        results.addAll(createPlaylists);

        collectPlaylists.add(new Playlist(0, R.drawable.playlist_image3, "The piano", 129, "hjc"));
        if (collectPlaylists.size() != 0) {
            results.add("collect");
            results.addAll(collectPlaylists);
        }

        if(mAdapter == null){
            mAdapter = new MusicFragmentAdapter(mContext);
        }
        mAdapter.updateResults(results, createPlaylists, collectPlaylists);
    }

    //异步刷新列表
    public void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                initAdapter();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (mContext == null)
                    return;
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }.execute();
    }
}
