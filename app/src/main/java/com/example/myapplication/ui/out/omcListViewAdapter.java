package com.example.myapplication.ui.out;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.myapplication.Thing;

import static android.content.ContentValues.TAG;
import static com.example.myapplication.R.color.colorLittleBlue;
import static com.example.myapplication.R.color.colorWhite;

public class omcListViewAdapter extends BaseAdapter{
    List<Thing> list=new ArrayList<Thing>();
    Context mcontext;

    public omcListViewAdapter(Context context,List<Thing> list1){
        mcontext=context;
        list=list1;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return true;}

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_omclistview, parent, false);
            convertView.setTag(new ViewH(convertView));
        }
        ListView li = (ListView) parent;
        ViewH holder = (ViewH) convertView.getTag();
        holder.t.setText(list.get(position).getName());
        if (((AbsListView)parent).isItemChecked(position)) {
            Log.d(TAG, "updateBackground: lll"+position);
            convertView.setBackgroundColor(Color.parseColor("#87CEFA"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        //updateBackground(li,position,convertView);
        return convertView;
    }
    @SuppressLint("ResourceAsColor")
    public void updateBackground(ListView l,int position, View view) {
        if (l.isItemChecked(position)) {
            Log.d(TAG, "updateBackground: lll");
            view.setBackgroundColor(colorLittleBlue);
        } else {
            view.setBackgroundColor(colorWhite);
        }
    }

    public static class ViewH{
        private TextView t;
        private CheckBox r;
        public ViewH(View view){
            t=view.findViewById(R.id.item_text1);
        }
    }

}
