package com.example.wyhjc.musicplayer.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wyhjc.musicplayer.model.Song;

public class RecentPlay {
    private static RecentPlay instance = null;
    private MusicSQLite mMusicSQLite = null;

    public RecentPlay(final Context context) {
        mMusicSQLite = MusicSQLite.getInstance(context);
    }

    public static final synchronized RecentPlay getInstance(final Context context) {
        if (instance == null) {
            instance = new RecentPlay(context.getApplicationContext());
        }
        return instance;
    }

    public void onCreate(final SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(MusicSQLiteConstant.CREATE_TABLE_RECENT_PLAY);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

    }

    /**
     * 获取当前用户最近播放列表
     * @param user_id
     * @return
     */
    public Cursor getRecentPlay(String user_id){
        final SQLiteDatabase database = mMusicSQLite.getReadableDatabase();
        return database.query(MusicSQLiteConstant.TABLE_RECENT_PLAY,
                new String[]{MusicSQLiteConstant.TABLE_COLUMN_SONG_ID,
                        MusicSQLiteConstant.TABLE_COLUMN_SONG_TITLE,
                        MusicSQLiteConstant.TABLE_COLUMN_SONG_ARTIST,
                        MusicSQLiteConstant.TABLE_COLUMN_ALBUM,
                        MusicSQLiteConstant.TABLE_COLUMN_ALBUM_ID,
                        MusicSQLiteConstant.TABLE_COLUMN_DURATION,
                        MusicSQLiteConstant.TABLE_COLUMN_SONG_SIZE,
                        MusicSQLiteConstant.TABLE_COLUMN_SONG_URL},
                MusicSQLiteConstant.TABLE_COLUMN_USER_ID + " = ?",
                new String[]{user_id},
                null,
                null,
                null,
                null);
    }

    /**
     * 插入最近播放列表前判断该歌曲是否已经在表中
     * @param _id
     * @param user_id
     * @return
     */
    public boolean recheck(long _id, String user_id){
        final SQLiteDatabase database = mMusicSQLite.getReadableDatabase();
        Cursor cursor = database.query(MusicSQLiteConstant.TABLE_RECENT_PLAY,
                new String[]{MusicSQLiteConstant.TABLE_COLUMN_SONG_ID},
                MusicSQLiteConstant.TABLE_COLUMN_SONG_ID + " = " + _id + " AND " + MusicSQLiteConstant.TABLE_COLUMN_USER_ID + " = ?",
                new String[]{user_id},
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void removeDuplicate(long _id, String user_id){
        final SQLiteDatabase database = mMusicSQLite.getWritableDatabase();
        database.delete(MusicSQLiteConstant.TABLE_RECENT_PLAY,
                MusicSQLiteConstant.TABLE_COLUMN_SONG_ID + " = " + _id + " AND " + MusicSQLiteConstant.TABLE_COLUMN_USER_ID + " = ?",
                new String[]{user_id});
    }

    /**
     * 往最近播放列表插入数据
     * @param song
     * @param user_id
     */
    public void insert(Song song, String user_id){
        final SQLiteDatabase database = mMusicSQLite.getWritableDatabase();
        database.beginTransaction();
        if(recheck(song.getId(), user_id))
            removeDuplicate(song.getId(), user_id);
        final ContentValues values = new ContentValues(9);
        values.put(MusicSQLiteConstant.TABLE_COLUMN_SONG_ID, song.getId());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_USER_ID, user_id);
        values.put(MusicSQLiteConstant.TABLE_COLUMN_SONG_TITLE, song.getTitle());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_SONG_ARTIST, song.getArtist());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_ALBUM, song.getAlbum());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_ALBUM_ID, song.getAlbumId());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_DURATION, song.getDuration());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_SONG_SIZE, song.getSize());
        values.put(MusicSQLiteConstant.TABLE_COLUMN_SONG_URL, song.getUrl());
        database.insert(MusicSQLiteConstant.TABLE_RECENT_PLAY, null, values);
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}