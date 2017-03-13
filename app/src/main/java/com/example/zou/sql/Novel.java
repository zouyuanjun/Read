package com.example.zou.sql;

import org.litepal.crud.DataSupport;

/**
 * Created by zou on 2016/9/6.
 */
public class Novel extends DataSupport{
    private int id;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    private String picurl;
    private String title;
    private String listurl;
    private String chapterurl;
    private int chapteraccount;
    private String chaptertitle;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    private int source;

    public String getChaptertitle() {
        return chaptertitle;
    }

    public void setChaptertitle(String chaptertitle) {
        this.chaptertitle = chaptertitle;
    }

    public int getChapteraccount() {
        return chapteraccount;
    }

    public void setChapteraccount(int chapteraccount) {
        this.chapteraccount = chapteraccount;
    }

    public String getChapterurl() {
        return chapterurl;
    }

    public void setChapterurl(String chapterurl) {
        this.chapterurl = chapterurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListurl() {
        return listurl;
    }
    public void setListurl(String listurl) {
        this.listurl = listurl;
    }
}
