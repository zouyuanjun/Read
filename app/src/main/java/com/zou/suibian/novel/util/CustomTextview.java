package com.zou.suibian.novel.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

/**
 * Created by 邹远君 on 2017/3/28.
 */

public class CustomTextview extends android.support.v7.widget.AppCompatTextView {

    private OndownActionListener downlistener;
    Paint paint;

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    public String title;

    public CustomTextview(Context context) {
        super(context);
    }

    public CustomTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint= new Paint();//设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setTextSize(40);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(gettime()+"     "+ title,20,60,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if (downlistener!=null){
                    downlistener.OnDown(x,y);
                }
                return  true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:

                return true;
        }
        return super.onTouchEvent(event);
    }
    public void setOndownlistener(OndownActionListener ondownlistener){
        this.downlistener=ondownlistener;
    }


    public interface OndownActionListener{
        public void OnDown(float x, float y);
    }
    public String gettime(){
        Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String time=hour+":"+minute;
        return time;
    }
}

