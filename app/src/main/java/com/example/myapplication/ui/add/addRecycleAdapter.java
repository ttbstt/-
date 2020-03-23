package com.example.myapplication.ui.add;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Thing;
import com.example.myapplication.ui.add.addFragment;


import java.util.ArrayList;

public class addRecycleAdapter extends RecyclerView.Adapter <addRecycleAdapter.myViewHolder>{
    private Context context;
    private ArrayList<Thing> list;

    //private OnItemClickListener mlistener;


    public addRecycleAdapter(Context context,ArrayList<Thing> list1/*,OnItemClickListener listener*/){
        this.context=context;
        this.list=list1;
       // this.mlistener=listener;
    }
    public addRecycleAdapter.myViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView=View.inflate(context, R.layout.item_layout_add,null);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder,final int position) {

        Thing thing=list.get(position);
        holder.thing_name.setText(thing.getName());
        holder.thing_flag.setText("");
        holder.number.setText(thing.getNumber());
        if(thing.getNumber()!=null)
            holder.button1.setText("已绑定");
        else
            holder.button1.setText("点击绑定");//？？？待测试，若不行，则添加刷新？？？

        //由接口实现在addFragment中 按钮触发
        /*holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onClick(position);
                //Toast.makeText(context,"click"+position,Toast.LENGTH_SHORT).show();
            }
        });*/
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int a=getItem(position).getId();
                mOnItemClickListener.onClick(position,holder.thing_name.getText().toString());
            }
        });


        if(mOnLongItemClickListener!=null){
            /*holder.thing_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });*/
            holder.thing_name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongItemClickListener.onLongClick(position,holder.thing_name.getText().toString());
                    return false;
                }
            });
        }


    }
    public int getItemCount(){
        return list.size();
    }
    public Thing getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView thing_name;
        private TextView thing_flag;
        private Button button1;
        private TextView number;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            thing_name=itemView.findViewById(R.id.thing_name);
            thing_flag=itemView.findViewById(R.id.thing_flag);
            button1=itemView.findViewById(R.id.button1);
            number=itemView.findViewById(R.id.number);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position,String id);
    }
    public OnItemClickListener mOnItemClickListener;
    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    //长按删除接口
    public interface  OnLongItemClickListener{
        //void onClick(int position);
        void onLongClick(int position,String name);
    }
    public OnLongItemClickListener mOnLongItemClickListener;
    public void setOnItemClickListener(OnLongItemClickListener onItemClickListener){
        this.mOnLongItemClickListener=onItemClickListener;
    }



    //添加数据
    public void addData(int loca,String name) {
        //在list中添加数据，并通知条目加入一条
        Thing th=new Thing(name,"0");
        list.add(th);
        //添加动画
        notifyItemInserted(loca);
        notifyItemRangeChanged(loca,list.size()-loca);
    }

    //删除数据
    public void deletData(int loca){
        list.remove(loca);
        notifyItemRemoved(loca);
        notifyDataSetChanged();
    }

}
