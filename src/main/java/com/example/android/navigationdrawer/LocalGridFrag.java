package com.example.android.navigationdrawer;

import android.os.Bundle;
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

/**
 * Created by heeye on 2016-10-07.
 */

public class LocalGridFrag extends Fragment {

    Button btn_local_next;
    ImageView bg_underbar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);

        toolbar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.top));
        txtTitle.setText("지역 선택");

        View rootView = inflater.inflate(R.layout.local_grid, container, false);

        bg_underbar = (ImageView)rootView.findViewById(R.id.localgrid_underbar);

         Glide.with(this).load(R.drawable.bgbottom).into(bg_underbar);

        btn_local_next = (Button)rootView.findViewById(R.id.btn_local_next);

        btn_local_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalRoadListFrag frag = new LocalRoadListFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
            }
        });


        return rootView;

    }



    }
