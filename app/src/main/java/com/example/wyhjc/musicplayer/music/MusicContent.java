package com.example.wyhjc.musicplayer.music;

import com.example.wyhjc.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new MusicItem(R.drawable.album_image1, "I will possess your heart", "Death Cab for Cutie", 515));
        ITEMS.add(new MusicItem(R.drawable.album_image2, "You", "the 1975", 591));
        ITEMS.add(new MusicItem(R.drawable.album_image3, "The Yellow Ones", "Pinback", 215));
        ITEMS.add(new MusicItem(R.drawable.album_image4, "Chop suey", "System of a down", 242));
    }

    public static class MusicItem {

        private final int mCover;
        private final String mTitle;
        private final String mArtist;
        private final long mDuration;

        public MusicItem(int cover, String title, String artist, long duration) {
            mCover = cover;
            mTitle = title;
            mArtist = artist;
            mDuration = duration;
        }

        public int getCover() {
            return mCover;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getArtist() {
            return mArtist;
        }

        public long getDuration() {
            return mDuration;
        }
    }
}
