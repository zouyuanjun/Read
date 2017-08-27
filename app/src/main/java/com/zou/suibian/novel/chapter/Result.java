package com.zou.suibian.novel.chapter;

/**
 * Created by zou on 2016/7/14.
 */
public class Result {
    String content;
    String lasturl;
    String nexturl;
    String name;
    String chaptertitle;
    public Result(String content, String lasturl, String nexturl,String name,String chaptertitle) {
        this.content = content;
        this.lasturl = lasturl;
        this.nexturl = nexturl;
        this.name=name;
        this.chaptertitle=chaptertitle;
    }
}
