package com.masil.android.navigationdrawer;


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
import android.widget.TextView;

import com.example.android.navigationdrawer.R;


/**
 * Created by heeye on 2016-09-16.
 */
public class MasilFragment extends Fragment {

    Button btn_masil, btn_location, btn_theme;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.main_frag, container, false);



        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);

        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        //toolbar.inflateMenu(R.menu.empty_menu);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.maintop));
        txtTitle.setText("");



        btn_masil = (Button)rootView.findViewById(R.id.btn_masil);
        btn_location = (Button)rootView.findViewById(R.id.btn_location);
        btn_theme = (Button)rootView.findViewById(R.id.btn_theme);

        btn_masil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MasilConFrag frag = new MasilConFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
                //Intent intent = new Intent(getActivity(), MasilConFrag.class);
                //startActivity(intent);
            }
});

        btn_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeGridFrag frag = new ThemeGridFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalGridFrag frag = new LocalGridFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();


            }
        });


        return  rootView;
        }

        }
