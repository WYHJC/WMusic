package com.example.wyhjc.musicplayer.dao;

public class MusicSQLiteConstant {
    public static final String DATABASE_NAME = "wmusic.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_RECENT_PLAY = "recent_play";
    public static final String TABLE_DOWNLOAD = "download_music";

    public static final String TABLE_COLUMN_USER_ID = "user_id";
    public static final String TABLE_COLUMN_SONG_ID = "_id";
    public static final String TABLE_COLUMN_SONG_TITLE = "title";
    public static final String TABLE_COLUMN_SONG_ARTIST = "artist";
    public static final String TABLE_COLUMN_ALBUM = "album";
    public static final String TABLE_COLUMN_ALBUM_ID = "album_id";
    public static final String TABLE_COLUMN_DURATION = "duration";
    public static final String TABLE_COLUMN_SONG_SIZE = "size";
    public static final String TABLE_COLUMN_SONG_URL = "url";

    public static final String CREATE_TABLE_RECENT_PLAY = "CREATE TABLE IF NOT EXISTS " + TABLE_RECENT_PLAY + "("
            + TABLE_COLUMN_SONG_ID + " LONG NOT NULL, "
            + TABLE_COLUMN_USER_ID + " VARCHAR(32), "
            + TABLE_COLUMN_SONG_TITLE + " TEXT, "
            + TABLE_COLUMN_SONG_ARTIST + " TEXT, "
            + TABLE_COLUMN_ALBUM + " TEXT, "
            + TABLE_COLUMN_ALBUM_ID + " LONG, "
            + TABLE_COLUMN_DURATION + " LONG, "
            + TABLE_COLUMN_SONG_SIZE + " LONG, "
            + TABLE_COLUMN_SONG_URL + " TEXT);";

    public static final String CREATE_TABLE_DOWNLOAD = "CREATE TABLE IF NOT EXISTS " + TABLE_DOWNLOAD + "("
            + TABLE_COLUMN_SONG_ID + " LONG NOT NULL, "
            + TABLE_COLUMN_USER_ID + " VARCHAR(32), "
            + TABLE_COLUMN_SONG_TITLE + " TEXT, "
            + TABLE_COLUMN_SONG_ARTIST + " TEXT, "
            + TABLE_COLUMN_ALBUM + " TEXT, "
            + TABLE_COLUMN_ALBUM_ID + " LONG, "
            + TABLE_COLUMN_DURATION + " LONG, "
            + TABLE_COLUMN_SONG_SIZE + " LONG, "
            + TABLE_COLUMN_SONG_URL + " TEXT);";
}
