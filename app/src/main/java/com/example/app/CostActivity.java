package com.example.app;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class CostActivity extends Activity {

        DrawerLayout mDrawerLayout;
        ImageButton  menuButton;
        GridView mGridView;
        GridView mKeyboardGridView;
        TextView tvChosenNums;
        ImageView ivChosenItem;
        NavigationView navView;
        TextView tvEmail;
        private  static String[] items=new String[]{"电影","运动","网络","交通","宠物","零食","旅游","吃饭","约会","书籍","婴儿","饮品"};
        private static int[] itemsPic=new int[]{R.mipmap.film_icon,R.mipmap.sport_icon,
                R.mipmap.internet_icon,R.mipmap.traffic_icon,R.mipmap.pet_icon,R.mipmap.snack_icon,
        R.mipmap.travel_icon,R.mipmap.meal_icon,R.mipmap.partner_icon,R.mipmap.book_icon,R.mipmap.baby_icon,R.mipmap.drink_icon};
        private  static String[] nums=new String[]{"1","2","3","4","5","6","7","8","9","←","0","√"};
        private static ArrayList<CostLogo> costs;
        private static ArrayList<String> numsList;
        private StringBuilder chosenNums=new StringBuilder();
        private String chosenItem;
        private  int chosenLogo;
        private  Calendar calendar;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cost);
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            LitePal.getDatabase();
            calendar=Calendar.getInstance();
            initView();
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });





        }

        private void initView(){

            mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
            menuButton=(ImageButton)findViewById(R.id.btn_menu);
            mGridView=(GridView) findViewById(R.id.gw_cost);
            mKeyboardGridView=(GridView) findViewById(R.id.gw_keyboard);
            tvChosenNums=(TextView)findViewById(R.id.tv_money);
            ivChosenItem=(ImageView) findViewById(R.id.iv_chosen_item);
            navView=(NavigationView)findViewById(R.id.nav);
//            View view=navView.getHeaderView(R.layout.nav_header);
//            TextView tvTel=(TextView)view.findViewById(R.id.tv_email);
//            SharedPreferences preferences=getSharedPreferences("UserInfo",MODE_PRIVATE);
//            if(preferences.getString("tel","").equals("")){
//
//            }else {
//            tvTel.setText("手机用户"+preferences.getString("tel",""));}
//            CircleImageView ivLogin=(CircleImageView)findViewById(R.id.iv_login);
//            ivLogin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent3=new Intent();
//                    intent3.setClass(CostActivity.this,LoginActivity.class);
//                    startActivity(intent3);
//                    mDrawerLayout.closeDrawers();
//                }
//            });
            navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.nav_cloud:
                            Intent intent3=new Intent();
                            intent3.setClass(CostActivity.this,LoginActivity.class);
                            startActivity(intent3);
                            mDrawerLayout.closeDrawers();
                            break;

                        case R.id.nav_recent:
                            Intent intent=new Intent();
                            intent.setClass(CostActivity.this,RecentCostActivity.class);
                            startActivity(intent);
                            mDrawerLayout.closeDrawers();
                            break;

                        case R.id.nav_help:
                            mDrawerLayout.closeDrawers();
                            break;

                        case R.id.nav_seven_days:
                            Intent intent2=new Intent();
                            intent2.setClass(CostActivity.this,LineActivity.class);
                            startActivity(intent2);
                            mDrawerLayout.closeDrawers();
                            break;

                        case R.id.nav_find:
                            final AlertDialog.Builder builder=new AlertDialog.Builder(CostActivity.this);
                            LayoutInflater inflate=LayoutInflater.from(CostActivity.this);
                            final View viewDialog=inflate.inflate(R.layout.date_picker_dialog, null);
                            final DatePicker picker=(DatePicker)viewDialog.findViewById(R.id.dp_costDate);

                            builder.setPositiveButton("我选好了！",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder  daydialog=new AlertDialog.Builder(CostActivity.this);
                                    LayoutInflater layoutInflater=LayoutInflater.from(CostActivity.this);
                                    View view=layoutInflater.inflate(R.layout.single_day_dialog,null);
                                    List<CostBean> beanList=DataSupport.where("year=? and month=? and day=?",
                                 picker.getYear()+"",picker.getMonth()+1+"",picker.getDayOfMonth()+"").find(CostBean.class);
                                    Log.d("Nick",picker.getYear()+"");
                            Log.d("Nick",picker.getMonth()+1+"");
                            Log.d("Nick",picker.getDayOfMonth()+"");
                                    CostBeanAdapter adapter=new CostBeanAdapter(beanList);
                                    RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyler_view_dialog);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(CostActivity.this));
//                                    recyclerView.setLayoutManager(new GridLayoutManager(CostActivity.this, 2));
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.addItemDecoration(new DividerItemDecoration(CostActivity.this,DividerItemDecoration.VERTICAL_LIST));
//                                    recyclerView.addItemDecoration(new DividerItemDecoration(CostActivity.this,DividerItemDecoration.HORIZONTAL_LIST));
                                    recyclerView.setBackgroundColor(Color.rgb(	230,230,250));
                                    daydialog.setView(view);
                                    daydialog.setPositiveButton("我知道了",null);
                                    Dialog mDaialog=daydialog.create();
                                    mDaialog.show();
                                    Button btnPos=((AlertDialog) mDaialog).getButton(DialogInterface.BUTTON_POSITIVE);
                                    btnPos.setTextColor(Color.BLUE);
                                }
                            });
                           builder.setNegativeButton("算了", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   builder.create().dismiss();
                               }
                           });
                            builder.setView(viewDialog);
                            builder.setTitle("Cocoin-选择一天吧！");
                            Dialog dialog=builder.create();
                            dialog.show();
                            Button btnPos=((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                            Button btnNeg = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                            btnPos.setTextColor(Color.BLUE);
                            btnNeg.setTextColor(Color.GRAY);
                            mDrawerLayout.closeDrawers();
                            break;
                    }
                    return true;
                }
            });
//            Toolbar mToolBar=(Toolbar) findViewById(R.id.tool_bar);
//            闪退？？
//            Log.d("Nick",mToolBar.toString());
//            setActionBar(mToolBar);
//            ActionBar actionBar=getActionBar();
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.default_user_logo);
//            mToolBar=(Toolbar) findViewById(R.id.tool_bar);
//            this.setActionBar(mToolBar);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.default_user_logo);
            costs=new ArrayList<CostLogo>();
            for(int i=0;i<items.length;i++){
                CostLogo costLogo=new CostLogo();
                costLogo.setItem(items[i]);
                costLogo.setItemLogo(itemsPic[i]);
                costs.add(costLogo);
            }
            GridViewAdapter adapter=new GridViewAdapter(this,costs);
            mGridView.setAdapter(adapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CostLogo costLogo=costs.get(position);
                    ivChosenItem.setImageResource(costLogo.getItemLogo());
                    chosenItem=costLogo.getItem();
                    chosenLogo=costLogo.getItemLogo();
                    //处理点击的消费项目
                }
            });
//            mGridView.setBackgroundColor(Color.rgb(176,196,222));
             numsList=new ArrayList<>();
            for(int i=0;i<nums.length;i++){
                numsList.add(nums[i]);
            }
            KeyboardAdapter keyboardAdapter=new KeyboardAdapter(this,numsList);
            mKeyboardGridView.setAdapter(keyboardAdapter);
            mKeyboardGridView.setBackgroundColor(Color.rgb(230,230,250));
            mKeyboardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 9&&chosenNums.length()>0) {
                        chosenNums.deleteCharAt(chosenNums.length()-1);
                        tvChosenNums.setText(chosenNums.toString());
                    }
                    if (position != 9 && position != 11) {
                        if(position==10&&chosenNums.length()==0){
                        }else {
                            String num = numsList.get(position);
                            chosenNums.append(num);
                            tvChosenNums.setText(chosenNums.toString());//处理消费金额
                        }
                    }
                    if(position==11){
                        if(chosenItem==null){
                            Toast.makeText(getApplicationContext(),"请先选择消费项目",Toast.LENGTH_SHORT).show();
                        }
                        else if(chosenNums.length()==0){
                            Toast.makeText(getApplicationContext(),"请先填写消费金额",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //处理保存消费项目事件，要先获取消费保存时间
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH)+1;
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            CostBean costBean=new CostBean();
                            costBean.setDay(day);
                            costBean.setMonth(month);
                            costBean.setYear(year);
                            costBean.setItem(chosenItem);
                            costBean.setMoney(chosenNums.toString());
                            costBean.setLogo(chosenLogo);
                            costBean.setDay_of_year(calendar.get(Calendar.DAY_OF_YEAR));
                            costBean.setWeek_of_year(calendar.get(Calendar.WEEK_OF_YEAR));
                            costBean.save();
                            SharedPreferences preferences=getSharedPreferences("UserInfo",MODE_PRIVATE);
                            String objectID=preferences.getString("objectID","nothing");
                            if(objectID.equals("nothing")){
                               Toast.makeText(CostActivity.this,"账单成功添加",Toast.LENGTH_SHORT).show();
                            }else {
                                List<CostBean> costBeanList = DataSupport.findAll(CostBean.class);
                                UserInfo userInfo=new UserInfo();
                                userInfo.setCostList(costBeanList);
                                userInfo.update(objectID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Toast.makeText(CostActivity.this,"账单成功添加并同步到云端",Toast.LENGTH_SHORT).show();
                                        }else{
                                        }
                                    }
                                });

                            }
                            Log.d("Nick",costBean.getMoney()+" "+costBean.getItem()+" "+costBean.getYear()+" "+
                                    costBean.getMonth()+" "+costBean.getDay());
                            chosenItem=null;
                            chosenNums.delete(0,chosenNums.length());
                            tvChosenNums.setText(chosenNums.toString());
                            ivChosenItem.setImageDrawable(null);
                        }
                    }
                }
            });
        }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.tool_bar_menu,menu);
//             return true;
//    }

    //    private void testLitepal(){
//        CostBean costBean=new CostBean();
//        costBean.setItem("晚饭");
//        costBean.save();
//        List<CostBean> beans= DataSupport.findAll(CostBean.class);
//        CostBean bean=beans.get(0);
//        textView.setText(bean.getItem());
//    }

  }

