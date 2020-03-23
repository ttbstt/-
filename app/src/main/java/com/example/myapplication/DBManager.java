package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    public DBManager(Context context){
        helper=new DBHelper(context);
        db=helper.getWritableDatabase();
    }
    //给product中添加物品
    public void insert(String name1){
        db.beginTransaction();
        try{
            ContentValues cv=new ContentValues();
            cv.put("name",name1);
            cv.put("flag","0");
            db.insert("product","id",cv);
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }
    }
    //给product1中添加物品
    public void insert1(Thing thing){
        db.beginTransaction();
        try{
            ContentValues cv=new ContentValues();
            cv.put("name",thing.getName());
            cv.put("number",thing.getNumber());
            cv.put("flag",thing.getFlag());
            db.insert("product1","id",cv);
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }
    }
    //给分组表即groups中添加小组名
    public void insert2(String name){
        db.beginTransaction();
        try{
            ContentValues cv=new ContentValues();
            cv.put("name",name);
            db.insert("groups","id",cv);
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }
    }

    //从product中查询所有项，显示在add页面上
    public ArrayList Query0(){
        String name="";
        String number="";
        String flag;
        ArrayList<Thing> produList=new ArrayList<Thing>();
        Cursor c= (Cursor) db.query("product",null,null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            name = c.getString(1);
            flag = c.getString(3);
            Thing produ = new Thing(name, flag);
            produList.add(produ);
            c.moveToNext();
        }
        c.close();
        return produList;
    }

    //从product中查询flag=1项，即已经绑定过硬件号码的物品，显示在out_thing页面
    public ArrayList Query(){
        String name="";
        String number="";
        String flag;
        ArrayList<Thing> produList=new ArrayList<Thing>();
        Cursor c= (Cursor) db.query("product",null,null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if("1".equals(c.getString(3))) {
                name = c.getString(1);
                flag = c.getString(3);
                Thing produ = new Thing(name, flag);
                produList.add(produ);
            }
            c.moveToNext();
        }
        c.close();
        return produList;
    }
    //从product1中查询所有项，显示在real_out页面上，即你已选定的要带物品
    public ArrayList Query1(){
        String name="";
        String number="";
        String flag;
        ArrayList<Thing> produList=new ArrayList<Thing>();
        Cursor c= (Cursor) db.query("product1",null,null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
                name = c.getString(1);
                flag = c.getString(3);
                Thing produ = new Thing(name, flag);
                produList.add(produ);
                c.moveToNext();
        }
        c.close();
        return produList;
    }
    //3.17
    //从分组表中查询信息，显示在out_manage页面上
    public ArrayList Query2(){
        String name="";
        //String number="";
        //String flag;
        ArrayList<String> groups=new ArrayList<String>();
        Cursor c= (Cursor) db.query("groups",null,null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            name = c.getString(1);
            groups.add(name);
            c.moveToNext();
        }
        c.close();
        return groups;
    }



    //将标签与物品绑定
    /*public void Binding(String flag_num,int id){

            ContentValues cv = new ContentValues();
            cv.put("number", flag_num);
            cv.put("flag", "1");
            //db.update("product",cv,"id = ?",new String[] {"pos+1"});
            db.update("product", cv, "id=?" + id + 1, null);
            db.setTransactionSuccessful();
    }*/
    //重写的绑定函数
    public void Binding(String flag_num,String name1){
        db.execSQL("update product set number=?,flag=? where name=?",new String[]{flag_num,"1",name1});
    }

    //删除add页面物品
    public void Delete_DB(String thingsname){
        db.execSQL("delete from product where name=?",new String[]{thingsname});
    }
    //取消某一要携带物品
    public void Delete_DB1(String thingsname){
        db.execSQL("delete from product1 where name=?",new String[]{thingsname});
    }


}
