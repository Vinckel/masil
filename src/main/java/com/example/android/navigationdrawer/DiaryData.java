package com.example.android.navigationdrawer;

/**
 * Created by heeye on 2016-11-03.
 */

public class DiaryData {
    int mroadId;
    String mwkdate;
    String mroadName;
    int mrating;
    int number;


    public DiaryData(int mrating, int mroadId, String mroadName, String mwkdate, int number) {
        this.mrating = mrating;
        this.mroadId = mroadId;
        this.mroadName = mroadName;
        this.mwkdate = mwkdate;
        this.number = number;
    }

    public int getMrating() {
        return mrating;
    }

    public void setMrating(int mrating) {
        this.mrating = mrating;
    }

    public int getMroadId() {
        return mroadId;
    }

    public void setMroadId(int mroadId) {
        this.mroadId = mroadId;
    }

    public String getMroadName() {
        return mroadName;
    }

    public void setMroadName(String mroadName) {
        this.mroadName = mroadName;
    }

    public String getMwkdate() {
        return mwkdate;
    }

    public void setMwkdate(String mwkdate) {
        this.mwkdate = mwkdate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
