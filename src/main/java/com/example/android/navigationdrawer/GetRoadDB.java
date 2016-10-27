package com.example.android.navigationdrawer;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by heeye on 2016-10-05.
 */

public class GetRoadDB {

    static String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String db_number = "number";
    private static final String db_name = "name";
    private static final String db_time = "time";
    private static final String db_length = "length";
    private static final String db_level = "level";
    private static final String db_theme = "theme";
    private static final String db_loca = "loca";
    private static final String db_detail = "detail";

    static JSONArray schkr = null;

    static String[] number_list,name_list, loca_list, time_list,
            level_list,theme_list, length_list,detail_list;


    public static void showList() {

        try {
            number_list = new String[5];
            time_list = new String[5];
            name_list = new String[5];
            loca_list = new String[5];
            level_list = new String[5];
            theme_list = new String[5];
            length_list = new String[5];

            JSONObject jsonObj = new JSONObject(myJSON);
            schkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < 5; i++) {
                JSONObject c = schkr.getJSONObject(i);

                 number_list[i] = String.valueOf(c.getInt(db_number)); //int타입인데 string으로 받아도 잘 받아와지는지 확인해야됨
                 name_list[i] = c.getString(db_name);
                 time_list[i] = String.valueOf(c.getInt(db_time));
                 length_list[i] = String.valueOf(c.getDouble(db_length));
                 level_list[i] = String.valueOf(c.getInt(db_level));
                 theme_list[i] = String.valueOf(c.getInt(db_theme));
                 loca_list[i] = c.getString(db_loca);
                 detail_list[i] = c.getString(db_detail);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public static void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                }catch(Exception e){
                    return null;
                }
            }
            @Override
            public void onPostExecute(String result){
                myJSON=result;

                showList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}

