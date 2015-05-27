package com.kuailedian.entity.Generate.Reservation;

import java.util.List;


public class Day{

    private static final String FIELD_DAY = "day";
    private static final String FIELD_FOODS = "foods";
    private static final String FIELD_DATE = "date";


    private String mDay;
    private List<Food> mFoods;
    private String mDate;


    public Day(){

    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getDay() {
        return mDay;
    }

    public void setFoods(List<Food> foods) {
        mFoods = foods;
    }

    public List<Food> getFoods() {
        return mFoods;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }


}