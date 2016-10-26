package com.masil.android.navigationdrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.navigationdrawer.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by heeye on 2016-09-22.
 */
public class EditDiaryFrag extends Fragment {

    TextView txt_date;
    String currentDate;
    Button btn_diary_no, btn_diary_done;
    EditText editText;

    String startfeel, wktime, wklength, wkcount, calorie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getArguments();

        startfeel = extra.getString("startfeel");
        wktime = extra.getString("wktime");
        wklength = extra.getString("wklength");
        wkcount = extra.getString("wkcount");
        calorie = extra.getString("calorie");

        Toast.makeText(getActivity(),startfeel+wktime+calorie,Toast.LENGTH_LONG);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);

        toolbar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.top));
        txtTitle.setText("산책 정리하기");

        View rootView = inflater.inflate(R.layout.diary_edit, container, false);


        txt_date = (TextView) rootView.findViewById(R.id.txt_date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date currentTime = new Date();
        currentDate = formatter.format(currentTime);
        txt_date.setText(currentDate+" 산책");

        editText = (EditText)rootView.findViewById(R.id.edit_diary);
        editText.setSelection ( editText.length());



        btn_diary_no = (Button)rootView.findViewById(R.id.btn_diary_no);
        btn_diary_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다음에 할래요 go 메인
                MasilFragment frag = new MasilFragment();
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
                //show diary 프래그먼트 보여줘야됨
                ShowDiaryFrag frag = new ShowDiaryFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
            }
        });

        FinishFeelDia mDialog = new FinishFeelDia();
        mDialog.show(getFragmentManager(),"MYTAG");


        return  rootView;

    }//onCreateView() EditDiaryFrag

    public static class FinishFeelDia extends DialogFragment {

        Button btn_diary_next;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getDialog().setCanceledOnTouchOutside(true);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onResume() {
            super.onResume();

            int dialogWidth = getResources().getDimensionPixelSize(R.dimen.popup_widthsize);
            int dialogHeight = getResources().getDimensionPixelSize(R.dimen.popup_heightsize);

            if (getDialog() != null) {
                Window window = getDialog().getWindow();
                window.setLayout(dialogWidth, dialogHeight);
                window.setGravity(Gravity.CENTER);
            }
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            View view = mLayoutInflater.inflate(R.layout.finishifeel_dia,null);
            mBuilder.setView(view);


            final Spinner fspinner = (Spinner)view.findViewById(R.id.feelingspinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.feeling, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fspinner.setPrompt("condition");
            fspinner.setAdapter(adapter);
            fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getActivity(), "값을 선택해", Toast.LENGTH_SHORT).show();


                }
            });



            btn_diary_next = (Button)view.findViewById(R.id.btn_diary_next);
            btn_diary_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //별점 다이얼로그
                    RatingDialog mRatingDia = new RatingDialog();
                    mRatingDia.show(getFragmentManager(),"MYTAG");
                    onStop();
                }
            });

            return mBuilder.create();
        }//onCreateDialog() FinishDia
    }//FinishFeelDia

    public static class RatingDialog extends DialogFragment {

        Button btn_rating_next;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getDialog().setCanceledOnTouchOutside(true);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onResume() {
            super.onResume();

            int dialogWidth = getResources().getDimensionPixelSize(R.dimen.popup_widthsize);
            int dialogHeight = getResources().getDimensionPixelSize(R.dimen.popup_heightsize);

            if (getDialog() != null) {
                Window window = getDialog().getWindow();
                window.setLayout(dialogWidth, dialogHeight);
                window.setGravity(Gravity.CENTER);
            }
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            View view = mLayoutInflater.inflate(R.layout.ratingbar_dia,null);
            mBuilder.setView(view);

            Spinner starspinner = (Spinner)view.findViewById(R.id.starspinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.rating, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            starspinner.setPrompt("★☆☆☆☆");
            starspinner.setAdapter(adapter);


            btn_rating_next = (Button)view.findViewById(R.id.btn_rating_next);
            btn_rating_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStop();

                }
            });

            return mBuilder.create();
        }
    }
}
