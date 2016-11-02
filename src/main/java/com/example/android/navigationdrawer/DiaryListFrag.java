package com.example.android.navigationdrawer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class DiaryListFrag extends Fragment {

    int memberid = 1;

    private static final String ARG_PARAM1 = "number";

    static String myDJSON;
    static JSONArray dschkr = null;

    ListView diarylist;
    static DiaryAdapter adapter;

    DiaryData[] d;

    private static final String TAG_RESULTS = "result";
    private static final String db_id = "selectid";
    private static final String db_name = "selectname";
    private static final String db_date = "currentdate";
    private static final String db_rating = "rating";
    private static final String db_number = "number";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.diary_list_view, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText("나의 산책 히스토리");

        diarylist = (ListView) rootView.findViewById(R.id.diarylistview);

        String getD = "http://condi.swu.ac.kr/schkr/getDiaryList.php?memberid="+memberid;
        getDiaryList(getD);


        diarylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaryData selected = (DiaryData) adapter.getItem(position);
                int selNum = selected.getNumber();

                HistoryDiaryFrag frag = new HistoryDiaryFrag();

                Bundle args = new Bundle();
                args.putInt(ARG_PARAM1,selNum);


                frag.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return rootView;
    }

    public void showDiaryList() {
        try {

            adapter = new DiaryAdapter(getActivity());

            diarylist.setAdapter(adapter);
            Log.d("RoadTag", "어댑터 셋 해줬음");

            Log.d("RoadTag", "쇼로드리스트 진입");
            JSONObject jsonObj = new JSONObject(myDJSON);
            dschkr = jsonObj.getJSONArray(TAG_RESULTS);

            d = new DiaryData[dschkr.length()];


            for (int i = 0; i < dschkr.length(); i++) {
                JSONObject c = dschkr.getJSONObject(i);

                int id = c.getInt(db_id);
                int number = c.getInt(db_number);
                String name = c.getString(db_name);
                String date = c.getString(db_date);
                int rating = c.getInt(db_rating);

                d[i] = new DiaryData(rating,id, name, date,number);
                adapter.add(d[i]);

            }// end of for()

            adapter.notifyDataSetChanged();//변했음을 알린다


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getDiaryList(String url) {

        class getJSON extends AsyncTask<String, Void, String> {
            ProgressDialog progDialog = new ProgressDialog(getActivity());

            @Override
            public void onPreExecute() {

                super.onPreExecute();
                //로딩 넣기

                progDialog.setMessage("Loading...");
                progDialog.setIndeterminate(false);
                progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progDialog.setCancelable(true);
                progDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    Log.d("MyTag", "겟리스트 진입");
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
                myDJSON = result;
                showDiaryList();
                progDialog.dismiss();
            }
        }
        getJSON g = new getJSON();
        g.execute(url);
    }
}
