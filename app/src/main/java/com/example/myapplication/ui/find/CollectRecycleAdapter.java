package com.example.myapplication.ui.find;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Thing;

import java.util.ArrayList;

public class CollectRecycleAdapter extends RecyclerView.Adapter<CollectRecycleAdapter.myViewHolder> {
    private Context context;
    private ArrayList<Thing> list;
    public CollectRecycleAdapter(Context mcontext,ArrayList<Thing> list1){
        this.context=mcontext;
        this.list=list1;
    }
    public CollectRecycleAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView=View.inflate(context, R.layout.item_layout_find,null);
        return new myViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        Thing thing=list.get(position);
        holder.thing_image.setImageResource(thing.getImageId());
        holder.thing_name.setText(thing.getName());
        if(mOnItemClickListener!=null){
            /*holder.thing_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });*/
            holder.thing_name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position,holder.thing_name.getText().toString());
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView thing_name;
        private ImageView thing_image;
        private Button button;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            thing_image=itemView.findViewById(R.id.thing_image);
            thing_name=itemView.findViewById(R.id.thing_name);
            button=itemView.findViewById(R.id.button1);
        }
    }
    //定义一个接口
    public interface  OnItemClickListener{
        //void onClick(int position);
        void onLongClick(int position,String name);
    }
    public OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

}

