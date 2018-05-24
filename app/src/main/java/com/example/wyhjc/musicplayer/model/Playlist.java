package com.example.wyhjc.musicplayer.model;

//歌单实体类
public class Playlist {
    private long id;
    private int imageID = -1;
    private String name;
    private int count;
    private String author;

    public Playlist(long id, int imageID, String name, int count, String author){
        this.id = id;
        this.imageID = imageID;
        this.name = name;
        this.count = count;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
