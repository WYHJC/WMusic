package com.example.wyhjc.musicplayer.model;

//本地音乐菜单列表item
public class MusicFragmentItem {
    private int imageID;
    private String title;
    private int count;
    private boolean countChange;

    public MusicFragmentItem(int imageID, String title, int count) {
        this.imageID = imageID;
        this.title = title;
        this.count = count;
        this.countChange = true;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCountChange() {
        return countChange;
    }

    public void setCountChange(boolean countChange) {
        this.countChange = countChange;
    }
}
