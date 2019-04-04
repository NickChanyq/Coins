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

public class LastWeekFragment extends BaseFragment {
    RecyclerView recyclerView;
    Calendar mCalendar;
    CostBeanAdapter adapter;
    List<CostBean> mCostList;
    @Override
    public int setBackground() {
        return R.mipmap.material_design_1;
    }

    @Override
    public String setTitle() {
        return "CoCoin_LastWeek";
    }
    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void initData(View view) {
        mCalendar = Calendar.getInstance();
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

        adapter = new CostBeanAdapter(mCostList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL_LIST));
        recyclerView.setBackgroundColor(Color.rgb(	230,230,250));
    }
}