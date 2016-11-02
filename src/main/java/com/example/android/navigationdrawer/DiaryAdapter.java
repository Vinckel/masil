package com.example.android.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by heeye on 2016-11-02.
 */

public class DiaryAdapter extends BaseAdapter{

    private DiaryData mDiary;
    private Context mContext;

    TextView roadnameText, wkdateText;
    RatingBar myRatingbar;
    ImageView roadImage;
    int roadId;
    LinearLayout diary_list_item;


    ArrayList<DiaryData> mDiaryData;

    public DiaryAdapter(Context context){
        super();
        mContext = context;
        mDiaryData = new ArrayList<DiaryData>();
    }



    @Override
    public int getCount() {
        return mDiaryData.size();
    }

    @Override
    public Object getItem(int position) {
        return mDiaryData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getRoadId(){
        return mDiary.getMroadId();
    }

    public int getDiaryNumber(){
        return mDiary.getNumber();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {

            v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.diary_list_item, parent,false);
        }

        diary_list_item = (LinearLayout)v.findViewById(R.id.diary_list_item);

        wkdateText = (TextView) v.findViewById(R.id.d_date);
        roadnameText = (TextView) v.findViewById(R.id.d_roadname);
        myRatingbar = (RatingBar) v.findViewById(R.id.d_rating);
        roadImage = (ImageView) v.findViewById(R.id.d_roadimg);


        mDiary = (DiaryData) getItem(position);

        diary_list_item.setTag(mDiary);


        if (mDiary != null) {
            roadnameText.setText(mDiary.getMroadName());
            wkdateText.setText(mDiary.getMwkdate());
            String mRating = String.valueOf(mDiary.getMrating());

            myRatingbar.setRating(Float.parseFloat(mRating));

            Glide.with(mContext).load("http://condi.swu.ac.kr/schkr/prevphoto/" + mDiary.getMroadId() + ".png").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(roadImage);

        }
        return v;
    }

    public void add(DiaryData diarydata){
        mDiaryData.add(diarydata);}

}
