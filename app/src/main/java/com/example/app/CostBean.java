package com.example.app;

import org.litepal.crud.DataSupport;


    public class CostBean extends DataSupport {
        private String money;
        private  String item;
        private int year;
        private int month;
        private int day;
        private  int week_of_year;
        private int day_of_year;
        private int logo;

        public int getWeek_of_year() {
            return week_of_year;
        }

        public void setWeek_of_year(int week_of_year) {
            this.week_of_year = week_of_year;
        }


        public int getDay_of_year() {
            return day_of_year;
        }

        public void setDay_of_year(int day_of_year) {
            this.day_of_year = day_of_year;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public int getDay() {
            return day;
        }
        public void setDay(int day) {
            this.day = day;
        }
        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

    }


