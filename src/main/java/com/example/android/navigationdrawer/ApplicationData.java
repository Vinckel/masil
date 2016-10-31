package com.example.android.navigationdrawer;

import android.app.Application;

/**
 * Created by heeye on 2016-10-17.
 */

public class ApplicationData extends Application {

    int memid = 1; //지금 기본 1번 회원으로 ..



    String[] id_list = new String[5];
    String[] name_list = new String[5];
    String[] time_list = new String[5];
    String[] loca_list = new String[5];
    String[] level_list = new String[5];
    String[] theme_list = new String[5];
    String[] length_list = new String[5];
    String[] detail_list = new String[5];
    String[] distance_list = new String[5];
    String[] rating_list = new String[5];
    String[] ratingNum_list = new String[5];


    public void setRoadData(int i, int id, String name, int time, String loca, int level, int theme, double length, String detail, double distance, double rating, int ratingNum) {


        id_list[i] = String.valueOf(id);
        name_list[i] = name;
        time_list[i] = String.valueOf(time);
        length_list[i] = String.valueOf(length);
        level_list[i] = String.valueOf(level);
        theme_list[i] = String.valueOf(theme);
        loca_list[i] = loca;
        detail_list[i] = detail;
        distance_list[i] = String.valueOf(distance);
        rating_list[i] = String.valueOf(rating);
        ratingNum_list[i] = String.valueOf(ratingNum);


       // Log.d("MyTag", name_list[1] + name_list[3] + "setRoadData가 동작한다");

    }//end of setRoadData

}