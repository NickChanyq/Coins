package com.example.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.litepal.crud.DataSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LineActivity extends Activity {

    LineChart lineChart;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_7_days_activity);
        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.seven_collapsing_ToolBarLayout);
        collapsingToolbarLayout.setTitle("CoCoin");
        initChart();

    }

    private void  initChart(){
        lineChart=(LineChart)findViewById(R.id.line_chart);
        lineChart.setDrawGridBackground(true);
        lineChart.setDescription("by Nick");
        lineChart.setAlpha(0.8f);
        lineChart.setTouchEnabled(true);
        LineData data=getLineData();
        lineChart.setData(data);
        lineChart.animateX(2000);
        lineChart.animateY(1500);
        lineChart.animateXY(1500,1500);

        lineChart.invalidate();

    }

    private LineData getLineData(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int day=calendar.get(Calendar.DAY_OF_YEAR);
        List<String> xVals= getDatePeriod(new Date(), 7);
        String[] yy=new String[xVals.size()];
        List<CostBean> costList=new ArrayList<>();
        for(int i=0;i<xVals.size();i++){
            costList= DataSupport.where("year=? and day_of_year=?",year+"",day-6+i+"").find(CostBean.class);
            int total=0;
          for(int k=0;k<costList.size();k++){
          total=total+Integer.parseInt(costList.get(k).getMoney());
          }
          yy[i]=total+"";
        }
        for(int i=0;i<xVals.size();i++){
            Log.d("Nick",xVals.get(i)+" "+yy[i]);
        }
        ArrayList<Entry> yVals=new ArrayList<>();
        for (int i=0;i<yy.length;i++){
            yVals.add(new Entry(Float.parseFloat(yy[i]),i));
        }
        LineDataSet set=new LineDataSet(yVals,"过去7天花费");
        set.setCubicIntensity(0.2f);
        set.setDrawFilled(false);
        set.setDrawCircles(true);
        set.setLineWidth(2f);
        set.setCircleRadius(5f);
        set.setHighLightColor(Color.rgb(102, 255, 153));
        set.setColor(Color.rgb(51,0,153));
        return new LineData(xVals,set);
    }



        private List<String> getDatePeriod(Date date, int beforeDays){   //得到前七天的日期
            List<String> datePeriodList = new ArrayList<String>();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            for(int i=beforeDays-1;i>=0;i--){
                cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear-i);
                datePeriodList.add(dateFormat.format(cal.getTime()));
            }
            return datePeriodList;
        }


}
