package com.example.app;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.example.app.Fragment.LastMonthFragment;
import com.example.app.Fragment.LastWeekFragment;
import com.example.app.Fragment.LastYearFragment;
import com.example.app.Fragment.ThisMonthFragment;
import com.example.app.Fragment.ThisWeekFragment;
import com.example.app.Fragment.ThisYearFragment;
import com.example.app.Fragment.TodayFragment;
import com.example.app.Fragment.YesterdayFragment;

import java.util.ArrayList;
import java.util.List;

public class RecentCostActivity extends FragmentActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private List<Fragment> fragments;
    private List<String> titles;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_cost_activity);
        initView();
        initData();
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
    }


    private void initView(){
      viewPager=(ViewPager) findViewById(R.id.viewPager);
      pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagerTabStrip);
      pagerTabStrip.setTabIndicatorColor(this.getResources().getColor(R.color.colorPrimaryDark));
      pagerTabStrip.setTextColor(this.getResources().getColor(R.color.black));
        pagerTabStrip.setPadding(30,30,30,30);
    }

    private void initData(){
        fragments=new ArrayList<>();
        titles=new ArrayList<>();
        titles.add("今天");
        titles.add("昨天");
        titles.add("这周");
        titles.add("上周");
        titles.add("这个月");
        titles.add("上个月");
        titles.add("今年");
        titles.add("去年");
        fragments.add(new TodayFragment());
        fragments.add(new YesterdayFragment());
        fragments.add(new ThisWeekFragment());
        fragments.add(new LastWeekFragment());
        fragments.add(new ThisMonthFragment());
        fragments.add(new LastMonthFragment());
        fragments.add(new ThisYearFragment());
        fragments.add(new LastYearFragment());
    }
}
