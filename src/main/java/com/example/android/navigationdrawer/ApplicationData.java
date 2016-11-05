package com.example.android.navigationdrawer;

import android.app.Application;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by heeye on 2016-10-17.
 */

public class ApplicationData extends Application {

    int memid = 1; //지금 기본 1번 회원으로 ..


    static String myRJSON;
    static JSONArray rschkr = null;

    private static final String TAG_RESULTS = "result";
    private static final String db_roadid = "roadid";

    String happyurl ="http://m.app.melon.com/landing/playList.htm?type=ply&plylstTypeCode=M20001&memberKey=11312353&plylstSeq=423425496&ref=kakao&snsGate=Y";
    String peaceurl = "http://m.app.melon.com/landing/playList.htm?type=ply&plylstTypeCode=M20001&memberKey=11312353&plylstSeq=423425912&ref=kakao&snsGate=Y";
    String boringurl = "http://m.app.melon.com/landing/playList.htm?type=ply&plylstTypeCode=M20001&memberKey=11312353&plylstSeq=423426561&ref=kakao&snsGate=Y";
    String sadurl = "http://m.app.melon.com/landing/playList.htm?type=ply&plylstTypeCode=M20001&memberKey=11312353&plylstSeq=423426784&ref=kakao&snsGate=Y";
    String randomurl = "http://m.app.melon.com/landing/playList.htm?type=ply&plylstTypeCode=M20001&memberKey=11312353&plylstSeq=423425496&ref=kakao&snsGate=Y";


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
    boolean[] bookmartk_list = new boolean[5];

    private ArrayList<Integer> mIdList ;

    String mHappy
            ,mPeace
            ,mBoring
            ,mSad
            ,mAngry;

    double finishx, finishy;
    Location finishP = new Location("finishP");

    public void getBookmark(int roadid){
        mIdList.add(roadid);

    }

    public boolean checkBookmark(int roadid){
        for(int i = 0; i<mIdList.size(); i++) {
            if (mIdList.get(i) == roadid) {
                return true;
            }
        }
        return false;
    }


    public void setFinish(double x, double y){
        finishx = x;
        finishy = y;
        Log.d("MyTag", "셋 피니쉬 실행일세"+finishx+" , "+finishy);
        finishP.setLatitude(x);
        finishP.setLongitude(y);
    }
    public boolean checkingFinish(double cx, double cy){
        Location currentP = new Location("currentP");
        currentP.setLatitude(cx);
        currentP.setLongitude(cy);

        if(finishP.distanceTo(currentP)<50){
            return true;
        }
        else {
            return false;
        }

    }

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

/*
    public void setRoadData(int i, int id, String name, int time, String loca, int level, int theme, double length, String detail, double distance, double rating, int ratingNum, boolean bookmark) {


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
        bookmartk_list[i] = bookmark;


       // Log.d("MyTag", name_list[1] + name_list[3] + "setRoadData가 동작한다");

    }//end of setRoadData
*/
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

    public void setBookmarkList() {
        try {

            JSONObject jsonObj = new JSONObject(myRJSON);
            rschkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < rschkr.length(); i++) {
                JSONObject c = rschkr.getJSONObject(i);

                int roadid = c.getInt(db_roadid);
                getBookmark(roadid);
                Log.d("되나",roadid+"북마크리스트");

            }// end of for()

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getBookmarkList(String url) {

        mIdList = new ArrayList<Integer>();

        class GetRoadListJSON extends AsyncTask<String, Void, String> {
            //ProgressDialog progDialog = new ProgressDialog(getApplicationContext());

            @Override
            public void onPreExecute() {

                super.onPreExecute();
                //로딩 넣기

                // progDialog.setMessage("Loading...");
                // progDialog.setIndeterminate(false);
                // progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // progDialog.setCancelable(true);
                // progDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    Log.d("RoadTag", "겟로드리스트 진입");
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onPostExecute(String result) {
                //로딩 없애기
                myRJSON = result;
                setBookmarkList();
                // progDialog.dismiss();
            }
        }
        GetRoadListJSON g = new GetRoadListJSON();
        g.execute(url);
    }



}