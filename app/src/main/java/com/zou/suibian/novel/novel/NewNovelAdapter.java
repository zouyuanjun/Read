package com.zou.suibian.novel.novel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zou.suibian.novel.start.StartActivity;
import com.zou.suibian.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NewNovelAdapter extends BaseAdapter {
    private Context context;
    ArrayList<NewNovelBean> newnovelbean=new ArrayList();
    private LayoutInflater mnewNovelInflater;
    ListView listView;
    public NewNovelAdapter(ArrayList<NewNovelBean> newnovelbean, ListView listView) {
        this.newnovelbean = newnovelbean;
        this.context= StartActivity.getContext();
        this.listView=listView;
        mnewNovelInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newnovelbean.size();
    }

    @Override
    public Object getItem(int position) {
        return newnovelbean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=mnewNovelInflater.inflate(R.layout.new_novel_listitem,null);
        TextView tv_new_novelname= (TextView) convertView.findViewById(R.id.tv_new_novelname);
        TextView tv_new_newchaptersname= (TextView) convertView.findViewById(R.id.tv_new_newchaptersname);
        SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.im_pic_view);
        TextView tv_intro= (TextView) convertView.findViewById(R.id.tv_intro);
        tv_intro.setText(newnovelbean.get(position).intro);
        tv_new_novelname.setText(newnovelbean.get(position).name);
        tv_new_newchaptersname.setText(newnovelbean.get(position).newchaptersname);
        draweeView.setImageURI(newnovelbean.get(position).picurl);
        return convertView;
    }
}
