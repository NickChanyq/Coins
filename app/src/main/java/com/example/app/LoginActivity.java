package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends Activity {

    EditText etTel;
    EditText etPassWord;
    Button btnLogin;
    Button btnRegister;
    Button btnCancle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        etTel=(EditText) findViewById(R.id.login_edit_account);
        etPassWord=(EditText)findViewById(R.id.login_edit_pwd);
        btnLogin=(Button)findViewById(R.id.login_btn_login);
        btnRegister=(Button)findViewById(R.id.login_btn_register);
        btnCancle=(Button)findViewById(R.id.login_btn_cancle) ;
        SharedPreferences preferences=getSharedPreferences("UserInfo",MODE_PRIVATE);
        etTel.setText(preferences.getString("tel",""));
        etPassWord.setText(preferences.getString("passWord",""));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                if (!preferences.getString("tel", "").equals("")) {
                    Toast.makeText(LoginActivity.this, "您已登录", Toast.LENGTH_SHORT).show();
                } else {
                    final String tel = etTel.getText().toString().trim();
                    final String passWord = etPassWord.getText().toString().trim();
                    BmobQuery<UserInfo> categoryBmobQuery = new BmobQuery<>();
                    categoryBmobQuery.addWhereEqualTo("tel", tel);
                    categoryBmobQuery.findObjects(new FindListener<UserInfo>() {
                        @Override
                        public void done(List<UserInfo> object, BmobException e) {
                            if (e == null) {
                                Log.d("Nick", object.size() + "");
                                if (object.size() > 0) {
                                    String rightPassWord = object.get(0).getPassWord();
                                    if (passWord.equals(rightPassWord)) {
                                        List<CostBean> cloudList = object.get(0).getCostList();
                                        for (int i = 0; i < cloudList.size(); i++) {   //把云后端的CostBean保存到本地
                                            CostBean costBean = cloudList.get(i);
                                            Log.d("Nick", costBean.getItem() + " cloud");
                                            CostBean localBean = new CostBean();
                                            localBean.setItem(costBean.getItem());
                                            localBean.setMoney(costBean.getMoney());
                                            localBean.setWeek_of_year(costBean.getWeek_of_year());
                                            localBean.setDay_of_year(costBean.getDay_of_year());
                                            localBean.setYear(costBean.getYear());
                                            localBean.setDay(costBean.getDay());
                                            localBean.setMonth(costBean.getMonth());
                                            localBean.setLogo(costBean.getLogo());
                                            localBean.save();
                                        }
                                        Calendar mCalendar = Calendar.getInstance();
                                        int month = mCalendar.get(Calendar.MONTH) + 1;
                                        List<CostBean> mCostList = DataSupport.findAll(CostBean.class);
                                        for (int i = 0; i < mCostList.size(); i++) {   //把云后端的CostBean保存到本地
                                            CostBean costBean = mCostList.get(i);
                                            Log.d("Nick", costBean.getItem() + " 本地");
                                        }
                                        SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                                        editor.putString("tel", tel);
                                        editor.putString("passWord", passWord);
                                        editor.putString("objectID", object.get(0).getObjectId());
                                        editor.apply();
                                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "手机号码或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("Nick", e.toString());
                            }
                        }
                    });
                }
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("UserInfo",MODE_PRIVATE).edit();
                editor.putString("tel","");
                editor.putString("passWord","");
                editor.putString("objectID","nothing");
                editor.apply();
                etTel.setText("");
                etPassWord.setText("");
                DataSupport.deleteAll(CostBean.class);
                Toast.makeText(LoginActivity.this,"已注销并清除本地账单记录",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
