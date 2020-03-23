package com.example.myapplication.ui.out;

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

public class real_outAdapter extends RecyclerView.Adapter<real_outAdapter.myViewHolder> {
    private Context context;
    private ArrayList<Thing> list;
    public real_outAdapter(Context context,ArrayList<Thing> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public real_outAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=View.inflate(context,R.layout.item_real_out,null);
        return new myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        Thing thing=list.get(position);
        holder.t.setText(thing.getName());
        holder.i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLister.onClick(position,holder.t.getText().toString());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder {
        private TextView t;
        private ImageView i;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t=itemView.findViewById(R.id.thing_name);
            i=itemView.findViewById(R.id.thing_image);
        }
    }
    public interface OnItemClickLister{
        void onClick(int position,String name);
    }
    public OnItemClickLister mOnItemClickLister;

    public void setmOnItemClickLister(OnItemClickLister mOnItemClickLister) {
        this.mOnItemClickLister = mOnItemClickLister;
    }
    //删除数据
    public void deletData(int loca){
        list.remove(loca);
        notifyItemRemoved(loca);
        notifyDataSetChanged();
    }
}


