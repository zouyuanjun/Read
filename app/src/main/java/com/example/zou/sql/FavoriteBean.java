package com.example.zou.sql;

/**
 * Created by zou on 2016/7/17.
 */
public class FavoriteBean {
    public String novelname;
    public String chapterurl;
    public  String novellist;
    public int ID;

    public FavoriteBean(String novelname, String chapterurl, String novellist,int ID) {
        this.novelname = novelname;
        this.chapterurl = chapterurl;
        this.novellist = novellist;
        this.ID=ID;
    }
}
