package com.example.myapplication.ui.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DBManager;
import com.example.myapplication.R;
import com.example.myapplication.Thing;

import java.util.ArrayList;

public class findFragment extends Fragment {
    private View view;
    private RecyclerView mcollectRecycleview;
    private ArrayList<Thing> list=new ArrayList<Thing>();
    private CollectRecycleAdapter mCollectRecycleAdapter;
    private DBManager mgr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mgr=new DBManager(getActivity());
        view=inflater.inflate(R.layout.find,container,false);
        Toolbar toolbar = (Toolbar)view.findViewById (R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        initRecycle();
        initData();
        return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.memu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                Toast.makeText(getActivity(), "连接成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                break;
        }
        return true;
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initRecycle();//网络数据刷新
        }
    }
    private void initRecycle(){
        mcollectRecycleview=(RecyclerView)view.findViewById(R.id.collect_recycleview);
        mCollectRecycleAdapter=new CollectRecycleAdapter(getActivity(),list);
        mcollectRecycleview.setAdapter(mCollectRecycleAdapter);
        mcollectRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCollectRecycleAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
            /*public void onClick(int position) {
                Toast.makeText(getActivity(),"您点击了"+position+"行",Toast.LENGTH_SHORT).show();
            }*/

            @Override
            public void onLongClick(int position,String name1) {
                Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                mgr.insert(name1);
                //mgr.Binding("111",position);

            }
        });
    }
    private void initData(){
        for(int i=0;i<6;i++){
            Thing cup=new Thing("水杯",R.drawable.cup);
            list.add(cup);
            Thing wallent=new Thing("钱包",R.drawable.wallent);
            list.add(wallent);
            Thing key=new Thing("钥匙",R.drawable.key);
            list.add(key);
            Thing dryer=new Thing("吹风机",R.drawable.dryer);
            list.add(dryer);
            Thing computer=new Thing("计算器",R.drawable.j1);
            list.add(computer);
        }

    }
}
