package com.example.wyhjc.musicplayer.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.wyhjc.musicplayer.model.Song;

import java.util.ArrayList;

/**
 * service层，数据库操纵函数接口集成类
 */
public class PlaylistManager {
    private static PlaylistManager instance = null;

    private MusicSQLite mMusicSQLite = null;
    private RecentPlay mRecentPlay = null;
    private DownloadDao mDownloadDao = null;
    private ArrayList<Song> mRecentPlayList = null;
    private ArrayList<Song> mDownloadList = null;

    public PlaylistManager(final Context context) {
        mMusicSQLite = MusicSQLite.getInstance(context);
        mRecentPlay = RecentPlay.getInstance(context);
        mDownloadDao = DownloadDao.getInstance(context);
        mRecentPlayList = new ArrayList<Song>();
        mDownloadList = new ArrayList<Song>();
    }

    public static final synchronized PlaylistManager getInstance(final Context context) {
        if (instance == null) {
            instance = new PlaylistManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 获取本地音乐列表
     * @return
     */
    public ArrayList<Song> getLocalMusic(){
        return null;
    }

    /**
     * 获取最近播放列表
     * @return
     */
    public ArrayList<Song> getRecentPlay(){
        return mRecentPlayList;
    }

    /**
     * 获取下载歌曲列表
     * @return
     */
    public ArrayList<Song> getDownload() {
        return mDownloadList;
    }

    /**
     * 获取本地音乐歌曲数量
     * @return
     */
    public int getLocalMusicSize(){
        return 0;
    }

    /**
     * 获取最近播放歌曲数量
     * @return
     */
    public int getRecentPlaySize(){
        return mRecentPlayList.size();
    }

    /**
     * 获取下载歌曲数量
     * @return
     */
    public int getDownloadSize(){
        return mDownloadList.size();
    }

    /**
     * 初始化
     * 从数据库读取最近播放列表
     * @param user_id
     */
    public void queryRecentPlay(String user_id){
        Cursor cursor = mRecentPlay.getRecentPlay(user_id);
        ArrayList<Song> list = new ArrayList<Song>();
        if(cursor.moveToFirst()){
            do {
                Song song = new Song();
                song.setId(cursor.getLong(0));
                song.setTitle(cursor.getString(1));
                song.setArtist(cursor.getString(2));
                song.setAlbum(cursor.getString(3));
                song.setAlbumId(cursor.getLong(4));
                song.setDuration(cursor.getLong(5));
                song.setSize(cursor.getLong(6));
                song.setUrl(cursor.getString(7));
                list.add(song);
            }while (cursor.moveToNext());
            cursor.close();
        }
        mRecentPlayList = list;
    }

    /**
     * 初始化
     * 从数据库读取下载歌曲列表
     * @param user_id
     */
    public void queryDownload(String user_id){
        Cursor cursor = mDownloadDao.getDownload(user_id);
        ArrayList<Song> list = new ArrayList<Song>();
        if(cursor.moveToFirst()){
            do {
                Song song = new Song();
                song.setId(cursor.getLong(0));
                song.setTitle(cursor.getString(1));
                song.setArtist(cursor.getString(2));
                song.setAlbum(cursor.getString(3));
                song.setAlbumId(cursor.getLong(4));
                song.setDuration(cursor.getLong(5));
                song.setSize(cursor.getLong(6));
                song.setUrl(cursor.getString(7));
                list.add(song);
            }while (cursor.moveToNext());
            cursor.close();
        }
        mDownloadList = list;
    }

    public void addRecentPlay(Song song, String user_id){
        mRecentPlay.insert(song, user_id);
    }
}
