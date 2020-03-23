package com.example.myapplication.ui.add;

import com.example.myapplication.DeviceListActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DBManager;
import com.example.myapplication.R;
import com.example.myapplication.Thing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class addFragment extends Fragment {
    private View view;
    private RecyclerView addRecycleView;
    private ArrayList<Thing> list=new ArrayList<Thing>();
    private addRecycleAdapter addAdapter;
    private DBManager mgr;
    private TextView textview;//接受消息;


    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号


    //    private ScrollView scrollview;//翻页
//    private TextView textview;//接受消息
    private InputStream acceptbluetooth;//输入流，用来接收蓝牙数据
    boolean bRun = true;
    boolean bThread = false;

    private String showdatacache="";
    private String savedatacache="";


    BluetoothDevice mdevice = null;
    BluetoothSocket msocket = null;

    private BluetoothAdapter mbluetooth = BluetoothAdapter.getDefaultAdapter();    //获取本地蓝牙适配器，即蓝牙设备




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mgr=new DBManager(getActivity());
        view=inflater.inflate(R.layout.add,container,false);
        Toolbar toolbar = (Toolbar)view.findViewById (R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        initRecycleView();
        //initData();
        //updateRecycleView();
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
                if(mbluetooth.isEnabled()==false){  //如果蓝牙服务不可用则提示
                    Toast.makeText(getActivity(), " 打开蓝牙中...", Toast.LENGTH_LONG).show();
                    break;
                }
                //如未连接设备则打开DeviceListActivity进行设备搜索
                if(msocket==null){
                    Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class); //跳转程序设置
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
                }
                else{
                    //关闭连接socket
                    try{
                        bRun = false;
                        Thread.sleep(2000);

                        acceptbluetooth.close();
                        msocket.close();
                        msocket = null;
                    }catch(IOException e){}
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Toast.makeText(getActivity(),"连接成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                if(msocket!=null){
                    //关闭连接socket
                    try{
                        bRun = false;
                        Thread.sleep(2000);

                        acceptbluetooth.close();
                        msocket.close();
                        msocket = null;
                    }catch(IOException e){}
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
                        int count = addAdapter.getItemCount();
                        addAdapter.addData(count,ann);
                        mgr.insert(ann);
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


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initRecycleView();//网络数据刷新
        }
    }
    private void initRecycleView(){
        addRecycleView=(RecyclerView)view.findViewById(R.id.add_recycleview);
        addRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*addAdapter=new addRecycleAdapter(getActivity(), mgr.Query0(), new addRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                int colum =  pos;
                //mgr.Binding(showdatacache,colum);
                mgr.Binding("111",colum);
                Toast.makeText(getActivity(),"绑定成功！"+pos,Toast.LENGTH_SHORT).show();
                //单击触发将物品写入表中

            }
        });*/
        addAdapter=new addRecycleAdapter(getActivity(),mgr.Query0());
        addRecycleView.setAdapter(addAdapter);
        addAdapter.setmOnItemClickListener(new addRecycleAdapter.OnItemClickListener() {

            @Override
            public void onClick(int pos,String name) {
                //int colum =  pos;
                //mgr.Binding(showdatacache,colum);
                mgr.Binding("111",name);//用来测试的
                Toast.makeText(getActivity(),"绑定成功！"+pos,Toast.LENGTH_SHORT).show();
                //单击触发将物品写入表中

            }
        });

        //长按 删除所选项
        addAdapter.setOnItemClickListener(new addRecycleAdapter.OnLongItemClickListener() {
            /*public void onClick(int position) {
                Toast.makeText(getActivity(),"您点击了"+position+"行",Toast.LENGTH_SHORT).show();
            }*/

            @Override
            public void onLongClick(int position,String name1) {
                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                //mgr.insert(name1);  //将find页面选中的项，写到数据库中
                addAdapter.deletData(position);
                mgr.Delete_DB(name1);


            }
        });


    }
    /*private void updateRecycleView(){
        addRecycleView=(RecyclerView)view.findViewById(R.id.add_recycleview);
        addAdapter=new addRecycleAdapter(getActivity(),mgr.Query());

    }*/

    //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case REQUEST_CONNECT_DEVICE:
                // 响应返回结果                            //连接结果，由DeviceListActivity设置返回
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    mdevice = mbluetooth.getRemoteDevice(address);

                    // UUID得到socket
                    try{
                        msocket = mdevice.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    }catch(IOException e){
                        Toast.makeText(getActivity(), "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
                    try{
                        msocket.connect();
                        Toast.makeText(getActivity(), "连接"+mdevice.getName()+"成功！", Toast.LENGTH_SHORT).show();

                    }catch(IOException e){
                        try{
                            Toast.makeText(getActivity(), "连接失败！", Toast.LENGTH_SHORT).show();
                            msocket.close();
                            msocket = null;
                        }catch(IOException ee){
                            Toast.makeText(getActivity(), "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }

                    //打开接收线程
                    try{
                        acceptbluetooth = msocket.getInputStream();   //得到蓝牙数据输入流
                    }catch(IOException e){
                        Toast.makeText(getActivity(), "接收数据失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(bThread==false){
                        readThread.start();
                        bThread=true;
                    }else{
                        bRun = true;
                    }
                }
                break;
            default:break;
        }
    }


    //接收数据线程
    Thread readThread=new Thread(){

        public void run(){
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            bRun = true;
            //接收线程
            while(true){
                try{
                    while(acceptbluetooth.available()==0){
                        while(bRun == false){}
                    }
                    while(true){
                        if(!bThread)//跳出循环
                            return;
                        num = acceptbluetooth.read(buffer);  //读入数据
                        n=0;

                        String s0 = new String(buffer,0,num);
                        savedatacache+=s0;    //保存收到数据 0x0d 回车
                        for(i=0;i<num;i++){
                            if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
                                buffer_new[n] = 0x0a;
                                i++;
                            }else{
                                buffer_new[n] = buffer[i];
                            }
                            n++;
                        }
                        String s = new String(buffer_new,0,n);

                        //将得到的字符串转化为十六进制
                        showdatacache=str2HexStr(s);

                        //showdatacache+=s;   //写入接收缓存
                        if(acceptbluetooth.available()==0)break;  //短时间没有数据才跳出进行显示
                    }
                    //发送显示消息，进行显示刷新
                    //handler.sendMessage(handler.obtainMessage());
                }catch(IOException e){
                }
            }
        }
    };

//    Handler handler= new Handler(){
//        public void handleMessage(Message msg){
//            super.handleMessage(msg);
//            number.setText(showdatacache);   //显示数据
//            scrollview.scrollTo(0,textview.getMeasuredHeight()); //跳至数据最后一页
//        }
//    };


    public static String str2HexStr(String s)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = s.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }


}
