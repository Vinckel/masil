package com.example.android.navigationdrawer;

/**
 * Created by heeye on 2016-09-29.
 */

public class RoadListInfo {
    private String mRoadName;
    private  String mRoadLoca;
    private String mRoadDist;
    private int mRoadId;
    private String mRoadRating;
    private boolean mBookmark;


    RoadListInfo(int flag, String roadloca, String roadname, String str, int roadid, boolean bookmark)
    {
        switch (flag){
            case 1: //거리나오는리스트
                mRoadLoca = roadloca;
                mRoadName = roadname;
                mRoadDist = str;
                mRoadId = roadid;
                mBookmark = bookmark;
                break;
            case 2://별점나오는리스트
                mRoadLoca = roadloca;
                mRoadName = roadname;
                mRoadRating = str;
                mRoadId = roadid;
                mBookmark = bookmark;
                break;
        }



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

    public String getmRoadDist() {
        return mRoadDist;
    }

    public void setmRoadDist(String mRoadDist) {
        this.mRoadDist = mRoadDist;
    }

    public int getmRoadId() {
        return mRoadId;
    }

    public String getmRoadRating() {
        return mRoadRating;
    }

    public void setmRoadRating(String mRoadRating) {
        this.mRoadRating = mRoadRating;
    }

    public boolean getmBookmark() {
        return mBookmark;
    }

    public void setmBookmark(boolean mBookmark) {
        this.mBookmark = mBookmark; //여기서 연결하면 되지 않을까 php로
    }
}
