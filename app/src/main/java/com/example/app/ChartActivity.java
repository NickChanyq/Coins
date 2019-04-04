package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.DisplayMetrics;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ChartActivity extends Activity {

    PieChart pieChart;
    List<CostBean> mCostList;
    Calendar mCalendar;
    ImageView background;
    CollapsingToolbarLayout toolbar;
    String title;
    int total=0;
    private  static  String[] items=new String[]{"电影","运动","网络","交通","宠物","零食","旅游","吃饭","约会","书籍","婴儿","饮品"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");  //获得跳转的Fragment
        getList();           //根据跳转的Fragment获得对应CostList
        setToolbarAndBackground();  //根据跳转的Fragment设置背景和标题
        setPieChart();    //根据跳转的Fragment设置饼状图

    }

    private  void setPieChart(){
        pieChart=(PieChart) findViewById(R.id.pie_chart);
        pieChart.setHoleRadius(55f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(5f);
        pieChart.setDescription("By Nick");
        pieChart.setTransparentCircleColor(Color.parseColor("#551A8B"));
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setData(getPieData());
        Legend legend=pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(5f);
        pieChart.animateXY(1000,1000);
        pieChart.setCenterText("Total:¥"+total);
        pieChart.invalidate();
    }

    private PieData getPieData(){
        ArrayList<String> xValues=new ArrayList<>();
        ArrayList<Entry> yValues=new ArrayList<Entry>();
        for(int i=0;i<items.length;i++){
            xValues.add(items[i]);
        }      //处理item名称
          int[] yy=handleYValues();
        for(int i=0;i<yy.length;i++){
            Log.d("Nick",xValues.get(i)+" "+yy[i]);
        }
        for(int i=0;i<yy.length;i++){
            yValues.add(new Entry(yy[i],i));}
            PieDataSet pieDataSet=new PieDataSet(yValues,"");
            pieDataSet.setSliceSpace(0f);
            ArrayList<Integer> colors=new ArrayList<>();
            colors.add(Color.rgb(255,102,0));
            colors.add(Color.rgb(255,102,102));
            colors.add(Color.rgb(153,255,0));
            colors.add(Color.rgb(102,153,204));
            colors.add(Color.rgb(204,0,51));
            colors.add(Color.parseColor("#458B00"));
            colors.add(Color.rgb(238,238,0));
            colors.add(Color.rgb(224,255,255));
            colors.add(Color.rgb(122,139,139));
            colors.add(Color.parseColor("#FF3E96"));
            colors.add(Color.parseColor("#33ffcc"));
            colors.add(Color.parseColor("#551A8B"));
            pieDataSet.setColors(colors);
            DisplayMetrics metrics=getResources().getDisplayMetrics();
            float px=5*(metrics.densityDpi/160f);
            pieDataSet.setSelectionShift(px);
            PieData pieData=new PieData(xValues,pieDataSet);
            return pieData;

    }
    private void getList(){
        mCalendar=Calendar.getInstance();
        switch (title){
            case "CoCoin_Today":
                mCostList = DataSupport.where("year=? and month=? and day=?", mCalendar.get(Calendar.YEAR) + "",mCalendar.get(Calendar.MONTH) + 1+"",mCalendar.get(Calendar.DAY_OF_MONTH)+"").
                        find(CostBean.class);
                break;
            case "CoCoin_Yesterday":
                int yesterday = mCalendar.get(Calendar.DAY_OF_YEAR) - 1;
                if(yesterday==0){
                    mCostList=DataSupport.where("year=?",mCalendar.get(Calendar.YEAR)-1+"").
                            where("month=?",12+"").
                            where("day=?",31+"").
                            find(CostBean.class);
                }else{
                    mCostList = DataSupport.where("day_of_year=? and year=?", yesterday + "",mCalendar.get(Calendar.YEAR)+"").
                            find(CostBean.class);
                }
                break;
            case "CoCoin_ThisYear":
                mCostList = DataSupport.where("year=?", mCalendar.get(Calendar.YEAR) + "").
                        find(CostBean.class);
                break;
            case"CoCoin_ThisWeek":
                mCostList = DataSupport.where("week_of_year=? and year=?", mCalendar.get(Calendar.WEEK_OF_YEAR) + "",mCalendar.get(Calendar.YEAR)+"").
                        find(CostBean.class);
                break;
            case"CoCoin_ThisMonth":
                mCostList = DataSupport.where("year=? and month=?", mCalendar.get(Calendar.YEAR) + "",mCalendar.get(Calendar.MONTH) + 1 + "").
                        find(CostBean.class);
                break;
            case"CoCoin_LastYear":
                int lastYear = mCalendar.get(Calendar.YEAR) - 1;
                mCostList = DataSupport.where("year=?",lastYear + "").
                        find(CostBean.class);
                break;
            case "CoCoin_LastWeek":
                int lastWeek=mCalendar.get(Calendar.WEEK_OF_YEAR)-1;
                if(lastWeek==0){
                    //跨年的上周怎么算？一年有52或53周
                    mCostList=DataSupport.where("year=? and week_of_year=?",mCalendar.get(Calendar.YEAR)-1+"",52+"").
                            find(CostBean.class);
                }
                else{
                    mCostList=DataSupport.where("year=? and week_of_year=?",mCalendar.get(Calendar.YEAR)+"",mCalendar.get(Calendar.WEEK_OF_YEAR)-1+"").
                            find(CostBean.class);
                }
                break;
            case"CoCoin_LastMonth":
                int lastMonth = mCalendar.get(Calendar.MONTH)+1-1 ;
                if(lastMonth==0){
                    mCostList=DataSupport.where("year=? and month=?",mCalendar.get(Calendar.YEAR)-1+"",12+"").find(CostBean.class);
                }else{
                    mCostList = DataSupport.where("year=? and month=?", mCalendar.get(Calendar.YEAR) + "",lastMonth+"")
                            .find(CostBean.class);
                }
                break;
        }

    }

    private void setToolbarAndBackground(){
//        colors.add(Color.rgb(255,102,0));
//        colors.add(Color.rgb(255,102,102));
//        colors.add(Color.rgb(153,255,0));
//        colors.add(Color.rgb(102,153,204));
//        colors.add(Color.rgb(204,0,51));
//        colors.add(Color.parseColor("#458B00"));
//        colors.add(Color.rgb(238,238,0));
//        colors.add(Color.rgb(224,255,255));
//        colors.add(Color.rgb(122,139,139));
//        colors.add(Color.parseColor("#FF3E96"));
//        colors.add(Color.parseColor("#33ffcc"));
//        colors.add(Color.parseColor("#551A8B"));
        toolbar=(CollapsingToolbarLayout) findViewById(R.id.chart_collapsingToolBarLayout);
        toolbar.setTitle(title);
        background=(ImageView)findViewById(R.id.chart_iv_background);
        switch (title){
            case "CoCoin_Today":
                background.setBackgroundColor(Color.rgb(255,102,0));
                break;
            case "CoCoin_Yesterday":
                background.setBackgroundColor(Color.rgb(255,102,102));
                break;
            case "CoCoin_ThisYear":
                background.setBackgroundColor(Color.rgb(153,255,0));
                break;
            case"CoCoin_ThisWeek":
                background.setBackgroundColor(Color.rgb(102,153,204));
                break;
            case"CoCoin_ThisMonth":
                background.setBackgroundColor(Color.parseColor("#551A8B"));
                break;
            case"CoCoin_LastYear":
                background.setBackgroundColor(Color.parseColor("#458B00"));
                break;
            case "CoCoin_LastWeek":
                background.setBackgroundColor(Color.DKGRAY);
                break;
            case"CoCoin_LastMonth":
                background.setBackgroundColor(Color.MAGENTA);
                break;
        }
    }

    private int[] handleYValues(){
//        "电影","运动","网络","交通","宠物","零食","旅游","吃饭","约会","书籍","婴儿","饮品"
        int[] yy=new int[12];
        for(int i=0;i<yy.length;i++){
            yy[i]=0;
        }
        CostBean temp=new CostBean();
        for(int i=0;i<mCostList.size();i++){
            temp=mCostList.get(i);
            total=total+Integer.parseInt(temp.getMoney());
            switch (temp.getItem()){
                case "电影" :
                    yy[0]=yy[0]+Integer.parseInt(temp.getMoney());
                     break;
                case "运动" :
                    yy[1]=yy[1]+Integer.parseInt(temp.getMoney());
                    break;
                case "网络" :
                    yy[2]=yy[2]+Integer.parseInt(temp.getMoney());
                    break;
                case "交通" :
                    yy[3]=yy[3]+Integer.parseInt(temp.getMoney());
                    break;
                case "宠物" :
                    yy[4]=yy[4]+Integer.parseInt(temp.getMoney());
                    break;
                case "零食" :
                    yy[5]=yy[5]+Integer.parseInt(temp.getMoney());
                    break;
                case "旅游" :
                    yy[6]=yy[6]+Integer.parseInt(temp.getMoney());
                    break;
                case "吃饭" :
                    yy[7]=yy[7]+Integer.parseInt(temp.getMoney());
                    break;
                case "约会" :
                    yy[8]=yy[8]+Integer.parseInt(temp.getMoney());
                    break;
                case "书籍" :
                    yy[9]=yy[9]+Integer.parseInt(temp.getMoney());
                    break;
                case "婴儿" :
                    yy[10]=yy[10]+Integer.parseInt(temp.getMoney());
                    break;
                case "饮品" :
                    yy[11]=yy[11]+Integer.parseInt(temp.getMoney());
                    break;

            }
        }
        return yy;
    }

}


