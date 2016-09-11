package com.example.zou.start;

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
import com.example.zou.read.R;
import com.example.zou.sql.Novel;
import com.example.zou.start.StartActivity;

import java.util.List;

/**
 * Created by zou on 2016/7/19.
 */
public class FavoriteListAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    List<Novel> favoritnovel;
    ListView listView;

    public FavoriteListAdapter(List<Novel> favoritnovel, ListView listView) {
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
        TextView name= (TextView) convertView.findViewById(R.id.tv_favorite_nover_name);
        TextView chaptertitle= (TextView) convertView.findViewById(R.id.tv_chapter_account);
        name.setText(favoritnovel.get(position).getTitle());
        chaptertitle.setText(favoritnovel.get(position).getChaptertitle());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(StartActivity.getContext(), ActivityChapter.class);
                intent.putExtra("newchapterurl",favoritnovel.get(position).getChapterurl());
                intent.putExtra("directoryurl",favoritnovel.get(position).getListurl());
                intent.putExtra("chapteraccount",favoritnovel.get(position).getChapteraccount());
                StartActivity.getContext().startActivity(intent);
                Toast.makeText(StartActivity.getContext(),"选择了"+favoritnovel.get(position)
                        .getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
