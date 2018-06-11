package com.example.wyhjc.musicplayer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicSQLite extends SQLiteOpenHelper {

    private static MusicSQLite instance = null;
    private final Context mContext;

    public MusicSQLite(final Context context) {
        super(context, MusicSQLiteConstant.DATABASE_NAME, null, MusicSQLiteConstant.DATABASE_VERSION);
        mContext = context;
    }

    public static final synchronized MusicSQLite getInstance(final Context context) {
        if (instance == null) {
            instance = new MusicSQLite(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        RecentPlay.getInstance(mContext).onCreate(sqLiteDatabase);
        DownloadDao.getInstance(mContext).onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        RecentPlay.getInstance(mContext).onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        DownloadDao.getInstance(mContext).onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
