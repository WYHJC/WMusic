package com.example.wyhjc.musicplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.wyhjc.musicplayer.model.Song;

import java.util.ArrayList;

public class MusicUtil {

    private static ArrayList<Song> mSongsList;

    public static void initLocalAllPlaylists(Context context){
        mSongsList = getSongs(context);
    }

    public static Song getPlaySong(int position){
        return mSongsList.get(position);
    }

    public static int getLocalMusicCount(){
        return mSongsList.size();
    }

    /**
     * 从数据库查询MP3文件信息
     * @param context
     * @return
     */
    public static ArrayList<Song> getSongs(Context context){
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.DURATION + ">=10000", null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        mSongsList = new ArrayList<Song>();
        for(int i = 0;i < cursor.getCount();i++){
            cursor.moveToNext();
            Song song = new Song();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));            //id
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));   //音乐标题
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)); //艺术家
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));   //专辑
            long albumid = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //专辑id
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));         //文件大小
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));       //文件路径

            song.setId(id);
            song.setTitle(title);
            song.setArtist(artist);
            song.setAlbum(album);
            song.setAlbumId(albumid);
            song.setDuration(duration);
            song.setSize(size);
            song.setUrl(url);
            mSongsList.add(song);
        }
        cursor.close();
        return mSongsList;
    }

    /**
     * 格式化时间,将毫秒转换为00:00格式
     * @param time
     * @return
     */
    public static String formatTime(long time){
        String min = time / (1000*60) + "";
        String sec = time % (1000*60) + "";
        if (min.length()<2){
            min = "0" + time / (1000*60) + "";
        }else {
            min = time / (1000*60) + "";
        }
        if (sec.length() == 4){
            sec = "0" + time % (1000*60) + "";
        }else if (sec.length() == 3){
            sec = "00" + time % (1000*60) + "";
        }else if (sec.length() == 2){
            sec = "000" + time % (1000*60) + "";
        }else if (sec.length() == 1) {
            sec = "0000" + time % (1000 * 60) + "";
        }
        return min + ":" + sec.trim().substring(0,2);
    }

    public static ArrayList<Song> getSongsList(){
        return mSongsList;
    }
}
