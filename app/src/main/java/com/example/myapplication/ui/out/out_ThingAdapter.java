package com.example.myapplication.ui.out;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.Thing;

import java.util.ArrayList;
import java.util.List;

public class out_ThingAdapter extends BaseAdapter implements View.OnClickListener {
    List<Thing> data=new ArrayList<Thing>();
    Context mContext;
    //创建构造方法
    public  out_ThingAdapter(Context context,ArrayList<Thing> List1){
        mContext=context;
        data=List1;
        /*for(int i=0;i<30;i++){
            data.add("item"+i);
        }*/
    }
    //重写自定义适配器的除了getView()的三个方法
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_out_thing, parent, false);
            convertView.setTag(new ViewH(convertView));
        }
        ViewH holder = (ViewH) convertView.getTag();
        holder.tv.setText(data.get(position).getName());
        holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.add(v,data.get(position));
                    }

                }
            });
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.delete(data.get(position));
                }
            }
        });
        return convertView;
    }

    public void onClick(View v) {
    }


    //定义接口
    public interface AddClickListener {
        void add(View v,Thing thing);
        void delete(Thing thing);
    }
    private AddClickListener mListener;
    public void setListener(AddClickListener listener) {
        mListener = listener;
    }

    /*public interface DeleteClickListener{
        void delete(Thing thing);
    }
    private DeleteClickListener Listener;
    public void setListener(DeleteClickListener listener){
        listener=listener;
    }*/
    public static class ViewH {
        private ImageView img;
        private ImageView img1;
        private TextView tv;
        public ViewH(View view) {
            img = ((ImageView) view.findViewById(R.id.item_img));
            img1=view.findViewById(R.id.item_img1);
            tv = ((TextView) view.findViewById(R.id.item_text));
        }

    }
}



