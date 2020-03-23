package com.example.myapplication.ui.out;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DBManager;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

//out_manage页面对应的fragment
public class out_manage extends Fragment implements out_manageAdapter.AddClickListener {
    private ExpandableListView expand_list_id;
    private DBManager mgr;
    private ImageView addimg;


    //Model：定义的数据
    List<String> list= new ArrayList<String>();


   // private String[] groups = {"开发部", "人力资源部", "销售部"};
    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    private String[][] childs = {{"赵珊珊", "钱丹丹", "孙可可", "李冬冬"},
            {"周大福", "吴端口", "郑非", "王疯狂"}, {"冯程程", "陈类", "楚哦", "魏王"}};

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mgr=new DBManager(getActivity());
        view=inflater.inflate(R.layout.out_manage,container,false);
        expand_list_id = view.findViewById(R.id.expand_ListId);
        initView();
        return view;
    }
    private void initView() {
        out_manageAdapter adapter = new out_manageAdapter(getActivity(), mgr.Query2(), childs);
        //当前fragment实现adapter内部点击的回调
        adapter.setListener(this);
        expand_list_id.setAdapter(adapter);
        //默认展开第一个数组
        expand_list_id.expandGroup(0);
        //关闭数组某个数组，可以通过该属性来实现全部展开和只展开一个列表功能
        expand_list_id.collapseGroup(0);
        expand_list_id.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                //showToastShort(groups[groupPosition]);
                //Toast.makeText(getActivity(),"数据"+groups[groupPosition],Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        //子视图的点击事件
        expand_list_id.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                //showToastShort(childs[groupPosition][childPosition]);
                Toast.makeText(getActivity(),"数据"+childs[groupPosition][childPosition],Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        //用于当组项折叠时的通知。
        expand_list_id.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //showToastShort("折叠了数据___" + groups[groupPosition]);
                //Toast.makeText(getActivity(),"折叠了数据"+groups[groupPosition],Toast.LENGTH_SHORT).show();

            }
        });        //
        // 用于当组项折叠时的通知。
        expand_list_id.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //showToastShort("展开了数据___" + groups[groupPosition]);
                //Toast.makeText(getActivity(),"展开了数据"+groups[groupPosition],Toast.LENGTH_SHORT).show();
            }
        });

    }
    //接口回调实现弹出一个listview显示product中物品，经选择后放入分组
    public void add(){
        Toast.makeText(getActivity(),"爱啊啊啊啊啊",Toast.LENGTH_SHORT).show();
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        LayoutInflater inflater=LayoutInflater.from(getContext());//将xml布局转换为view
        View view1=inflater.inflate(R.layout.omclistview, null);

        //final View view1 = LayoutInflater.from(getContext()).inflate(R.layout.omclistview,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("请输入要添加的物品名称");

        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view1);
        final ListView lv=view1.findViewById(R.id.omclistvie);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        final omcListViewAdapter omc=new omcListViewAdapter(getContext(),mgr.Query());
        lv.setAdapter(omc);
        //inal CheckBox c=view.findViewById(R.id.rad1);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                omc.notifyDataSetChanged();
                //Toast.makeText(getContext(), "哈哈哈哈",Toast.LENGTH_SHORT).show();
            }
        });



        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                long[] ids=lv.getCheckedItemIds();
                //得到选中的itemId
                String str="";
                for(int i=0;i<ids.length;i++){
                    str+=ids[i]+",";
                }
                Log.d(TAG, "hhh"+str);
                Toast.makeText(getContext(),str, Toast.LENGTH_LONG).show();
                //确定操作的内容  将增加的物品显示在add页面 并写入数据库
                //Toast.makeText(getContext(), "添加成功" ,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(),"dianannananana",Toast.LENGTH_SHORT).show();

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
        builder.create().show();



    }
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initView();//网络数据刷新
        }
    }



}
