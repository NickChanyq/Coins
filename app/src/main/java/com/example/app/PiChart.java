package com.example.app;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.Utils;

/**
 * 项目名：MyApplication
 * 包名：com.example.app
 * 文件名：PiChart
 * 创建者：wsg
 * 创建时间：2017/7/17  9:11
 * 描述：TODO
 */

public class PiChart extends PieChart {
    public PiChart(Context context) {
        super(context);
    }

    public PiChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PiChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int size = (int) Utils.convertDpToPixel(50f);
        int size = (int) Utils.convertDpToPixel(MeasureSpec.getSize(widthMeasureSpec));
        setMeasuredDimension(
                Math.max(getSuggestedMinimumWidth(),
                        resolveSize(size,
                                widthMeasureSpec)),
//                Math.max(getSuggestedMinimumHeight(),
//                        resolveSize(size,
//                                heightMeasureSpec)));
                Math.max(getSuggestedMinimumHeight(),
                        resolveSize(size,
                                widthMeasureSpec)));
    }


}
