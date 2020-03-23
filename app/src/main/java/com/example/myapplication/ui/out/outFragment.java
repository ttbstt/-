package com.example.myapplication.ui.out;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DBManager;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class outFragment extends Fragment {
    private RadioGroup rb;
    private Fragment oT;
    private Fragment oM;
    //private Fragment real_out;
    private Fragment[] fragments;
    List<String> list=new ArrayList<String>();
    private int lastfragment;
    private DBManager mgr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        mgr=new DBManager(getActivity());
        View view=inflater.inflate(R.layout.out,container,false);
        Toolbar toolbar = (Toolbar)view.findViewById (R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        rb=(RadioGroup)view.findViewById(R.id.rg);
        initFragments();
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_Thing:{
                        if (lastfragment != 0) {
                            switchFragment(lastfragment, 0,"oT");
                            lastfragment = 0;
                        }
                        //switchFragment(lastfragment,0);
                        //Toast.makeText(getActivity(),"点击t",Toast.LENGTH_SHORT).show();
                        break;}
                    case R.id.rb_manage: {
                        if (lastfragment != 1) {
                            switchFragment(lastfragment, 1,"oM");
                            lastfragment = 1;
                        }
                        Toast.makeText(getActivity(), "点击m", Toast.LENGTH_SHORT).show();
                        break;
                    }//switchFragment(lastfragment,1);
                }
            }
        });
        return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_out,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                Toast.makeText(getActivity(),"连接成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                break;
            case R.id.add_new:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("请输入要添加的物品名称");
                // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.add_dialog, null);
                // 设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view);


                final EditText username = (EditText) view.findViewById(R.id.add_new_name);



                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定操作的内容  将增加的物品显示在add页面 并写入数据库
                        final String ann=username.getText().toString();
                        //int count = out_manageAdapter.getItemCount();
                        addData(ann);
                        mgr.insert2(ann);
                    }
                });

                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Toast.makeText(getContext(), "取消添加",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();


        }
        return true;
    }
    //初始化子fragment
    public void initFragments(){
        oT=new out_Thing();
        oM=new out_manage();
        fragments=new Fragment[]{oT,oM};
        lastfragment=0;
        getChildFragmentManager().beginTransaction().replace(R.id.fr,oT).show(oT).addToBackStack(null).commit();

    }
    //fragment的转换
    private void switchFragment(int last,int index,String tag){
        FragmentTransaction transaction=getChildFragmentManager().beginTransaction();
        transaction.hide(fragments[last]);
        if(fragments[index].isAdded()==false){
            transaction.add(R.id.fr,fragments[index]);
            transaction.addToBackStack(tag);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initFragments();//网络数据刷新
        }
    }
    //添加分组名字
    public void addData(String name) {
        //在list中添加数据，并通知条目加入一条
        list.add(name);
        //添加动画
        //notifyItemInserted(loca);
        //notifyItemRangeChanged(loca,list.size()-loca);
    }

    //删除数据
    /*public void deletData(int loca){
        list.remove(loca);
        notifyItemRemoved(loca);
        notifyDataSetChanged();
    }*/


}
