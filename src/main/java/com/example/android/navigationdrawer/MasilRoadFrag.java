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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by heeye on 2016-09-21.
 */
public class MasilRoadFrag extends Fragment{

    private static final String ARG_PARAM1 = "selectId";
    private static final String ARG_PARAM2 = "selectName";

    private static final String ADD_URL = "http://condi.swu.ac.kr/schkr/addBookmark.php";
    private static final String DEL_URL = "http://condi.swu.ac.kr/schkr/delBookmark.php";

    ApplicationData appdata;

    int memberid = 1;

    int id;
    String name="";
    int time = 0;
    double length = 0;
    int level = 0;
    int theme = 0;
    String loca = "";
    String detail = "";
    double distance = 0;
    double rating = 0;
    int ratingNum = 0;

    int selectId;
    String selectName;

    Button btn_start;

    ImageView bigimg,img1,img2, img3, img4, img5;

    TextView nameView, detailView, tagView, ratingTextView;
    ImageView bookmarkView;
    RatingBar ratingView;
    static String txt_tag, txt_name, txt_detail;

    String user_condition, user_time;

    static String myJSON;
    static JSONArray schkr = null;

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


    private double lat,lon;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getArguments();
        selectId = extra.getInt("selectId");
        selectName = extra.getString("selectName");

        appdata = (ApplicationData)getActivity().getApplicationContext();

        //Toast.makeText(getActivity(),"넘어온 아이디"+selectId,Toast.LENGTH_SHORT).show();


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.masilroad_frag, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText(selectName);

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
        lat = 37.629254;
        lon = 127.090701;//실습실 좌표

        String sss = "http://condi.swu.ac.kr/schkr/receive_road_1.php?id="+selectId+"&xpoint="+lat+"&ypoint="+lon;
        getData(sss);

        bookmarkView = (ImageView) rootView.findViewById(R.id.road_bookmark);
        //아이디랑 현재 우ㅣ치 보내야됨
        bookmarkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                if((bookmarkView.getTag()).equals("off")){//북마크 체크 ㄴㄴ면
                    //add url로 memberid 랑 roadid


                    try {
                        addBookmark(memberid,selectId);

                        String getP = "http://condi.swu.ac.kr/schkr/getBookmark.php?memberid="+memberid;//appdata.getBookmarkList로 그거 리샛해준다 업데이트라는말
                        appdata.getBookmarkList(getP);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else{ //북마크 체크 ㅇㅇ면

                    try {
                        delBookmark(memberid,selectId);

                        String getP = "http://condi.swu.ac.kr/schkr/getBookmark.php?memberid="+memberid;//appdata.getBookmarkList로 그거 리샛해준다 업데이트라는말
                        appdata.getBookmarkList(getP);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


        btn_start = (Button) rootView.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WalkingFrag frag = new WalkingFrag();

                Bundle args = new Bundle();
                args.putInt(ARG_PARAM1,selectId); //"selectId"로 보내는거임 걸로 받아야댐
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


        try {

            String theme_txt="";
            String level_txt="";
            String time_txt="";


            JSONObject jsonObj = new JSONObject(myJSON);
            schkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < schkr.length(); i++) {
                JSONObject c = schkr.getJSONObject(i);

                 id = c.getInt(db_id);
                 name = c.getString(db_name);
                 time = c.getInt(db_time);
                 length = c.getDouble(db_length);
                 level = c.getInt(db_level);
                 theme = c.getInt(db_theme);
                 loca = c.getString(db_loca);
                 detail = c.getString(db_detail);
                 distance = c.getDouble(db_distance);
                 rating = c.getDouble(db_rating);
                ratingNum = c.getInt(db_ratingNum);



                switch (theme) //테마 이름....
                {
                    case 1:
                        theme_txt = "역사 속 산책";
                        break;
                    case 2:
                        theme_txt = "물길 따라 산책";
                        break;
                    case 3:
                        theme_txt = "숲길 산책";
                        break;
                    case 4:
                        theme_txt = "골목골목 산책";
                        break;
                    case 5:
                        theme_txt = "캠퍼스 산책";
                        break;
                    case 6:
                        theme_txt = "데이트 산책";
                        break;
                    case 7:
                        theme_txt = "꽃길 산책";
                        break;
                    case 8:
                        theme_txt = "미술관 옆 산책";
                        break;
                    default:
                        theme_txt = "테마에러입니다";
                        break;
                }



                switch (level)
                {
                    case 1:
                        level_txt = "초급";
                        break;
                    case 2:
                        level_txt = "중급";
                        break;
                    case 3:
                        level_txt = "상급";
                        break;
                    default:
                        level_txt = "실패셈";
                        break;

                }



                switch (time)
                {
                    case 30:
                        time_txt = "30분";
                        break;
                    case 60:
                        time_txt = "1시간";
                        break;
                    case 90:
                        time_txt = "1시간 30분";
                        break;
                    case 120:
                        time_txt = "2시간";
                        break;
                    case 150:
                        time_txt = "2시간 30분";
                        break;
                    case 180:
                        time_txt = "3시간";
                        break;
                    case 210:
                        time_txt = "3시간 30분";
                        break;
                    default:
                        time_txt ="실패잼";
                        break;
                }

            }// end of for()

            ratingView = (RatingBar) getActivity().findViewById(R.id.rating);
            ratingTextView = (TextView) getActivity().findViewById(R.id.ratingNum_txt);
            nameView = (TextView) getActivity().findViewById(R.id.masil_name);
            detailView = (TextView) getActivity().findViewById(R.id.masil_detail);
            tagView = (TextView) getActivity().findViewById(R.id.textView_roadtag);
            bookmarkView = (ImageView) getActivity().findViewById(R.id.road_bookmark);

            if(appdata.checkBookmark(id)){//true 체크된거면
                bookmarkView.setImageResource(R.drawable.fiil_mark);
                bookmarkView.setTag("on");
            }
            //Log.d("Book","태그가 잘 출력되나"+bookmarkView.getTag());

           String mrate = String.format("%.2f" ,rating);
            ratingView.setRating(Float.parseFloat(mrate));


            String txt_rate = String.format("%.1f" ,rating);
            ratingTextView.setText("("+txt_rate+"/"+ratingNum+"명 참여)");

            txt_tag = "#"+ theme_txt+"\t#"+loca;
            txt_name = name;

            String txt_dist = String.format("%.2f" , distance);

            txt_detail = "현재 위치와의 거리: "+txt_dist +
                    "km\n난이도: "+level_txt+
                    "\n소요시간: "+time_txt+
                    "\n산책로 거리: "+length +"km"+
                    "\n설명: \n"+detail;

            tagView.setText(txt_tag);
            nameView.setText(txt_name);
            detailView.setText(txt_detail);

            bigimg = (ImageView) getActivity().findViewById(R.id.bigimage);
            img1 = (ImageView) getActivity().findViewById(R.id.imageView1);
            img2 = (ImageView) getActivity().findViewById(R.id.imageView2);
            img3 = (ImageView) getActivity().findViewById(R.id.imageView3);
            img4 = (ImageView) getActivity().findViewById(R.id.imageView4);
            img5 = (ImageView) getActivity().findViewById(R.id.imageView5);

            Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
            Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img1);
            Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_2.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img2);
            Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_3.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img3);
            Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_4.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img4);
            Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_5.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img5);

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Glide.with(getActivity()).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);

                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(getActivity()).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_2.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                }
            });
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(getActivity()).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_3.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                }
            });
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(getActivity()).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_4.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                }
            });
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(getActivity()).load("http://condi.swu.ac.kr/schkr/photo/" + id + "_5.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                }
            });



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

    private void addBookmark(int memberid, int roadid) throws UnsupportedEncodingException {

        String urlSuffix = "?memberid="+memberid+"&roadid="+roadid;
        class AddB extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if (s.equals("success")) {
                    Toast.makeText(getActivity(), "북마크 추가", Toast.LENGTH_SHORT).show();
                    bookmarkView.setTag("on");//setTag on
                    bookmarkView.setImageResource(R.drawable.fiil_mark);//view도 green으로 해줘야댐
            }
                else if(s.equals("errrrrroooooooooor")){
                    Toast.makeText(getActivity(),"뭐가 비었단다",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "추가 실패셈", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                Log.d("MyTag",s+"두인백그라운드 진입");
                try {
                    Log.d("MyTag",s+"두인백그라운드 try진입");

                    URL url = new URL(ADD_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;

                } catch (Exception e) {
                    return null;
                }

            }

        }

        AddB se = new AddB();
        se.execute(urlSuffix);


    }

    private void delBookmark (int memberid, int roadid) throws UnsupportedEncodingException {

        String urlSuffix = "?memberid="+memberid+"&roadid="+roadid;
        class DelB extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if (s.equals("success")) {
                    Toast.makeText(getActivity(), "북마크 삭제", Toast.LENGTH_SHORT).show();
                    bookmarkView.setTag("off");//setTag on
                    bookmarkView.setImageResource(R.drawable.empty_mark);//view도 gray으로 해줘야댐
                }
                else if(s.equals("errrrrroooooooooor")){
                    Toast.makeText(getActivity(),"뭐가 비었단다",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "삭제 실패셈", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                Log.d("MyTag",s+"두인백그라운드 진입");
                try {
                    Log.d("MyTag",s+"두인백그라운드 try진입");

                    URL url = new URL(DEL_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }

            }

        }

        DelB se = new DelB();
        se.execute(urlSuffix);


    }


}




