package com.example.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp;
    private Button  pre_bt;
    private Button next_bt;


    private String mJson = "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34}," +
            "{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]}," +
            "{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"外卖\",\"value\":32}," +
            "{\"title\":\"娱乐\",\"value\":22},{\"title\":\"其他\",\"value\":42}]}," +
            "{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"外卖\",\"value\":34}," +
            "{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":24}]}," +
            "{\"date\":\"2016年8月\",\"obj\":[{\"title\":\"外卖\",\"value\":145}," +
            "{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":124}]}]";
    private ArrayList<MonthBean> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        Gson gson=new Gson();
        mData = gson.fromJson(mJson,new TypeToken<ArrayList<MonthBean>>(){}.getType());
    }

    private void initView() {
        vp=(ViewPager) findViewById(R.id.vp_main);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });


        pre_bt=(Button)findViewById(R.id.bt_pre);
        next_bt=(Button)findViewById(R.id.bt_next);
        pre_bt.setOnClickListener(this);
        next_bt.setOnClickListener(this);

        updateJumpText();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pre:
                if (vp.getCurrentItem() != 0){
                    vp.setCurrentItem(vp.getCurrentItem()-1);
                }

                break;
            case R.id.bt_next:
                if (vp.getCurrentItem() != vp.getAdapter().getCount()-1){
                    vp.setCurrentItem(vp.getCurrentItem()+1);
                }
        }



        updateJumpText();

    }

    private void updateJumpText() {

        if (vp.getCurrentItem() != vp.getAdapter().getCount()-1) {
            next_bt.setText(mData.get(vp.getCurrentItem()+1).data);
//            Log.i("wsg", object.get(vp.getCurrentItem()+1).data);
        } else {
            next_bt.setText("没有了！");
        }
        if (vp.getCurrentItem() != 0) {
            pre_bt.setText(mData.get(vp.getCurrentItem()-1).data);
//            Log.i("wsg", object.get(vp.getCurrentItem()-1).data);
        } else {
            pre_bt.setText("没有了！");
        }
    }








}
