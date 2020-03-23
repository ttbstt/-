package com.example.myapplication.ui.out;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class out_manageAdapter extends BaseExpandableListAdapter implements View.OnClickListener {
    public String children[][];
    private Context context;
    List<String> groups=new ArrayList<String>();
    //final FragmentManager fm = getFragmentManager();
    //final FragmentTransaction ft = fm.beginTransaction();

    public out_manageAdapter(Context context,ArrayList<String> groups,String[][] children){
        this.context=context;
        this.groups=groups;
        this.children=children;


    }
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return children[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return children[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
    // //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }
    /** * * 获取显示指定组的视图对象
     * * * @param groupPosition 组位置
     * * @param isExpanded 该组是展开状态还是伸缩状态，true=展开
     * * @param convertView 重用已有的视图对象
     * * @param parent 返回的视图对象始终依附于的视图组 */

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandlistview_parent,parent,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.parent_textview_id = convertView.findViewById(R.id.parent_textview_id);
            groupViewHolder.parent_image = convertView.findViewById(R.id.parent_image);
            groupViewHolder.image=convertView.findViewById(R.id.item_img1);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.parent_textview_id.setText(groups.get(groupPosition));
        //如果是展开状态，
        if (isExpanded){
            groupViewHolder.parent_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.down));
            groupViewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.add();
                    }

               /* Fragment out_managechoose=new out_manageChoose();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fr,out_managechoose);
                ft.addToBackStack(null);
                ft.commit();*/
                }
            });
        }else{
            groupViewHolder.parent_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.down));
            groupViewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.add();
                    }

               /* Fragment out_managechoose=new out_manageChoose();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fr,out_managechoose);
                ft.addToBackStack(null);
                ft.commit();*/
                }
            });
        }



        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandlistview_child,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.chidren_item = (TextView)convertView.findViewById(R.id.children_item);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }        childViewHolder.chidren_item.setText(children[groupPosition][childPosition]);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    static class GroupViewHolder {

        TextView parent_textview_id;

        ImageView parent_image;
        ImageView image;

    }
    static class ChildViewHolder {

        TextView chidren_item;



    }

    public interface AddClickListener{
        void add();
    }
    private AddClickListener mListener;
    public void setListener(AddClickListener listener){mListener=listener;}

}
