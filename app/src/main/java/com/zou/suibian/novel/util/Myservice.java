package com.zou.suibian.novel.util;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;

/**
 * Created by 邹远君 on 2017/3/30.
 */

public class Myservice extends Service{
    String data;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MyBinder myBinder=new MyBinder();
        return myBinder;
    }
    public class MyBinder extends Binder{

        public String httpconnet(String url){

            HttpLoad httpLoad = new HttpLoad();
            httpLoad.setDataDownloadListener(new HttpLoad.DataDownloadListener() {
                @Override
                public void dataDownloadSuccessfully(String result) throws UnsupportedEncodingException {
                data=result;
                }

                @Override
                public void dataDownloadFailed() {

                }
            });
        return data;
        }

    }
}
