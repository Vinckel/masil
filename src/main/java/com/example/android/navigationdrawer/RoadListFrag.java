package com.example.android.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by heeye on 2016-09-29.
 */

public class RoadListFrag extends Fragment {

    private static final String ARG_PARAM1 = "selectId";
    private static final String ARG_PARAM2= "selectName";

    int selectThemeId;
    double lat, lon;

    String selectThemeTxt;

    static String myRJSON;
    static JSONArray rschkr = null;

    ListView roadList;
    RoadListAdapter adapter;

    ApplicationData appdata;


    RoadListInfo[] r;

    private static final String TAG_RESULTS = "result";
    private static final String db_id = "id";
    private static final String db_name = "name";
    private static final String db_loca = "loca";
    private static final String db_distance = "distance";
    private static final String db_bookmark = "bookmark";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appdata = (ApplicationData) getActivity().getApplicationContext();

        Bundle extra = getArguments();
        selectThemeId = extra.getInt("themeId"); //선택한 테마 아이디
        Log.d("RoadTag","선택한 테마 아이디"+selectThemeId);

        switch (selectThemeId)
        {
            case 1:
                selectThemeTxt = "역사 속 산책";
                break;
            case 2:
                selectThemeTxt = "물길 따라 산책";
                break;
            case 3:
                selectThemeTxt = "숲길 산책";
                break;
            case 4:
                selectThemeTxt = "골목골목 산책";
                break;
            case 5:
                selectThemeTxt = "캠퍼스 산책";
                break;
            case 6:
                selectThemeTxt = "데이트 산책";
                break;
            case 7:
                selectThemeTxt = "꽃길 산책";
                break;
            case 8:
                selectThemeTxt = "미술관 옆 산책";
                break;

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.road_list_view, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText(selectThemeTxt);

        roadList = (ListView) rootView.findViewById(R.id.listview1);


//현재 위치 구하는 함수지렁 지금은 무조건 실습실이지렁
        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            lat = lastKnownLocation.getLatitude();
            lon = lastKnownLocation.getLongitude();
           // lat = 37.629254;
           // lon = 127.090701;//실습실 좌표

        }
        else {
            Toast.makeText(getActivity(),"error: can't get location",Toast.LENGTH_LONG).show();}



       // lat = 37.629254;
       // lon = 127.090701;//실습실 좌표


        String getP = "http://condi.swu.ac.kr/schkr/getThemeRoadList.php?themeid="+selectThemeId+"&xpoint="+lat+"&ypoint="+lon;
        getRoadList(getP);


        roadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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

            adapter = new RoadListAdapter(getActivity());

            roadList.setAdapter(adapter);
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
                double dist = c.getDouble(db_distance);
                boolean bookmark = appdata.checkBookmark(id);

                String txt_dist = String.format("%.2f" , dist);
                //저 로드 리스트 인포가 수정되겠지

                r[i] = new RoadListInfo(1,loca,name,txt_dist, id, bookmark);
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
