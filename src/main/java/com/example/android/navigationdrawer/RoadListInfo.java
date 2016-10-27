package com.example.android.navigationdrawer;

/**
 * Created by heeye on 2016-09-29.
 */

public class RoadListInfo {
    private String mRoadName;
    private  String mRoadLoca;

    RoadListInfo(String roadloca, String roadname)
    {
        mRoadLoca = roadloca;
        mRoadName = roadname;
    }

    public String getmRoadLoca() {
        return mRoadLoca;
    }

    public void setmRoadLoca(String mRoadLoca) {
        this.mRoadLoca = mRoadLoca;
    }

    public String getmRoadName() {
        return mRoadName;
    }

    public void setmRoadName(String mRoadName) {
        this.mRoadName = mRoadName;
    }
}
