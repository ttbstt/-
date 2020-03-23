package com.example.myapplication.ui.out;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DBManager;
import com.example.myapplication.R;
import com.example.myapplication.Thing;

import java.util.ArrayList;

public class real_out extends Fragment {
    private View view;
    private ImageView img1;
    private Fragment oT;
    private RecyclerView real_out;
    private real_outAdapter real_outAdapter;
    private DBManager mgr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mgr=new DBManager(getActivity());
        view = inflater.inflate(R.layout.real_out, container, false);
        img1 = view.findViewById(R.id.r1);
        oT = new out_Thing();
        initRecycleView();
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });


        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            initRecycleView();//网络数据刷新
        }
    }

    public void initRecycleView() {
        real_out = view.findViewById(R.id.rc);
        real_outAdapter = new real_outAdapter(getActivity(), mgr.Query1());
        real_outAdapter.setmOnItemClickLister(new real_outAdapter.OnItemClickLister() {
            @Override
            public void onClick(int position, String name) {
                real_outAdapter.deletData(position);
                mgr.Delete_DB1(name);
            }
        });
        real_out.setAdapter(real_outAdapter);
        real_out.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
