package com.masil.android.navigationdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPagerContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPagerContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerContent extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int num;
    private int mId;




    ImageView bigimg,img1,img2, img3, img4, img5;

    private String theme, level, time, info;
    private TextView tvName, tvTag, tvDetail;
    private ApplicationData roadData;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPagerContent newInstance(int num) {
        ViewPagerContent fragment = new ViewPagerContent();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, num);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewPagerContent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            num = getArguments().getInt(ARG_PARAM1);
        }
        roadData = (ApplicationData) getActivity().getApplicationContext();



    }

    public void setTvName(String newText) {
        tvName.setText(newText);
    }
    public void setTvTag(String newText){tvTag.setText(newText);}
    public void setTvDetail(String newText){tvDetail.setText(newText);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_road, container, false);
        // Inflate the layout for this fragment

        bigimg = (ImageView) v.findViewById(R.id.pager_bigimage);
        img1 = (ImageView) v.findViewById(R.id.pager_imageView1);
        img2 = (ImageView) v.findViewById(R.id.pager_imageView2);
        img3 = (ImageView) v.findViewById(R.id.pager_imageView3);
        img4 = (ImageView) v.findViewById(R.id.pager_imageView4);
        img5 = (ImageView) v.findViewById(R.id.pager_imageView5);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);


        mId = Integer.parseInt(roadData.id_list[num]);


        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + mId + "_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + mId + "_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img1);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + mId + "_2.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img2);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + mId + "_3.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img3);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + mId + "_4.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img4);
        Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + mId + "_5.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img5);

        tvName = (TextView) v.findViewById(R.id.pager_name);
        tvName.setText(roadData.name_list[num]);


        switch (Integer.parseInt(roadData.theme_list[num]))
        {
            case 1:
                theme = "역사 속 산책";
                break;
            case 2:
                theme = "물길 따라 산책";
                break;
            case 3:
                theme = "숲길 산책";
                break;
            case 4:
                theme = "골목골목 산책";
                break;
            case 5:
                theme = "캠퍼스 산책";
                break;
            case 6:
                theme = "데이트 산책";
                break;
            case 7:
                theme = "꽃길 산책";
                break;
            case 8:
                theme = "미술관 옆 산책";
                break;

        }

        switch (Integer.parseInt(roadData.level_list[num]))
        {
            case 1:
                level = "초급";
                break;
            case 2:
                level = "중급";
                break;
            case 3:
                level = "상급";
                break;
            default:
                level = "실패셈";

        }

        switch (roadData.time_list[num])
        {
            case "30":
                time = "30분";
                break;
            case "60":
                time = "1시간";
                break;
            case "90":
                time = "1시간 30분";
                break;
            case "120":
                time = "2시간";
                break;
            case "150":
                time = "2시간 30분";
                break;
            case "180":
                time = "3시간";
                break;
            case "210":
                time = "3시간 30분";
                break;
            default:
                time ="실패잼";
        }


        tvTag = (TextView) v.findViewById(R.id.pager_roadtag);
        tvTag.setText("#"+theme+"\t#"+roadData.loca_list[num]);

        tvDetail = (TextView)v.findViewById(R.id.pager_detail);
        info = "현재 위치와의 거리: \n난이도: "+level+"\n소요시간: "+time+"\n산책로 거리: "+roadData.length_list[num]+"km\n설명:\n"+roadData.detail_list[num];
        tvDetail.setText(info);



        return v;
    }

    @Override
    public void onClick(View v) { //산책로 이미지 클릭했을때 큰사진으로 바뀌어야함...
        switch (v.getId()) {
            case R.id.pager_imageView1:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + (num + 1) + "_1.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.pager_imageView2:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + (num + 1) + "_2.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.pager_imageView3:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + (num + 1) + "_3.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.pager_imageView4:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + (num + 1) + "_4.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
            case R.id.pager_imageView5:
                Glide.with(this).load("http://condi.swu.ac.kr/schkr/photo/" + (num + 1) + "_5.jpg").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigimg);
                break;
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}