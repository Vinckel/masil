package com.example.android.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by heeye on 2016-09-22.
 */
public class DiaryFrag extends Fragment {

    Button btn_diary_no, btn_diary_done;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.diary_edit, container, false);

        btn_diary_no = (Button)rootView.findViewById(R.id.btn_diary_no);
        btn_diary_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 안하고 넘어가는 fragment로
                MasilRoadFrag frag = new MasilRoadFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();

            }
        });

        btn_diary_done = (Button)rootView.findViewById(R.id.btn_diary_done);
        btn_diary_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return  rootView;

    }
}
