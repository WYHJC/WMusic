package com.example.wyhjc.musicplayer.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.activities.PlayActivity;
import com.example.wyhjc.musicplayer.dao.PlaylistManager;
import com.example.wyhjc.musicplayer.model.MusicFragmentItem;
import com.example.wyhjc.musicplayer.model.Playlist;
import com.example.wyhjc.musicplayer.util.MusicUtil;

import java.util.ArrayList;

public class MusicFragmentAdapter extends RecyclerView.Adapter<MusicFragmentAdapter.ItemHolder>{

    private Context mContext;
    private boolean createdExpand = true;
    private boolean collectExpand = true;
    private ArrayList mItemResults = new ArrayList<>();
    private ArrayList<Playlist> mCreatePlaylists = new ArrayList<Playlist>();
    private ArrayList<Playlist> mCollectPlaylists = new ArrayList<Playlist>();
    private PlaylistManager mPlaylistManager = null;

    public MusicFragmentAdapter(Context context){
        this.mContext = context;
        mPlaylistManager = PlaylistManager.getInstance(mContext);
    }

    //刷新列表，包括总列表，创建的歌单列表，收藏的歌单列表
    public void updateResults(ArrayList itemResults, ArrayList<Playlist> createPlaylists, ArrayList<Playlist> collectPlaylists){
        this.mItemResults = itemResults;
        this.mCreatePlaylists = createPlaylists;
        this.mCollectPlaylists = collectPlaylists;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        switch (viewType){
            case 0:
                View view0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_music_item, parent, false);
                ItemHolder holder0 = new ItemHolder(view0);
                return holder0;
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_playlist_item, parent, false);
                ItemHolder holder1 = new ItemHolder(view1);
                return holder1;
            case 2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                ItemHolder holder2 = new ItemHolder(view2);
                return holder2;
            case 3:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                ItemHolder holder3 = new ItemHolder(view3);
                return holder3;
        }
        return null;
    }

    /**
     * case 0:菜单列表
     * case 1:创建的歌单列表、收藏的歌单列表
     * case 2:创建的歌单导航栏
     * case 3:收藏的歌单导航栏
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                MusicFragmentItem item = (MusicFragmentItem) mItemResults.get(position);
                holder.musicItemImage.setImageResource(item.getImageID());
                holder.musicItemtitle.setText(item.getTitle());
                holder.musicItemCount.setText("(" + item.getCount() + ")");
                setOnListener(holder, position);
                break;
            case 1:
                Playlist playlist = (Playlist) mItemResults.get(position);
                if (createdExpand && playlist.getAuthor().equals("local")) {
                    if (playlist.getImageID() != -1){
                        Glide.with(mContext).load(playlist.getImageID()).into(holder.playlistItemImage);
                    }
                    holder.playlistItemTitle.setText(playlist.getName());
                    holder.playlistItemCount.setText(playlist.getCount() + " songs");
                }
                if (collectExpand && !playlist.getAuthor().equals("local")) {
                    if (playlist.getImageID() != -1){
                        Glide.with(mContext).load(playlist.getImageID()).into(holder.playlistItemImage);
                    }
                    holder.playlistItemTitle.setText(playlist.getName());
                    holder.playlistItemCount.setText(playlist.getCount() + " songs");
                }
                break;
            case 2:
                holder.expandImage.setImageResource(R.drawable.ic_expand_right);
                holder.expandTitle.setText("Create playlists(" + mCreatePlaylists.size() + ")");
                break;
            case 3:
                holder.expandImage.setImageResource(R.drawable.ic_expand_right);
                holder.expandTitle.setText("Collect playlists(" + mCollectPlaylists.size() + ")");
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 0) {
            return -1;
        }
        if (mItemResults.get(position) instanceof MusicFragmentItem)
            return 0;
        if (mItemResults.get(position) instanceof Playlist) {
            return 1;
        }
        if (mItemResults.get(position) instanceof String) {
            if (((String) mItemResults.get(position)).equals("collect"))
                return 3;
        }
        return 2;
    }

    @Override
    public int getItemCount() {
        if(mItemResults == null){
            return 0;
        }
        if (!createdExpand && mCreatePlaylists != null) {
            mItemResults.removeAll(mCreatePlaylists);
        }
        if (!collectExpand) {
            mItemResults.removeAll(mCollectPlaylists);
        }
        return mItemResults.size();
    }

    private void setOnListener(ItemHolder itemHolder, final int position) {
        switch (position) {
            case 0:
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.setAction("com.wyhjc.service");
                                intent.putExtra("type", 0);
                                mContext.getApplicationContext().sendBroadcast(intent);

                                intent = new Intent(mContext, PlayActivity.class);
                                intent.putExtra("type", 0);
                                intent.putExtra("name", "Local music");
                                intent.putExtra("count", MusicUtil.getLocalMusicCount());
                                mContext.startActivity(intent);
                            }
                        }, 60);

                    }
                });
                break;
            case 1:
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.setAction("com.wyhjc.service");
                                intent.putExtra("type", 1);
                                mContext.getApplicationContext().sendBroadcast(intent);

                                intent = new Intent(mContext, PlayActivity.class);
                                intent.putExtra("type", 1);
                                intent.putExtra("name", "Recent play");
                                intent.putExtra("count", mPlaylistManager.getRecentPlaySize());
                                mContext.startActivity(intent);
                            }
                        }, 60);
                    }
                });
                break;
            case 2:
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(mContext, PlayActivity.class);
                                mContext.startActivity(intent);
                            }
                        }, 60);

                    }
                });
                break;
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView musicItemImage, playlistItemImage, playlistItemMore, expandImage, expandMenu;
        protected TextView musicItemtitle, musicItemCount, playlistItemTitle, playlistItemCount, expandTitle;

        public ItemHolder(View view) {
            super(view);
            this.musicItemImage = (ImageView) view.findViewById(R.id.fragment_music_item_image);
            this.musicItemtitle = (TextView) view.findViewById(R.id.fragment_music_item_title);
            this.musicItemCount = (TextView) view.findViewById(R.id.fragment_music_item_count);

            this.playlistItemImage = (ImageView) view.findViewById(R.id.music_playlist_item_image);
            this.playlistItemTitle = (TextView) view.findViewById(R.id.music_playlist_item_title);
            this.playlistItemCount = (TextView) view.findViewById(R.id.music_playlist_item_count);
            this.playlistItemMore = (ImageView) view.findViewById(R.id.music_playlist_item_more);

            this.expandImage = (ImageView) view.findViewById(R.id.expand_img);
            this.expandTitle = (TextView) view.findViewById(R.id.expand_title);
            this.expandMenu = (ImageView) view.findViewById(R.id.expand_menu);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ObjectAnimator anim = null;
            anim = ObjectAnimator.ofFloat(expandImage, "rotation", 90, 0);
            anim.setDuration(100);
            anim.setRepeatCount(0);
            anim.setInterpolator(new LinearInterpolator());
            switch (getItemViewType()) {
                case 2:
                    if (createdExpand) {
                        mItemResults.removeAll(mCreatePlaylists);
                        updateResults(mItemResults, mCreatePlaylists, mCollectPlaylists);
                        notifyItemRangeRemoved(5, mCreatePlaylists.size());
                        anim.start();

                        createdExpand = false;
                    } else {
                        mItemResults.removeAll(mCollectPlaylists);
                        mItemResults.remove("collect");
                        mItemResults.addAll(mCreatePlaylists);
                        mItemResults.add("collect");
                        mItemResults.addAll(mCollectPlaylists);
                        updateResults(mItemResults, mCreatePlaylists, mCollectPlaylists);
                        notifyItemRangeInserted(5, mCreatePlaylists.size());
                        anim.reverse();
                        createdExpand = true;
                    }
                    break;
                case 3:
                    if (collectExpand) {
                        mItemResults.removeAll(mCollectPlaylists);
                        updateResults(mItemResults, mCreatePlaylists, mCollectPlaylists);
                        int len = mCreatePlaylists.size();
                        notifyItemRangeRemoved(6 + len, mCollectPlaylists.size());
                        anim.start();

                        collectExpand = false;
                    } else {
                        mItemResults.addAll(mCollectPlaylists);
                        updateResults(mItemResults, mCreatePlaylists, mCollectPlaylists);
                        int len = mCreatePlaylists.size();
                        notifyItemRangeInserted(6 + len, mCollectPlaylists.size());
                        anim.reverse();
                        collectExpand = true;
                    }
                    break;
            }
        }
    }
}
