package com.example.wyhjc.musicplayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.adapter.RecommendPlaylistAdapter;
import com.example.wyhjc.musicplayer.model.Playlist;
import com.example.wyhjc.musicplayer.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class CloudFragment extends Fragment {

    private View view;

    public CloudFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cloud, container, false);
        initBanner();
        initRecyclerView();
        return view;
    }

    //初始化轮播图
    private void initBanner() {
        List<String> titles = new ArrayList<String>();
        titles.add("Advertisement");
        titles.add("Advertisement");
        titles.add("Advertisement");
        //Integer[] images={R.mipmap.a,R.mipmap.b,R.mipmap.c};
        List<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.banner_img1);
        images.add(R.drawable.banner_img2);
        images.add(R.drawable.banner_img3);
        Banner banner = (Banner) view.findViewById(R.id.cloud_banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setDelayTime(3000);
        banner.setBannerTitles(titles);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();
    }

    //初始化歌单列表
    private void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.cloud_recycler_view);
        List<Playlist> playlists = new ArrayList<Playlist>();
        for(int i = 0; i < 3; i++){
            playlists.add(new Playlist(0 + i, R.drawable.playlist_image1, "唯美纯音｜一个小小的青春故事。", 100, "hjc"));
            playlists.add(new Playlist(1 + i, R.drawable.playlist_image2, "100首工作看书刷题纯音乐", 110,"hjc"));
            playlists.add(new Playlist(2 + i, R.drawable.playlist_image3, "那些你熟悉却又不知道名字的轻音乐", 120, "hjc"));
        }

        //设置布局管理器，显示三列
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        RecommendPlaylistAdapter adapter = new RecommendPlaylistAdapter(playlists);
        recyclerView.setAdapter(adapter);
    }
}
