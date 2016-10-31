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
import android.widget.TextView;

/**
 * Created by heeye on 2016-09-22.
 */
public class ShowDiaryFrag extends Fragment {

    Button btn_diary_finish;
    String diarytxt;
    TextView review_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getArguments();
        diarytxt = extra.getString("diarytxt");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.showdiary, container, false);

        review_text = (TextView) rootView.findViewById(R.id.review_text);
        review_text.setText(diarytxt);


        btn_diary_finish = (Button) rootView.findViewById(R.id.btn_diary_finish);
        btn_diary_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MasilFragment frag = new MasilFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
            }
        });

        return  rootView;
    }
}
