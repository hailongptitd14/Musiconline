package com.example.cauvong.musiconline;

/**
 * Created by cauvong on 1/5/2018.
 */

public class ItemSong {

    private String mNameSong;
    private String mNameSinger;
    private String mPath;

    public ItemSong(String nameSong, String nameSinger, String path){
        this.mNameSong = nameSong;
        this.mNameSinger = nameSinger;
        this.mPath = path;
    }

    public String getmPath() {
        return mPath;
    }

    public String getmNameSong() {
        return mNameSong;
    }

    public String getmNameSinger() {
        return mNameSinger;
    }

}