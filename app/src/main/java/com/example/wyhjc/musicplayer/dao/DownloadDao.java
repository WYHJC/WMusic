package com.example.wyhjc.musicplayer.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DownloadDao {
    private static DownloadDao instance = null;
    private MusicSQLite mMusicSQLite = null;

    public DownloadDao(final Context context) {
        mMusicSQLite = MusicSQLite.getInstance(context);
    }

    public static final synchronized DownloadDao getInstance(final Context context){
        if(instance == null){
            instance = new DownloadDao(context.getApplicationContext());
        }
        return instance;
    }

    public void onCreate(final SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(MusicSQLiteConstant.CREATE_TABLE_DOWNLOAD);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        sqLiteDatabase.execSQL(MusicSQLiteConstant.CREATE_TABLE_DOWNLOAD);
    }

    /**
     * 获取当前用户下载列表
     * @param user_id
     * @return
     */
    public Cursor getDownload(String user_id){
        final SQLiteDatabase database = mMusicSQLite.getReadableDatabase();
        return database.query(MusicSQLiteConstant.TABLE_DOWNLOAD,
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
}
