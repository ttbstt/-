package com.example.myapplication.ui.out;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.widget.ImageView;


@SuppressLint("AppCompatCustomView")
public class MoveImageView extends ImageView{
    public MoveImageView(Context context) {
        super(context);
    }
    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }

}
