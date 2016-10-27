package com.example.android.navigationdrawer;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by heeye on 2016-09-29.
 */

public class RoadListAdapter extends BaseAdapter implements View.OnClickListener
{

    //가져온 정보 저장할 변수
    private RoadListInfo mRoad;
    private Context mContext;

    TextView roadnameText, roadlocaText;
    LinearLayout road_list_item;


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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.road_list_item, parent,false);
        }
        roadlocaText = (TextView) v.findViewById(R.id.road_loca);
        roadnameText = (TextView) v.findViewById(R.id.road_name);
        road_list_item = (LinearLayout)v.findViewById(R.id.road_list_item);

        mRoad = (RoadListInfo) getItem(position);

        road_list_item.setTag(mRoad);

        if (mRoad != null) {
            roadnameText.setText(mRoad.getmRoadName());
            roadlocaText.setText(mRoad.getmRoadLoca());
            road_list_item.setOnClickListener(this);
        }
        return v;
    }

    public void add(RoadListInfo roadinfo){
        mRoadData.add(roadinfo);}


    @Override
    public void onClick(View view) {

        RoadListInfo clickItem = (RoadListInfo) view.getTag();

       // view.getId();
            MasilRoadFrag frag = new MasilRoadFrag();
            FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.commit();



    }
}
