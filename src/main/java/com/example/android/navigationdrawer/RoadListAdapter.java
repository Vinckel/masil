package com.example.android.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by heeye on 2016-09-29.
 */

public class RoadListAdapter extends BaseAdapter
{



    //가져온 정보 저장할 변수
    private RoadListInfo mRoad;
    private Context mContext;

    TextView roadnameText, roadlocaText, roaddistText;
    ImageView roadImage;
    ImageView roadBookmark;
    int roadId;
    LinearLayout road_list_item;
    RequestManager mRequestManager;




    //리스트 아이템 데이터를 저장할 배열열
    private ArrayList<RoadListInfo> mRoadData;


    public RoadListAdapter(Context context){
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
        boolean flag;

        if (v == null) {

            v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.road_list_item, parent,false);
        }

        road_list_item = (LinearLayout)v.findViewById(R.id.road_list_item);

        roadlocaText = (TextView) v.findViewById(R.id.road_loca);
        roadnameText = (TextView) v.findViewById(R.id.road_name);
        roaddistText = (TextView) v.findViewById(R.id.road_distance);
        roadImage = (ImageView) v.findViewById(R.id.road_img);
        roadBookmark = (ImageView) v.findViewById(R.id.bookmark1);

        mRoad = (RoadListInfo) getItem(position);

        road_list_item.setTag(mRoad);


        if (mRoad != null) {

            roadnameText.setText(mRoad.getmRoadName());
            roadlocaText.setText(mRoad.getmRoadLoca());
            roaddistText.setText(mRoad.getmRoadDist()+"km");
            //flag = appdata.checkBookmark(mRoad.getmRoadId());
           // Log.d("MyTag","북마크 잘 받아오는건가"+mRoad.getmBookmark()+"아이디"+mRoad.getmRoadId());
            if(mRoad.getmBookmark()){
                roadBookmark.setImageResource(R.drawable.fiil_mark);
            }
            Glide.with(mContext).load("http://condi.swu.ac.kr/schkr/prevphoto/" + mRoad.getmRoadId() + ".png").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(roadImage);

            
            
        }
        return v;
    }

    public void add(RoadListInfo roadinfo){
        mRoadData.add(roadinfo);}


}
