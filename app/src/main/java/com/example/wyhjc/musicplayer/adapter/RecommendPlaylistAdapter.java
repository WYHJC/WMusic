package com.example.wyhjc.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.model.Playlist;

import java.util.List;

public class RecommendPlaylistAdapter extends RecyclerView.Adapter<RecommendPlaylistAdapter.ViewHolder> {

    private Context mContext;
    private List<Playlist> mPlaylists;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            imageView = (ImageView)view.findViewById(R.id.recommend_playlist_item_image);
            textView = (TextView)view.findViewById(R.id.recommend_playlist_item_text);
        }
    }

    public RecommendPlaylistAdapter(List<Playlist> playlists){
        mPlaylists = playlists;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommend_playlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mPlaylists.get(position);
        Glide.with(mContext).load(playlist.getImageID()).into(holder.imageView);
        holder.textView.setHint(playlist.getName());
    }
}
