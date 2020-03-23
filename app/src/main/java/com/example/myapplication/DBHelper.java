package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="hhh.db";
    private static final int VERSION=3;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists product(id integer primary key autoincrement,name varchar(20),number varchar(20),flag varchar(20))";
        String sql1="create table if not exists product1(id integer primary key autoincrement,name varchar(20),number varchar(20),flag varchar(20))";
        String sql2="create table if not exists groups(id integer primary key autoincrement,name varchar(20),contents varchar(1000))";

        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists product1");
        db.execSQL("drop table if exists groups");
        onCreate(db);
    }
}
