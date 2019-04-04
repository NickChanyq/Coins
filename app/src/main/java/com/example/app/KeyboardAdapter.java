package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KeyboardAdapter extends BaseAdapter {
    private List<String> nums;
    private LayoutInflater layoutInflater;

    public KeyboardAdapter(Context context, List<String> nums)
    {
        this.nums=nums;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return nums.size();
    }

    @Override
    public Object getItem(int position) {
        return nums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.keyboard_item,null);
            holder=new ViewHolder();
            holder.tvItem=(TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        String num=nums.get(position);
        if(num!=null){
            holder.tvItem.setText(num);
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvItem;
    }
}

