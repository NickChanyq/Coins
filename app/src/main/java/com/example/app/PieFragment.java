package com.example.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：MyApplication
 * 包名：com.example.app
 * 文件名：PieFragment
 * 创建者：wsg
 * 创建时间：2017/7/16  16:31
 * 描述：TODO
 */

public class PieFragment extends Fragment implements OnChartValueSelectedListener {
    private static final String DATA_KEY = "piefragment_data_key";
    private MonthBean mData;
    private PieChart mChart;
    private TextView des_tv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments=getArguments();
        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate=inflater.inflate(R.layout.fragment_pie,null);
        mChart = (PieChart) inflate.findViewById(R.id.chart);
        des_tv=(TextView)inflate.findViewById(R.id.tv_des);
        initView();
        return inflate;
    }

    private void initView() {
        setData();

        mChart.setCenterText(getCenterText());

        mChart.setDrawSliceText(false);
        mChart.getData().getDataSet().setDrawValues(false);
        //没有下面的描述
        mChart.getLegend().setEnabled(false);
        //设置详情，也就什么读不显示了
        mChart.setDescription("");
        //禁止旋转
        mChart.setRotationEnabled(false);
        //添加点击事件
        mChart.setOnChartValueSelectedListener(this);
    }

    private void setData() {
        List<String> titles = new ArrayList<>();
        List<Entry> entrys = new ArrayList<>();
        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            titles.add(pieBean.title);
            entrys.add(new Entry(pieBean.value, i));
        }
        PieDataSet dataSet = new PieDataSet(entrys, "");
        //设置颜色背景
        dataSet.setColors(new int[]{Color.rgb(216, 77, 79), Color.rgb(183, 56, 63), Color.rgb(247, 85, 47)});
        PieData pieData = new PieData(titles, dataSet);
        //设置字体大小
        pieData.setValueTextSize(22);
        mChart.setData(pieData);
    }


    public static PieFragment newInstance(MonthBean data) {
        
        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        float p=360f/mData.getSum();;
        float a = 90-mData.obj.get(e.getXIndex()).value*p/2-mData.getSum(e.getXIndex())*p;
        mChart.setRotationAngle(a);
        upDateDesText(e.getXIndex());
    }

    private void upDateDesText(int index) {
        des_tv.setText(mData.obj.get(index).title+":"+mData.obj.get(index).value);
    }

    @Override
    public void onNothingSelected() {

    }




    private CharSequence getCenterText() {
        CharSequence centerText = "总支出\n" + mData.getSum() + "元";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(64, true), 3, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
