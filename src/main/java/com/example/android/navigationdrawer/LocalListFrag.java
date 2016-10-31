package com.example.android.navigationdrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by heeye on 2016-10-07.
 */

public class LocalListFrag extends Fragment {

    private static final String ARG_PARAM1 = "selectCount";
    private static final String ARG_PARAM2 = "selectArray";




    Button btn_local_next;
    ImageView bg_underbar;
    private ListView mListView = null;
    private CheckBox mAllCheckBox = null;
    private Button mCountBt = null;

    // Data를 관리해주는 Adapter
    private CustomAdapter mCustomAdapter = null;
    // 제네릭(String)을 사용한 ArrayList
    private ArrayList<String> mArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         mArrayList = new ArrayList<String>();



    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.local_list, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText("지역 선택");


        mArrayList.add("강남구");
        mArrayList.add("강동구");
        mArrayList.add("강북구");
        mArrayList.add("강서구");
        mArrayList.add("관악구");
        mArrayList.add("광진구");
        mArrayList.add("구로구");
        mArrayList.add("금천구");
        mArrayList.add("노원구");
        mArrayList.add("도봉구");
        mArrayList.add("동대문구");
        mArrayList.add("동작구");
        mArrayList.add("마포구");
        mArrayList.add("서대문구");
        mArrayList.add("서초구");
        mArrayList.add("성동구");
        mArrayList.add("성북구");
        mArrayList.add("송파구");
        mArrayList.add("양천구");
        mArrayList.add("영등포구");
        mArrayList.add("용산구");
        mArrayList.add("은평구");
        mArrayList.add("종로구");
        mArrayList.add("중구");
        mArrayList.add("중랑구");


        mCustomAdapter = new CustomAdapter(getActivity(), mArrayList);
        mListView = (ListView) rootView.findViewById(R.id.local_listview);

        mListView.setAdapter(mCustomAdapter);
        mListView.setOnItemClickListener(mItemClickListener);

        btn_local_next = (Button) rootView.findViewById(R.id.btn_local_next);

        btn_local_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCustomAdapter.getChecked().size()!=0){

                LocalRoadListFrag frag = new LocalRoadListFrag();

                 Bundle args = new Bundle();
                args.putInt(ARG_PARAM1,mCustomAdapter.getChecked().size());

                String[] selectArray = new String[mCustomAdapter.getChecked().size()];

                for (int i = 0; i < mCustomAdapter.getChecked().size(); i++) {
                    int num = mCustomAdapter.getChecked().get(i);
                    selectArray[i] = mArrayList.get(num);
                    Log.d("ArrayTag","지역구 잘 들어가나"+selectArray[i]);
                }
                args.putStringArray(ARG_PARAM2,selectArray);

                frag.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.commit();
                }
                else{
                    Toast.makeText(getActivity(),"지역을 선택해주세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return rootView;

    }

    // ListView 안에 Item을 클릭시에 호출되는 Listener
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               // Toast.makeText(getActivity(), "" + (position), Toast.LENGTH_SHORT).show();
                mCustomAdapter.setChecked(position);
                // Data 변경시 호출 Adapter에 Data 변경 사실을 알려줘서 Update 함.
                mCustomAdapter.notifyDataSetChanged();


        }
    };


    class CustomAdapter extends BaseAdapter {

        private ViewHolder viewHolder = null;
        // 뷰를 새로 만들기 위한 Inflater
        private LayoutInflater inflater = null;
        private ArrayList<String> sArrayList = new ArrayList<String>();
        private boolean[] isCheckedConfirm;

        public CustomAdapter(Context c, ArrayList<String> mList) {
            inflater = LayoutInflater.from(c);
            this.sArrayList = mList;
            // ArrayList Size 만큼의 boolean 배열을 만든다.
            // CheckBox의 true/false를 구별 하기 위해
            this.isCheckedConfirm = new boolean[sArrayList.size()];
        }



        public void setChecked(int position) {

            isCheckedConfirm[position] = !isCheckedConfirm[position];
            if(mCustomAdapter.getChecked().size()>5){
                Toast.makeText(getActivity(),"5개까지 선택할 수 있습니다.",Toast.LENGTH_SHORT).show();
                isCheckedConfirm[position] = !isCheckedConfirm[position];
            }
        }

        public ArrayList<Integer> getChecked() {


                int tempSize = isCheckedConfirm.length;
                ArrayList<Integer> mArrayList = new ArrayList<Integer>();
                for (int b = 0; b < tempSize; b++) {
                    if (isCheckedConfirm[b]) {
                        mArrayList.add(b);
                    }
                }
                return mArrayList;


        }

        @Override
        public int getCount() {
            return sArrayList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        class ViewHolder {
            // 새로운 Row에 들어갈 CheckBox
            private CheckBox cBox = null;
            private TextView cText = null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // ConvertView가 null 일 경우
            View v = convertView;

            if (v == null) {
                viewHolder = new ViewHolder();
                // View를 inflater 시켜준다.
                v = inflater.inflate(R.layout.local_list_item, null);
                viewHolder.cBox = (CheckBox) v.findViewById(R.id.main_check_box);
                viewHolder.cText = (TextView) v.findViewById(R.id.main_check_text);
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) v.getTag();
            }

            // CheckBox는 기본적으로 이벤트를 가지고 있기 때문에 ListView의 아이템
            // 클릭리즈너를 사용하기 위해서는 CheckBox의 이벤트를 없애 주어야 한다.
            viewHolder.cBox.setClickable(false);
            viewHolder.cBox.setFocusable(false);

            viewHolder.cText.setText(sArrayList.get(position));
            // isCheckedConfirm 배열은 초기화시 모두 false로 초기화 되기때문에
            // 기본적으로 false로 초기화 시킬 수 있다.
            viewHolder.cBox.setChecked(isCheckedConfirm[position]);

            return v;
        }


    }
}

