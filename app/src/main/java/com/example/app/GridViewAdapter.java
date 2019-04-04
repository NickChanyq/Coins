package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private List<CostLogo> costs;
    private LayoutInflater layoutInflater;

    public GridViewAdapter(Context context,List<CostLogo> costs)
    {
        this.costs=costs;
       layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return costs.size();
    }

    @Override
    public Object getItem(int position) {
        return costs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder=null;
           if(convertView==null){
               convertView=layoutInflater.inflate(R.layout.gw_item,null);
               holder=new ViewHolder();
               holder.ivLogo=(ImageView) convertView.findViewById(R.id.iv_cost);
               holder.tvItem=(TextView) convertView.findViewById(R.id.tv_cost);
               convertView.setTag(holder);
           }
           else {
               holder=(ViewHolder)convertView.getTag();
           }
            CostLogo costLogo=costs.get(position);
           if(costLogo!=null){
               holder.ivLogo.setImageResource(costLogo.getItemLogo());
               holder.tvItem.setText(costLogo.getItem());
           }
        return convertView;
    }
    class ViewHolder{
        ImageView ivLogo;
        TextView tvItem;
    }
}
