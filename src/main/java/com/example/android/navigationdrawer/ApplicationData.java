package com.example.android.navigationdrawer;

import android.app.Application;
import android.util.Log;

/**
 * Created by heeye on 2016-10-17.
 */

public class ApplicationData extends Application {

    int memid = 1; //지금 기본 1번 회원으로 ..

    String happyurl ="http://m.app.melon.com/landing/playList.htm?type=ply&plylstTypeCode=M20001&memberKey=11312353&plylstSeq=423425496&ref=kakao&snsGate=Y";
    String peaceurl = "";
    String boringurl = "";
    String sadurl = "";
    String randomurl = "";


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

    String mHappy
            ,mPeace
            ,mBoring
            ,mSad
            ,mAngry;


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

    public void setMusic(String happy, String peace, String boring, String sad, String angry){
        this.mHappy = happy;
        this.mPeace = peace;
        this.mBoring = boring;
        this.mSad = sad;
        this.mAngry = angry;
        Log.d("MyTag","앱데이터 부분에서 값이!"+mHappy+mPeace+mBoring+mSad+mAngry);
    }

    public String playHappy(){
        String str = "";
        switch (mHappy)
        {
            case "기쁜음악":
                str = happyurl;
                break;
            case "평온한음악":
                str = peaceurl;
                break;
            case "무료한음악":
                str = boringurl;
                break;
            case "우울한음악":
                str = sadurl;
                break;
            case "랜덤":
                str = randomurl;
                break;
        }
        return str;
    }
    public String playPeace(){
        String str = "";
        switch (mPeace)
        {
            case "기쁜음악":
                str = happyurl;
                break;
            case "평온한음악":
                str = peaceurl;
                break;
            case "무료한음악":
                str = boringurl;
                break;
            case "우울한음악":
                str = sadurl;
                break;
            case "랜덤":
                str = randomurl;
                break;
        }
        return str;
    }
    public String playBoring(){
        String str = "";
        switch (mBoring)
        {
            case "기쁜음악":
                str = happyurl;
                break;
            case "평온한음악":
                str = peaceurl;
                break;
            case "무료한음악":
                str = boringurl;
                break;
            case "우울한음악":
                str = sadurl;
                break;
            case "랜덤":
                str = randomurl;
                break;
        }
        return str;
    }
    public String playSad(){
        String str = "";
        switch (mSad)
        {
            case "기쁜음악":
                str = happyurl;
                break;
            case "평온한음악":
                str = peaceurl;
                break;
            case "무료한음악":
                str = boringurl;
                break;
            case "우울한음악":
                str = sadurl;
                break;
            case "랜덤":
                str = randomurl;
                break;
        }
        return str;
    }
    public String playAngry(){
        String str = "";
        switch (mAngry)
        {
            case "기쁜음악":
                str = happyurl;
                break;
            case "평온한음악":
                str = peaceurl;
                break;
            case "무료한음악":
                str = boringurl;
                break;
            case "우울한음악":
                str = sadurl;
                break;
            case "랜덤":
                str = randomurl;
                break;
        }
        return str;
    }



}