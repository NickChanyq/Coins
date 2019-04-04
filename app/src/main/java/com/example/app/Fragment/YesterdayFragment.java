package com.example.app.Fragment;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.app.BaseFragment;
import com.example.app.CostBean;
import com.example.app.CostBeanAdapter;
import com.example.app.DividerItemDecoration;
import com.example.app.R;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

public class YesterdayFragment extends BaseFragment {
    RecyclerView recyclerView;
    Calendar mCalendar;
    CostBeanAdapter adapter;
    List<CostBean> mCostList;

    @Override
    public int setBackground() {
        return R.mipmap.material_design_0;
    }

    @Override
    public String setTitle() {
        return "CoCoin_Yesterday";
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


    }

    @Override
    public void initData(View view) {
        mCalendar = Calendar.getInstance();
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
        adapter = new CostBeanAdapter(mCostList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL_LIST));
        recyclerView.setBackgroundColor(Color.rgb(	230,230,250));
    }
}

