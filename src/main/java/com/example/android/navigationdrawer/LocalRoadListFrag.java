package com.example.android.navigationdrawer;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by heeye on 2016-10-05.
 */

public class LocalRoadListFrag extends Fragment {



    ListView roadList2;
    RoadListAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.top));
        actionBar.setDisplayShowTitleEnabled(true);

        View rootView = inflater.inflate(R.layout.road_list_local_view, container, false);

        adapter = new RoadListAdapter(getActivity());

        roadList2 = (ListView) rootView.findViewById(R.id.listview2);
        roadList2.setAdapter(adapter);





        RoadListInfo r1 = new RoadListInfo("노원구","봉화산둘레길");
        adapter.add(r1);


        RoadListInfo r2 = new RoadListInfo("중구","북한산 둘레길");
        adapter.add(r2);

        RoadListInfo r3 = new RoadListInfo("중랑구","중랑산 둘레길");
        adapter.add(r2);

        adapter.notifyDataSetChanged();


        return rootView;
    }



}
