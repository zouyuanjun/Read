package com.example.zou.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zou on 2016/7/17.
 */
public class FavoriteDatabaseHelper extends SQLiteOpenHelper{
    private static final String NAME="novelname";
    private static final String CHAPTERURL="chapterurl";
    private  static final String NOVELLIST="novellist";
    private static final String DATABASE_TABLE="favorite";
    public ArrayList<FavoriteBean> favoriteBeanArrayList=new ArrayList<>();

    public static String name="favorite.db";
    public static int version=1;

    public static final String CREATE_Database = "create table favorite ("
            + "_id integer primary key autoincrement, "
            + "novelname text, "
            + "novellist text, "
            + "chapterurl text)";
    private Context mContext;

    public FavoriteDatabaseHelper(Context context) {
        super(context, name, null, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Database);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertContact(String novelname, String chanpterurl,String novellist)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, novelname);
        initialValues.put(CHAPTERURL, chanpterurl);
        initialValues.put(NOVELLIST, novellist);
        long rowID =db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        db.close();
        return rowID;
    }
    public ArrayList<FavoriteBean> getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.query(DATABASE_TABLE, new String[]{NAME, CHAPTERURL, NOVELLIST,"_id"}, null, null, null, null, null, null);
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                String name = mCursor.getString(mCursor.getColumnIndex(NAME));
                String chapterurl = mCursor.getString(mCursor.getColumnIndex(CHAPTERURL));
                String novellist = mCursor.getString(mCursor.getColumnIndex(NOVELLIST));
                int ID=mCursor.getInt(mCursor.getColumnIndex("_id"));
                favoriteBeanArrayList.add(new FavoriteBean(name,chapterurl,novellist,ID));
            }
        }
        mCursor.close();
        db.close();
        return favoriteBeanArrayList;
    }
}
