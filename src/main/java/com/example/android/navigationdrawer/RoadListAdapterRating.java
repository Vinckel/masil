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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by heeye on 2016-10-31.
 */

public class RoadListAdapterRating extends BaseAdapter {

    //가져온 정보 저장할 변수
    private RoadListInfo mRoad;
    private Context mContext;

    TextView roadnameText, roadlocaText;
    RatingBar roadRatingbar;
    ImageView roadImage;
    int roadId;
    LinearLayout road_list_item_rating;
    RequestManager mRequestManager;



    //리스트 아이템 데이터를 저장할 배열열
    private ArrayList<RoadListInfo> mRoadData;


    public RoadListAdapterRating(Context context){
        super();
        mContext = context;
        mRoadData = new ArrayList<RoadListInfo>();
    }


    @Override
    public int getCount() {
        return mRoadData.size();

    }

    @Override
    public Object getItem(int position) {
        return mRoadData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getRoadId(){
        return mRoad.getmRoadId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;



        if (v == null) {

            v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.road_list_item_rating, parent,false);
        }

        road_list_item_rating = (LinearLayout)v.findViewById(R.id.road_list_item_rating);

        roadlocaText = (TextView) v.findViewById(R.id.road_loca);
        roadnameText = (TextView) v.findViewById(R.id.road_name);
        roadRatingbar = (RatingBar) v.findViewById(R.id.road_rating);
        roadImage = (ImageView) v.findViewById(R.id.road_img);


        mRoad = (RoadListInfo) getItem(position);

        road_list_item_rating.setTag(mRoad);


        if (mRoad != null) {
            roadnameText.setText(mRoad.getmRoadName());
            roadlocaText.setText(mRoad.getmRoadLoca());
            String mRating = mRoad.getmRoadRating();

            roadRatingbar.setRating(Float.parseFloat(mRating));

            Glide.with(mContext).load("http://condi.swu.ac.kr/schkr/prevphoto/16.png").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(roadImage);

            //mRequestManager.load("http://condi.swu.ac.kr/schkr/prevphoto/" + mRoad.getmRoadId() + ".png").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(roadImage);

        }
        return v;
    }

    public void add(RoadListInfo roadinfo){
        mRoadData.add(roadinfo);}


}
