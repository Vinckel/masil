package com.masil.android.navigationdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.navigationdrawer.R;

/**
 * Created by heeye on 2016-09-29.
 */

public class RoadListFrag extends Fragment {

    ListView roadList;
    RoadListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.road_list_view, container, false);

        adapter = new RoadListAdapter(getActivity());

        roadList = (ListView) rootView.findViewById(R.id.listview1);
        roadList.setAdapter(adapter);

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
