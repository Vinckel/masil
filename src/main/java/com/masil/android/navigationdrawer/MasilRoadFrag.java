package com.masil.android.navigationdrawer;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.navigationdrawer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.android.navigationdrawer.R.id.imageView1;

/**
 * Created by heeye on 2016-09-21.
 */
public class MasilRoadFrag extends Fragment implements View.OnClickListener {

    Button btn_start;

    ImageView bigimg,img1,img2, img3, img4, img5;

    TextView nameView, detailView, tagView;
    static String txt_tag, txt_name, txt_detail;

    String user_condition, user_time;

    static String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String db_id = "id";
    private static final String db_name = "name";
    private static final String db_time = "time";
    private static final String db_length = "length";
    private static final String db_level = "level";
    private static final String db_theme = "theme";
    private static final String db_loca = "loca";
    private static final String db_detail = "detail";

    static JSONArray schkr = null;

    static String[] id_list,name_list, loca_list, time_list,
            level_list,theme_list, length_list,detail_list;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        Bundle extra = getArguments();
        user_condition = extra.getString("param1");
        user_time = extra.getString("param2");*/

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.masilroad_frag, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.top));
        txtTitle.setText("테마별 산책로");

        id_list = new String[5];
        time_list = new String[5];
        name_list = new String[5];
        loca_list = new String[5];
        level_list = new String[5];
        theme_list = new String[5];
        length_list = new String[5];
        detail_list = new String[5];


/*
        nameView = (TextView) rootView.findViewById(R.id.masil_name);
        detailView = (TextView) rootView.findViewById(R.id.masil_detail);
        tagView = (TextView) rootView.findViewById(R.id.textView_roadtag); //이걸 showlist로 옮겨보자 그랬더니 되었다...

        */
        bigimg = (ImageView) rootView.findViewById(R.id.bigimage);
        img1 = (ImageView) rootView.findViewById(imageView1);
        img2 = (ImageView) rootView.findViewById(R.id.imageView2);
        img3 = (ImageView) rootView.findViewById(R.id.imageView3);
        img4 = (ImageView) rootView.findViewById(R.id.imageView4);
        img5 = (ImageView) rootView.findViewById(R.id.imageView5);

        getData("http://condi.swu.ac.kr/schkr/receive_road.php");


        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);

        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img1);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_2.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img2);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_3.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img3);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_4.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img4);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_5.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img5);





        btn_start = (Button) rootView.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WalkingFrag frag = new WalkingFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
            }
        });

        return rootView;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.imageView1:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.imageView2:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_2.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.imageView3:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_3.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.imageView4:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_4.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.imageView5:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/1_5.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;

        }

    }


    public void showList() {


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            schkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < schkr.length(); i++) {
                JSONObject c = schkr.getJSONObject(i);

                int id = c.getInt(db_id); //int타입인데 string으로 받아도 잘 받아와지는지 확인해야됨
                String name = c.getString(db_name);
                int time = c.getInt(db_time);
                double length =c.getDouble(db_length);
                int level = c.getInt(db_level);
                int theme = c.getInt(db_theme);
                String loca = c.getString(db_loca);
                String detail = c.getString(db_detail);

                switch (theme) //테마 이름....
                {
                    case 1:
                        theme_list[i] = "역사 속 산책";
                        break;
                    case 2:
                        theme_list[i] = "물길 따라 산책";
                        break;
                    case 3:
                        theme_list[i] = "숲길 산책";
                        break;
                    case 4:
                        theme_list[i] = "골목골목 산책";
                        break;
                    case 5:
                        theme_list[i] = "캠퍼스 산책";
                        break;
                    case 6:
                        theme_list[i] = "데이트 산책";
                        break;
                    case 7:
                        theme_list[i] = "꽃길 산책";
                        break;
                    case 8:
                        theme_list[i] = "미술관 옆 산책";
                        break;
                    default:
                        theme_list[i] = "테마에러입니다";
                        break;
                }

                switch (level)
                {
                    case 1:
                        level_list[i] = "초급";
                        break;
                    case 2:
                        level_list[i] = "중급";
                        break;
                    case 3:
                        level_list[i] = "상급";
                        break;
                    default:
                        level_list[i] = "실패셈";
                        break;

                }

                switch (time)
                {
                    case 30:
                        time_list[i] = "30분";
                        break;
                    case 60:
                        time_list[i] = "1시간";
                        break;
                    case 90:
                        time_list[i] = "1시간 30분";
                        break;
                    case 120:
                        time_list[i] = "2시간";
                        break;
                    case 150:
                        time_list[i] = "2시간 30분";
                        break;
                    case 180:
                        time_list[i] = "3시간";
                        break;
                    case 210:
                        time_list[i] = "3시간 30분";
                        break;
                    default:
                        time_list[i] ="실패잼";
                        break;
                }




                id_list[i] = String.valueOf(id); //int타입인데 string으로 받아도 잘 받아와지는지 확인해야됨
                name_list[i] = name;
                //time_list[i] = String.valueOf(time);
                length_list[i] = String.valueOf(length);
                //level_list[i] = String.valueOf(level);

                loca_list[i] =loca;
                detail_list[i] =detail;

                //여기서 로그 찍으면 다섯개 다 받아지는데 위에 올라가서 가져오면 안됨 이유가 무엇?
            }// end of for()


            nameView = (TextView) getActivity().findViewById(R.id.masil_name);
            detailView = (TextView) getActivity().findViewById(R.id.masil_detail);
            tagView = (TextView) getActivity().findViewById(R.id.textView_roadtag);

            txt_tag = "#"+ theme_list[0]+"\t\t#"+loca_list[0];
            txt_name = name_list[0];
            txt_detail = "현재 위치와의 거리: \n난이도: "+level_list[0]+
                    "\n소요시간: "+time_list[0]+
                    "\n산책로 거리: "+length_list[0]+"km"+
                    "\n설명: \n"+detail_list[0];

            Log.d("MyTag","뭐가 없는걸까"+txt_tag+txt_name+txt_detail);

            tagView.setText(txt_tag);
            nameView.setText(txt_name);
            detailView.setText(txt_detail);

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




