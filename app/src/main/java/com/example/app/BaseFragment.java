package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public abstract class BaseFragment extends Fragment {

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imageView;
    ImageView ivDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.base_fragment,container,false);
        collapsingToolbarLayout=(CollapsingToolbarLayout)view.findViewById(R.id.collapsingToolBarLayout);
        collapsingToolbarLayout.setTitle(setTitle());
        imageView=(ImageView) view.findViewById(R.id.iv_background);
        imageView.setBackgroundResource(setBackground());
        ivDialog=(ImageView)view.findViewById(R.id.iv_dialog);
        ivDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("title",setTitle());
                intent.setClass(getActivity(),ChartActivity.class);
                startActivity(intent);
            }
        });
        initView(view);
        initData(view);
        return view;
    }
    public abstract void initView(View view);
    public abstract void initData(View view);
    public  abstract String setTitle();
    public abstract int setBackground();

}
