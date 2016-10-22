package com.masil.android.navigationdrawer;

import android.app.Application;

import com.masil.android.common.logger.Log;

/**
 * Created by heeye on 2016-10-17.
 */

public class RoadData extends Application {

    String[] id_list = new String[5];
    String[] name_list = new String[5];
    String[] time_list = new String[5];
    String[] loca_list = new String[5];
    String[] level_list = new String[5];
    String[] theme_list = new String[5];
    String[] length_list = new String[5];
    String[] detail_list = new String[5];

    public void setRoadData(int i, int id, String name, int time, String loca, int level, int theme, double length, String detail) {


        id_list[i] = String.valueOf(id);
        name_list[i] = name;
        time_list[i] = String.valueOf(time);
        length_list[i] = String.valueOf(length);
        level_list[i] = String.valueOf(level);
        theme_list[i] = String.valueOf(theme);
        loca_list[i] = loca;
        detail_list[i] = detail;

        Log.d("MyTag", name_list[1] + name_list[3] + "setRoadData가 동작한다");

    }//end of setRoadData

}