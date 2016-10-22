package com.masil.android.navigationdrawer;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.navigationdrawer.R;

/**
 * Created by heeye on 2016-09-30.
 */

public class ThemeGridFrag extends Fragment implements View.OnClickListener{

    ImageView theme1,theme2, theme3,theme4, theme5, theme6, theme7, theme8;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);

        toolbar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.top));
        txtTitle.setText("테마별 산책로");

        View rootView = inflater.inflate(R.layout.theme_grid, container, false);

        theme1 = (ImageView)rootView.findViewById(R.id.theme1);
        theme2 = (ImageView)rootView.findViewById(R.id.theme2);
        theme3 = (ImageView)rootView.findViewById(R.id.theme3);
        theme4 = (ImageView)rootView.findViewById(R.id.theme4);
        theme5 = (ImageView)rootView.findViewById(R.id.theme5);
        theme6 = (ImageView)rootView.findViewById(R.id.theme6);
        theme7 = (ImageView)rootView.findViewById(R.id.theme7);
        theme8 = (ImageView)rootView.findViewById(R.id.theme8);

        Glide.with(this).load(R.drawable.theme1).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme1);
        Glide.with(this).load(R.drawable.theme2).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme2);
        Glide.with(this).load(R.drawable.theme3).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme3);
        Glide.with(this).load(R.drawable.theme4).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme4);
        Glide.with(this).load(R.drawable.theme5).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme5);
        Glide.with(this).load(R.drawable.theme6).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme6);
        Glide.with(this).load(R.drawable.theme7).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme7);
        Glide.with(this).load(R.drawable.theme8).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(theme8);

        theme1.setOnClickListener(this);
        theme2.setOnClickListener(this);
        theme3.setOnClickListener(this);
        theme4.setOnClickListener(this);
        theme5.setOnClickListener(this);
        theme6.setOnClickListener(this);
        theme7.setOnClickListener(this);
        theme8.setOnClickListener(this);

        return rootView;

    }



    @Override
    public void onClick(View v) {

        RoadListFrag frag = new RoadListFrag();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        switch(v.getId())
        {
            case R.id.theme1:


                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme2:

                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme3:
                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme4:
                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme5:
                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme6:
                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme7:
                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;
            case R.id.theme8:
                ft.replace(R.id.content_frame, frag);
                ft.commit();

                break;

        }


    }
}
