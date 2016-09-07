package com.example.zou.read;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zou.chapter.ActivityChapter;
import com.example.zou.sql.Novel;

import java.util.List;

/**
 * Created by zou on 2016/7/19.
 */
public class GridViewAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    List<Novel> favoritnovel;
    ListView listView;

    public GridViewAdapter(List<Novel> favoritnovel,ListView listView) {
        layoutInflater=LayoutInflater.from(StartActivity.getContext());
        this.favoritnovel=favoritnovel;
        this.listView=listView;

    }

    @Override
    public int getCount() {
        return favoritnovel.size();
    }

    @Override
    public Object getItem(int position) {
        return favoritnovel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.gridview_item,null);
        TextView name= (TextView) convertView.findViewById(R.id.tv_gridview_item);
        name.setText(favoritnovel.get(position).getTitle());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(StartActivity.getContext(), ActivityChapter.class);
                intent.putExtra("newchapterurl",favoritnovel.get(position).getChapterurl());
                intent.putExtra("baseurl",favoritnovel.get(position).getListurl());
                StartActivity.getContext().startActivity(intent);
                Toast.makeText(StartActivity.getContext(),"选择了"+favoritnovel.get(position)
                        .getTitle(),Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
