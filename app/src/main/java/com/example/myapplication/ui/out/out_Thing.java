package com.example.myapplication.ui.out;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DBManager;
import com.example.myapplication.R;
import com.example.myapplication.Thing;

public class out_Thing extends Fragment implements out_ThingAdapter.AddClickListener, Animator.AnimatorListener {
    private View view;
    private ImageView shopImg;//出行书包的照片
    private ListView itemLv;
    private ImageView imageView;
    private RelativeLayout container;//ListView,出行书包的父布局;
    private DBManager mgr;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mgr=new DBManager(getActivity());

        view=inflater.inflate(R.layout.out_thing,container,false);
        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        shopImg=view.findViewById(R.id.main_img);
        shopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment real_out=new real_out();
                ft.replace(R.id.fr,real_out);
                ft.addToBackStack(null);
                ft.commit();


            }
        });


        findViews();
        initViews();
        return view;
    }
    public void initViews(){
        out_ThingAdapter adapter=new out_ThingAdapter(getActivity(),mgr.Query());
        //当前fragment实现adapter内部点击的回调
        adapter.setListener(this);
        itemLv.setAdapter(adapter);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //动画结束后 父布局移除 img
        Object target = ((ObjectAnimator) animation).getTarget();
        container.removeView((View) target);
        //shopImg 开始一个放大动画
        Animation scaleAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shop_main);
        shopImg.startAnimation(scaleAnim);


    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
    //显示减号的动画
   /*private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(1000);
        return set;
    }
    //隐藏减号的动画
    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }*/
    @Override
    public void add(View addV, Thing thing) {
        //imageView=itemLv.findViewById(R.id.item_img1);
        int[] childCoordinate = new int[2];
        int[] parentCoordinate = new int[2];
        int[] shopCoordinate = new int[2];
        //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
        addV.getLocationInWindow(childCoordinate);
        container.getLocationInWindow(parentCoordinate);
        shopImg.getLocationInWindow(shopCoordinate);
        //2.自定义ImageView 继承ImageView
        MoveImageView img = new MoveImageView(getActivity());
        img.setImageResource(R.drawable.ic_yellow);
        //3.设置img在父布局中的坐标位置
        img.setX(childCoordinate[0] - parentCoordinate[0]);
        img.setY(childCoordinate[1] - parentCoordinate[1]);
        //4.父布局添加该Img
        container.addView(img);
        //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();
        //开始的数据点坐标就是 addV的坐标
        startP.x = childCoordinate[0] - parentCoordinate[0];
        startP.y = childCoordinate[1] - parentCoordinate[1];
        //结束的数据点坐标就是 shopImg的坐标
        endP.x = shopCoordinate[0] - parentCoordinate[0];
        endP.y = shopCoordinate[1] - parentCoordinate[1];
        //控制点坐标 x等于 购物车x；y等于 addV的y
        controlP.x = endP.x;
        controlP.y = startP.y;
        //启动属性动画
        ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        animator.setDuration(1000);
        animator.addListener(this);
        animator.start();
        Toast.makeText(getActivity(),"添加了"+thing.getName(),Toast.LENGTH_LONG).show();
        mgr.insert1(thing);
        /*if(addV.getVerticalScrollbarPosition()==position) {
            if (imageView.getVisibility() == View.INVISIBLE) {
                imageView.setVisibility(View.VISIBLE);
                imageView.clearAnimation();

            } else {
            }
            MoveImageView img1 = new MoveImageView(getActivity());
            img1.setImageResource(R.drawable.decrease);
            img1.setMPointF(startP);
            itemLv.addView(img1,25,25);
            img1.setAnimation(getShowAnimation());
            img1.startAnimation(getShowAnimation());
            //img1.setVisibility(View.VISIBLE);
        }*/
        /*MoveImageView img1 = new MoveImageView(getActivity());
        img1.setImageResource(R.drawable.decrease);
        img1.setMPointF(startP);
        itemLv.addView(img1,25,25);
        img1.setAnimation(getShowAnimation());
        img1.startAnimation(getShowAnimation());
        img1.setVisibility(view.getVisibility());*/





    }
    /**     * 自定义估值器     */
    public class PointFTypeEvaluator implements TypeEvaluator<PointF> {
        /**         * 每个估值器对应一个属性动画，每个属性动画仅对应唯一一个控制点         */
        PointF control;        /**         * 估值器返回值         */
        PointF mPointF = new PointF();
        public PointFTypeEvaluator(PointF control) {
            this.control = control;
        }
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return getBezierPoint(startValue, endValue, control, fraction);
        }
        /**         * 二次贝塞尔曲线公式         *
         *  * @param start   开始的数据点
         *  * @param end     结束的数据点
         *  * @param control 控制点
         *  * @param t       float 0-1
         *  * @return 不同t对应的PointF         */
        private PointF getBezierPoint(PointF start, PointF end, PointF control, float t) {
            mPointF.x = (1 - t) * (1 - t) * start.x + 2 * t * (1 - t) * control.x + t * t * end.x;
            mPointF.y = (1 - t) * (1 - t) * start.y + 2 * t * (1 - t) * control.y + t * t * end.y;
            return mPointF;
        }
    }

    public void delete(Thing thing){
        Toast.makeText(getActivity(),"删除了"+thing.getName(),Toast.LENGTH_LONG).show();
        mgr.Delete_DB1(thing.getName());

    }
    private void findViews() {
        shopImg = (ImageView) view.findViewById(R.id.main_img);
        container = (RelativeLayout) view.findViewById(R.id.main_container);
        itemLv = (ListView)view.findViewById(R.id.main_lv);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initViews();//网络数据刷新
        }
    }
}


