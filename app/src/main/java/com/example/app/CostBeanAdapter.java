package com.example.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CostBeanAdapter extends RecyclerView.Adapter<CostBeanAdapter.ViewHolder> {
    private List<CostBean> mCostList;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        View viewCost;
        ImageView ivCostItem;
        TextView tvCostMoney;
        TextView tvCostDate;
     public  ViewHolder(View view){
       super(view);
       viewCost=view;
       ivCostItem=(ImageView) view.findViewById(R.id.iv_cost_item);
     tvCostDate=(TextView)view.findViewById(R.id.tv_cost_date) ;
     tvCostMoney=(TextView)view.findViewById(R.id.tv_cost_money);
      }
    }
    public CostBeanAdapter(List<CostBean> list){
        this.mCostList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cost_bean_item,parent,false);
       final ViewHolder holder=new ViewHolder(view);
       holder.viewCost.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               AlertDialog.Builder builder=new AlertDialog.Builder(parent.getContext());
               builder.setTitle("CoCoin");
               builder.setMessage("确定要删除这项花费吗？");
               builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                         int pos=holder.getAdapterPosition();
                         CostBean bean=mCostList.get(pos);
                       DataSupport.deleteAll(CostBean.class,"day_of_year=? and item=? and money=?",
                               bean.getDay_of_year()+"",bean.getItem()+"",bean.getMoney()+"");
                         mCostList.remove(pos);
//                         notifyDataSetChanged();
                       notifyItemRemoved(pos);   //recylerView删除动画要用这个
                   }
               });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               Dialog dialog=builder.create();
               dialog.show();
               Button btnPos=((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
               Button btnNeg = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
               btnPos.setTextColor(Color.BLUE);
               btnNeg.setTextColor(Color.GRAY);
               return false;
           }
       });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CostBean costBean=mCostList.get(position);
        holder.ivCostItem.setImageResource(costBean.getLogo());
        holder.tvCostMoney.setText("¥"+costBean.getMoney()+"消费于");
        holder.tvCostDate.setText(costBean.getYear()+"/"+costBean.getMonth()+"/"+costBean.getDay());
    }

    @Override
    public int getItemCount() {
        return mCostList.size();
    }
}
