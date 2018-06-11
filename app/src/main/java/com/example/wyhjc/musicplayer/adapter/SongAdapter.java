package com.example.wyhjc.musicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wyhjc.musicplayer.R;
import com.example.wyhjc.musicplayer.model.Song;
import com.example.wyhjc.musicplayer.util.MusicUtil;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private ArrayList<Song> mSongsList;
    private Context mContext;

    public SongAdapter(ArrayList<Song> songs){
        mSongsList = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.content_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = mSongsList.get(position);
        Glide.with(mContext).load(MusicUtil.getAlbumArt(mContext, song.getAlbumId())).into(holder.cover);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        holder.duration.setText(MusicUtil.formatTime(song.getDuration()));
    }

    @Override
    public int getItemCount() {
        if(mSongsList == null)
            return 0;
        return mSongsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title, artist, duration;

        public ViewHolder(View view){
            super(view);
            cover = (ImageView)view.findViewById(R.id.cover);
            title = (TextView)view.findViewById(R.id.title);
            artist = (TextView)view.findViewById(R.id.artist);
            duration = (TextView)view.findViewById(R.id.duration);
        }
    }
}
