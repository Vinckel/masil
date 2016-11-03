package com.example.android.navigationdrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by heeye on 2016-11-03.
 */

public class HistoryDiaryFrag extends Fragment {

    int selNum = 0;
    String currentdate = "";
    String selectname = "";
    String wktime= "";
    String startfeel= "";
    String finishfeel= "";
    String wklength= "";
    String wkcount= "";
    String calorie= "";
    String diarytxt= "";

    TextView name_txt,review_text, timer_txt,length_txt,count_txt,calorie_txt,startfeel_txt, finishfeel_txt,date_txt;

    static String myJSON;
    static JSONArray schkr = null;

    private static final String TAG_RESULTS = "result";
    private static final String db_date = "currentdate";
    private static final String db_time = "wktime";
    private static final String db_name = "selectname";
    private static final String db_startfeel = "startfeel";
    private static final String db_finishfeel = "finishfeel";
    private static final String db_wklength = "wklength";
    private static final String db_wkcount = "wkcount";
    private static final String db_calorie = "calorie";
    private static final String db_diarytxt = "diarytxt";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getArguments();
        selNum = extra.getInt("number"); //선택된 다이어리
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.showdiary, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText("산책 결과");

        review_text = (TextView) rootView.findViewById(R.id.review_text);
        name_txt = (TextView) rootView.findViewById(R.id.road);
        timer_txt = (TextView) rootView.findViewById(R.id.timer);
        length_txt = (TextView) rootView.findViewById(R.id.lengthview);
        count_txt = (TextView) rootView.findViewById(R.id.walkview);
        calorie_txt = (TextView) rootView.findViewById(R.id.calview);
        startfeel_txt = (TextView) rootView.findViewById(R.id.startfeel_txt);
        finishfeel_txt = (TextView) rootView.findViewById(R.id.finishfeel_txt);
        date_txt = (TextView) rootView.findViewById(R.id.result_date);




        String sss = "http://condi.swu.ac.kr/schkr/getDiary.php?number="+selNum;
        getData(sss);


        return rootView;
    }

    public void showList() {


        try {

            String theme_txt="";
            String level_txt="";
            String time_txt="";


            JSONObject jsonObj = new JSONObject(myJSON);
            schkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < schkr.length(); i++) {
                JSONObject c = schkr.getJSONObject(i);

                currentdate = c.getString(db_date);
                wktime = c.getString(db_time);
                selectname = c.getString(db_name);
                startfeel = c.getString(db_startfeel);
                finishfeel = c.getString(db_finishfeel);
                wklength = c.getString(db_wklength);
                wkcount = c.getString(db_wkcount);
                calorie = c.getString(db_calorie);
                diarytxt = c.getString(db_diarytxt);

                date_txt.setText(currentdate);
                review_text.setText(diarytxt);
                name_txt.setText(selectname);
                timer_txt.setText(wktime);
                length_txt.setText(wklength);
                count_txt.setText(wkcount);
                calorie_txt.setText(calorie);
                date_txt.setText(currentdate+" 산책");


                switch(startfeel){
                    case "기뻐요":
                        startfeel = "기쁨";
                        startfeel_txt.setText("기쁨");
                        break;
                    case "평온해요":
                        startfeel = "평온";
                        startfeel_txt.setText("평온");
                        break;
                    case "무료해요":
                        startfeel = "무료";
                        startfeel_txt.setText("무료");
                        break;
                    case "우울해요":
                        startfeel = "우울";
                        startfeel_txt.setText("우울");
                        break;
                    case "짜증나요":
                        startfeel = "짜증";
                        startfeel_txt.setText("짜증");
                        break;
                }
                switch(finishfeel){
                    case "기뻐요":
                        finishfeel = "기쁨";
                        finishfeel_txt.setText("기쁨");
                        break;
                    case "평온해요":
                        finishfeel = "평온";
                        finishfeel_txt.setText("평온");
                        break;
                    case "무료해요":
                        finishfeel = "무료";
                        finishfeel_txt.setText("무료");
                        break;
                    case "우울해요":
                        finishfeel = "우울";
                        finishfeel_txt.setText("우울");
                        break;
                    case "짜증나요":
                        finishfeel = "짜증";
                        finishfeel_txt.setText("짜증");
                        break;
                }

            }// end of for()


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void getData(String url){
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
