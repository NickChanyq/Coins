package com.example.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 项目名：MyApplication
 * 包名：com.example.app
 * 文件名：MonthBean
 * 创建者：wsg
 * 创建时间：2017/7/16  17:23
 * 描述：实体类
 */

public class MonthBean implements Parcelable{
    @Override
    public String toString() {
        return "MonthBean{" +
                "data='" + data + '\'' +
                ", obj=" + obj.toString() +
                '}';
    }

    public String data;
    public ArrayList<PieBean> obj;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public float getSum() {

        float sum=0;
        for (PieBean pieBean : obj) {
            sum+=pieBean.value;
        }
        return sum;
    }



    public float getSum(int index) {

        float sum=0;
        for (int i = 0; i < index; i++) {
            sum+=obj.get(i).value;
        }
        return sum;
    }


    class PieBean{
        @Override
        public String toString() {
            return "PieBean{" +
                    "title='" + title + '\'' +
                    ", value=" + value +
                    '}';
        }

        public String title;
        public int value;
    }
}
