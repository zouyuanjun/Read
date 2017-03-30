package com.example.z.novellist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.z.chapter.ActivityChapter;
import com.example.z.start.Setting;
import com.example.z.start.StartActivity;
import com.example.zou.novel.R;

import java.util.ArrayList;

/**
 * Created by zou on 2016/7/14.
 */
public class NovelListAdapter extends BaseAdapter{
    private Context context;
    ArrayList<ChapterDirectoryBean> novelListBean =new ArrayList();
    private LayoutInflater mNovelListInflater;
    public NovelListAdapter(ArrayList<ChapterDirectoryBean> novelListBean) {
        this.novelListBean = novelListBean;
        this.context= StartActivity.getContext();
        mNovelListInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return novelListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return novelListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=mNovelListInflater.inflate(R.layout.novel_listitem,null);

        TextView chaptersname= (TextView) convertView.findViewById(R.id.tv_novel_list_item);
        chaptersname.setText(novelListBean.get(position).chaptersname);
        return convertView;
    }
}
