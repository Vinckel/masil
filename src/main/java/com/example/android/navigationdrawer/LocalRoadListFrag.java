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
import android.widget.Button;
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
 * Created by heeye on 2016-10-05.
 */

public class LocalRoadListFrag extends Fragment {

    private static final String ARG_PARAM1 = "selectId";
    private static final String ARG_PARAM2= "selectName";

    int selectCount;
    String[] selectArray;



    static String myRJSON;
    static JSONArray rschkr = null;

    ListView roadList2;
    RoadListAdapterRating adapter;

    RoadListInfo[] r;

    private static final String TAG_RESULTS = "result";
    private static final String db_id = "id";
    private static final String db_name = "name";
    private static final String db_loca = "loca";
    private static final String db_rating = "rating";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getArguments();
        selectCount = extra.getInt("selectCount");
        selectArray = new String[selectCount];
        selectArray = extra.getStringArray("selectArray");
        for(int i = 0; i<selectCount; i++){
            //Log.d("BTag","번들 확인 중"+selectArray[i]);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.road_list_local_view, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText("지역별 산책로");

        Button btn_selectback = (Button) rootView.findViewById(R.id.list_linear);
        btn_selectback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalListFrag frag = new LocalListFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //adapter = new RoadListAdapterRating(getActivity());

        roadList2 = (ListView) rootView.findViewById(R.id.listview2);



        String getP = "http://condi.swu.ac.kr/schkr/getLocalRoadList.php?selectCount="+selectCount;
        for(int i = 0; i<selectCount; i++){
            getP = getP + "&selectArray[]="+selectArray[i];
        }
        Log.d("MyTag",getP);
        getRoadList(getP);

        roadList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RoadListInfo selected = (RoadListInfo)adapter.getItem(position);
                int selID = selected.getmRoadId();
                String selName = selected.getmRoadName();

                MasilRoadFrag frag = new MasilRoadFrag();

                Bundle args = new Bundle();
                args.putInt(ARG_PARAM1,selID);
                args.putString(ARG_PARAM2,selName);

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

    public void showRoadList() {
        try {

            adapter = new RoadListAdapterRating(getActivity());

            roadList2.setAdapter(adapter);
            Log.d("RoadTag", "어댑터 셋 해줬음");

            Log.d("RoadTag", "쇼로드리스트 진입");
            JSONObject jsonObj = new JSONObject(myRJSON);
            rschkr = jsonObj.getJSONArray(TAG_RESULTS);

            r = new RoadListInfo[rschkr.length()];


            for (int i = 0; i < rschkr.length(); i++) {
                JSONObject c = rschkr.getJSONObject(i);

                int id = c.getInt(db_id);
                String name = c.getString(db_name);
                String loca = c.getString(db_loca);
                double rating = c.getDouble(db_rating);

                String txt_rate = String.format("%.2f" ,rating);

                Log.d("MyTag","별점나오나: "+rating+"자르면: "+txt_rate);

                r[i] = new RoadListInfo(2,loca,name, txt_rate, id);
                adapter.add(r[i]);

            }// end of for()

            adapter.notifyDataSetChanged();//변했음을 알린다


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getRoadList(String url) {

        class GetRoadListJSON extends AsyncTask<String, Void, String> {
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
                showRoadList();
                progDialog.dismiss();
            }
        }
        GetRoadListJSON g = new GetRoadListJSON();
        g.execute(url);
    }

}