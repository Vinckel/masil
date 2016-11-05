package com.example.android.navigationdrawer;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by heeye on 2016-10-11.
 */

public class MyViewPager extends Fragment {

    Button btn_start;
    private ApplicationData roaddata;


    //static String[] id_list,name_list, loca_list, time_list,level_list,theme_list, length_list,detail_list;


    private static final String ARG_PARAM1 = "selectId";
    private static final String ARG_PARAM2 = "selectName";

    String user_condition, user_time;

    static String myJSON;
    static JSONArray schkr = null;

    static final int memberId = 1;

    private static final String TAG_RESULTS = "result";
    private static final String db_id = "id";
    private static final String db_name = "name";
    private static final String db_time = "time";
    private static final String db_length = "length";
    private static final String db_level = "level";
    private static final String db_theme = "theme";
    private static final String db_loca = "loca";
    private static final String db_detail = "detail";
    private static final String db_distance = "distance";
    private static final String db_rating = "rating";
    private static final String db_ratingNum = "ratingNum";
    // 뷰페이저에 필요한 것들....

    private int mPrevPosition; //이전에 선택되었던 포지션 값
    private ViewPager mPager; //뷰페이저
    private LinearLayout mPageMark; //페이지 마크 (점 다섯개 그거)
    private MyPagerAdapter adapter;

    int selectId;
    String selectName;

    static double lat, lon;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roaddata = (ApplicationData) getActivity().getApplicationContext();

        //컨디션이랑 가용시간 필터 할 때 써야됨
        Bundle extra = getArguments();
        user_condition = extra.getString("param1");
        user_time = extra.getString("param2");


        //Toast.makeText(getActivity(),user_condition+user_time,Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.masil_viewpager, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        txtTitle.setText("마실마실 콕");
/*
        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            //lat = lastKnownLocation.getLatitude();
           //lon = lastKnownLocation.getLongitude();
            lat = 37.629254;
            lon = 127.090701;//실습실 좌표

        }
        else {
            Toast.makeText(getActivity(),"noooooooooooooo",Toast.LENGTH_LONG).show();}
*/


        Log.d("MyTag", "온크리에잇뷰 드러옴");

        int condition;

        switch (user_condition)
        {
            case "좋음":
                condition = 3;
                break;
            case "보통":
                condition = 2;
                break;
            case "나쁨":
                condition = 1;
                break;
            default:
                condition = 0;
                break;

        }

        int time = Integer.parseInt(user_time);

        lat = 37.629254;
        lon = 127.090701;//실습실 좌표

        String mUrl = "http://condi.swu.ac.kr/schkr/receive_road_2.php?memberid="+memberId+"&condition="+condition+"&time="+time+"&xpoint="+lat+"&ypoint="+lon;
        getData(mUrl);

        mPageMark = (LinearLayout) rootView.findViewById(R.id.page_mark);
        mPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        initPageMark(0);


        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //아이템이 선택이 되었으면
                mPageMark.getChildAt(mPrevPosition).setBackgroundResource(R.drawable.page_not);   //이전 페이지에 해당하는 페이지 표시 이미지 변경
                mPageMark.getChildAt(position).setBackgroundResource(R.drawable.page_select);      //현재 페이지에 해당하는 페이지 표시 이미지 변경
                mPrevPosition = position;//이전 포지션 값을 현재로 변경

                selectId = Integer.parseInt(roaddata.id_list[position]); //페이지 선택 값~~
                selectName = roaddata.name_list[position];
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_start = (Button) rootView.findViewById(R.id.btn_pager_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사실은 넘어갈때 번들 같이 넘겨서 무슨 산책로인지 확인하고 디비 가서 새로 불러와야되는것임 ㅇㅅㅇ

                WalkingFrag frag = new WalkingFrag();

                Bundle args = new Bundle();
                args.putInt(ARG_PARAM1,selectId);
                args.putString(ARG_PARAM2,selectName);
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


    public void showList() {
        Log.d("MyTag", "쇼리스트 진입");

        try {
            Log.d("MyTag", "쇼리스트 try 진입 성공");

            JSONObject jsonObj = new JSONObject(myJSON);
            schkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < schkr.length(); i++) {
                JSONObject c = schkr.getJSONObject(i);

                int id = c.getInt(db_id);
                String name = c.getString(db_name);
                int time = c.getInt(db_time);
                double length = c.getDouble(db_length);
                int level = c.getInt(db_level);
                int theme = c.getInt(db_theme);
                String loca = c.getString(db_loca);
                String detail = c.getString(db_detail);
                double distance = c.getDouble(db_distance);
                double rating = c.getDouble(db_rating);
                int ratingNum = c.getInt(db_ratingNum);

                roaddata.setRoadData(i, id, name, time, loca, level, theme, length, detail, distance,rating, ratingNum);
               // Log.d("MyTag", "showList for문 안에서 리스트 불러보는 중" + roaddata.name_list[i]);
            }// end of for()

           // Log.d("MyTag", "showList에서 리스트 불러보는 중" + roaddata.name_list[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {

        class GetDataJSON extends AsyncTask<String, Void, String> {
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
                    Log.d("MyTag", "겟데이타진입");
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
                myJSON = result;
                showList();
                Log.d("MyTag", "postExecute에서 리스트 불러보는 중" + roaddata.name_list[3]);
                progDialog.dismiss();

                List<ViewPagerContent> fragments = new ArrayList<ViewPagerContent>();
                    for (int i = 0; i < 5; i++) {
                    ViewPagerContent f = ViewPagerContent.newInstance(i);
                    fragments.add(f);
                }

                adapter = new MyPagerAdapter(getFragmentManager(), fragments);
                mPager.setAdapter(adapter);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    //하단의 현재 페이지 표시하는 뷰 초기화
    private void initPageMark(int initPosition) {
        for (int i = 0; i < 5; i++) {
            ImageView iv = new ImageView(getContext());   //페이지 표시 이미지 뷰 생성

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            iv.setLayoutParams(lp);

            //첫 페이지 표시 이미지 이면 선택된 이미지로
            if (i == initPosition) {
                //selectId = Integer.parseInt(roaddata.id_list[initPosition]);
                //Toast.makeText(getActivity(),"지금 산책로 번호"+selectId,Toast.LENGTH_SHORT).show();
                iv.setBackgroundResource(R.drawable.page_select);
                iv.getLayoutParams().height = 40; //콩알 크기 dimen으로 정해놓고 쓰면 되게따...이거 뭔 단위냐...
                iv.getLayoutParams().width = 40;
            } else {  //나머지는 선택안된 이미지로
                iv.setBackgroundResource(R.drawable.page_not);
                iv.getLayoutParams().height = 40;
                iv.getLayoutParams().width = 40;
            }
            //LinearLayout에 추가
            mPageMark.addView(iv);
        }
        mPrevPosition = initPosition;   //이전 포지션 값 초기화
    }



    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<ViewPagerContent> fragments;

        public MyPagerAdapter(FragmentManager fm, List<ViewPagerContent> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public ViewPagerContent getItem(int pos) {
            return fragments.get(pos);
        }
    }
}