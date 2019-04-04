package com.example.app;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject {


    private List<CostBean> costList;
    private String tel;
    private  String passWord;

    public List<CostBean> getCostList() {
        return costList;
    }

    public void setCostList(List<CostBean> costList) {
        this.costList = costList;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}
