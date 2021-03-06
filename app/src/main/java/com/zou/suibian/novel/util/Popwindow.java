package com.zou.suibian.novel.util;

import android.util.Log;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.drawable.ColorDrawable;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.content.Context;

import com.zou.suibian.R;

/**
 * Created by 邹远君 on 2017/3/29.
 */

public class Popwindow extends PopupWindow {
    TextView collect;
    TextView backlist;
    private View mMenuView;

    public Popwindow(Activity activity,OnClickListener itemsOnClick) {
        super(activity);
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popwindow,null);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AppTheme);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x19e4b500);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.popwindowsview).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        collect= (TextView) mMenuView.findViewById(R.id.tv_collect);
        collect.setOnClickListener(itemsOnClick);
        backlist= (TextView) mMenuView.findViewById(R.id.tv_backlist);
        backlist.setOnClickListener(itemsOnClick);

    }
}
