package com.example.android.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by heeye on 2016-09-22.
 */
public class ShowDiaryFrag extends Fragment {

    int memberid = 1;



    Button btn_diary_finish;
    String startfeel, finishfeel, wktime, wklength, wkcount, calorie, selectName, selectImg;
    String diarytxt, resultdate;
    TextView name_txt,review_text, timer_txt,length_txt,count_txt,calorie_txt,startfeel_txt, finishfeel_txt,date_txt;
    private String currentDate;
    int selectId;
    ImageView show_img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getArguments();
        diarytxt = extra.getString("diarytxt");
        startfeel = extra.getString("startfeel");
        finishfeel = extra.getString("finishfeel");
        wktime = extra.getString("wktime");
        wklength = extra.getString("wklength");
        wkcount = extra.getString("wkcount");
        calorie = extra.getString("calorie");
        selectName = extra.getString("selectName");
        selectId = extra.getInt("selectId");
        selectImg = extra.getString("selectImg");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        show_img = (ImageView) rootView.findViewById(R.id.show_img);

        String imggg = "http://condi.swu.ac.kr/schkr/"+selectImg;

        Glide.with(this).load(selectImg).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(show_img);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date currentTime = new Date();
        currentDate = formatter.format(currentTime);
        date_txt.setText(currentDate+" 산책");


        review_text.setText(diarytxt);
        name_txt.setText(selectName);
        timer_txt.setText(wktime);
        length_txt.setText(wklength);
        count_txt.setText(wkcount);
        calorie_txt.setText(calorie);
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





        btn_diary_finish = (Button) rootView.findViewById(R.id.btn_diary_finish);
        btn_diary_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MasilFragment frag = new MasilFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null);
                ft.commit();



            }
        });

        return  rootView;
    }




}
