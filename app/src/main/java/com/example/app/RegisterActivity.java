package com.example.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity {
    Button btnSure;
    EditText tel;
    EditText firPwd;
    EditText SedPwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        btnSure=(Button)findViewById(R.id.register_btn_sure);
        tel=(EditText)findViewById(R.id.resetpwd_edit_name);
        firPwd=(EditText)findViewById(R.id.resetpwd_edit_pwd_old);
        SedPwd=(EditText)findViewById(R.id.resetpwd_edit_pwd_new);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tel.getText().toString().length()!=11){
                    Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }else if(  !firPwd.getText().toString().equals(SedPwd.getText().toString())  ){
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    UserInfo user=new UserInfo();
                    user.setPassWord(firPwd.getText().toString());
                    user.setTel(tel.getText().toString());
                    List<CostBean> list= DataSupport.findAll(CostBean.class);
                    user.setCostList(list);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(RegisterActivity.this,"注册成功"+"，您的ID为"+objectId,Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this,"网络出错，注册失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}
