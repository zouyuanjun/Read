package com.example.zou.chapter;

/**
 * Created by zou on 2016/7/14.
 */
public class Result {
    String content;
    String lasturl;
    String nexturl;
    String name;
    public Result(String content, String lasturl, String nexturl,String name) {
        this.content = content;
        this.lasturl = lasturl;
        this.nexturl = nexturl;
        this.name=name;
    }
}
