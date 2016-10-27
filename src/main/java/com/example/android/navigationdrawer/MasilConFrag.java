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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by heeye on 2016-09-20.
 */
public class MasilConFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    Button btn_masil_next;
    String select_condition;
    String select_time;

    public void MasilFragment(){}

    public static MasilFragment newInstance(String param1, String param2){
        MasilFragment frag = new MasilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2,param2);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

       // Toast.makeText(getActivity(),"현재 시작점과의 위치가 멀어요!",Toast.LENGTH_SHORT).show();

        View rootView = inflater.inflate(R.layout.masilcon_frag, container, false);

        //상단바 바꾸기~~어예~~~
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
       // toolbar.inflateMenu(R.menu.menu_home);


        toolbar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.top));
        txtTitle.setText("마실마실 콕");


        btn_masil_next = (Button)rootView.findViewById(R.id.btn_masil_next);
        btn_masil_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //MasilRoadFrag frag = new MasilRoadFrag();
                MyViewPager frag = new MyViewPager();

                Bundle args = new Bundle();
                args.putString(ARG_PARAM1,select_condition);
                args.putString(ARG_PARAM2,select_time);
                frag.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();

            }
        });

        final Spinner cspinner = (Spinner)rootView.findViewById(R.id.conditionspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.condition, R.layout.spin);
        adapter.setDropDownViewResource(R.layout.spin_dropdown);
        cspinner.setPrompt("condition");
        cspinner.setAdapter(adapter);
        cspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String str = (String) cspinner.getSelectedItem();
                select_condition = str;
               // Toast.makeText(getActivity(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "값을 선택해", Toast.LENGTH_SHORT).show();


            }
        });


        final Spinner tspinner = (Spinner) rootView.findViewById(R.id.timespinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.time, R.layout.spin);
        adapter1.setDropDownViewResource(R.layout.spin_dropdown);
        tspinner.setPrompt("time");
        tspinner.setAdapter(adapter1);
        tspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               switch (position)
               {
                   case 0: //30분
                       select_time = 30+"";
                       break;
                   case 1: //60분
                       select_time = 60+"";
                       break;
                   case 2: // 90분
                       select_time = 90+"";
                       break;
                   case 3://120분
                       select_time = 120+"";
                       break;
                   case 4://150분
                       select_time = 150+"";
                       break;
               }

               // Toast.makeText(getActivity(), select_time+"",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "값을 선택해", Toast.LENGTH_SHORT).show();


            }
        });

        return  rootView;
    }

}
