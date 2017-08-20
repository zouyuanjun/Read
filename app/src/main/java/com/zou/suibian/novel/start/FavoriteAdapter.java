package com.zou.suibian.novel.start;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zou.suibian.novel.sql.Novel;
import com.zou.suibian.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by zou on 2016/7/19.
 */
public class FavoriteAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    //数据源
    private List<Novel> dataList;
    //构造函数
    public FavoriteAdapter(List<Novel> dataList) {
        this.dataList = dataList;
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

        mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.startactivity_item, null);
        BodyViewHolder bodyViewHolder=new BodyViewHolder(view);
        view.setOnClickListener(this);
        return bodyViewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            //其他条目中的逻辑在此
            Setting.setSource(dataList.get(position).getSource());
            ((BodyViewHolder)viewHolder).itemView.setTag(dataList.get(position).getChapterurl());
            ((BodyViewHolder) viewHolder).getfavorite_nover_name().setText(dataList.get(position).getNovelname());
            ((BodyViewHolder) viewHolder).getTv_chapter_account().setText(dataList.get(position).getChaptertitle());
            ((BodyViewHolder) viewHolder).getDraweeView().setImageURI(dataList.get(position).getPicurl());
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }
     /**
     * 给GridView中的条目用的ViewHolder
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
         private TextView favorite_nover_name;
         private SimpleDraweeView draweeView ;
         private TextView tv_chapter_account;
         public BodyViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.im_favorite_view);
            favorite_nover_name = (TextView) itemView.findViewById(R.id.tv_favorite_nover_name);
            tv_chapter_account= (TextView) itemView.findViewById(R.id.tv_chapter_account);
        }
         public SimpleDraweeView getDraweeView() {
             return draweeView;
         }

         public TextView getTv_chapter_account() {
             return tv_chapter_account;
         }
        public TextView getfavorite_nover_name() {
            return favorite_nover_name;
        }
    }
}