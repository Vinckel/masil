package com.masil.android.navigationdrawer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.navigationdrawer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by heeye on 2016-10-21.
 */

public class ProfileFrag extends Fragment {

    static String myPJSON;
    static JSONArray pschkr = null;

    static final int memid = 1;

    private static final String SETPROFILE_URL = "http://condi.swu.ac.kr/schkr/setProfile.php";

    private static final String TAG_RESULTS = "result";
    private static final String db_memberid = "memberid";
    private static final String db_height = "height";
    private static final String db_weight = "weight";
    private static final String db_favlevel = "favlevel";
    private static final String db_happy = "happy";
    private static final String db_peace = "peace";
    private static final String db_boring = "boring";
    private static final String db_sad = "sad";
    private static final String db_annoying = "annoying";

    static int favlevel;
    static double height, weight;
    static String happy, peace, boring, sad, annoying; //어떨 때

    static EditText editHeight, editWeight;
    static Spinner fav_level, happyspin, calmspin, boredspin, sadspin, angryspin;

    Button btn_submit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.top));
        txtTitle.setText("기본 정보 프로필");

        editHeight = (EditText) rootView.findViewById(R.id.editheight);
        editWeight = (EditText) rootView.findViewById(R.id.editweight);

        fav_level = (Spinner) rootView.findViewById(R.id.favorspin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.level, R.layout.spin);
        adapter.setDropDownViewResource(R.layout.spin_dropdown);
        fav_level.setPrompt("level");
        fav_level.setAdapter(adapter);
        fav_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                favlevel = position+1; // 1이면 초급 2면 중급 3면 상급
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        happyspin = (Spinner) rootView.findViewById(R.id.happyspin);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.music, R.layout.spin);
        adapter2.setDropDownViewResource(R.layout.spin_dropdown);
        happyspin.setPrompt("music");
        happyspin.setAdapter(adapter2);
        happyspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                happy = (String) happyspin.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calmspin = (Spinner) rootView.findViewById(R.id.calmspin);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.music, R.layout.spin);
        adapter3.setDropDownViewResource(R.layout.spin_dropdown);
        calmspin.setPrompt("music");
        calmspin.setAdapter(adapter3);
        calmspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                peace = (String) calmspin.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       boredspin = (Spinner) rootView.findViewById(R.id.boredspin);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.music, R.layout.spin);
        adapter4.setDropDownViewResource(R.layout.spin_dropdown);
        boredspin.setPrompt("music");
        boredspin.setAdapter(adapter4);
        boredspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boring = (String) boredspin.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       sadspin = (Spinner) rootView.findViewById(R.id.sadspin);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.music, R.layout.spin);
        adapter5.setDropDownViewResource(R.layout.spin_dropdown);
        sadspin.setPrompt("music");
        sadspin.setAdapter(adapter5);
        sadspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sad = (String) sadspin.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        angryspin = (Spinner) rootView.findViewById(R.id.angryspin);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.music, R.layout.spin);
        adapter6.setDropDownViewResource(R.layout.spin_dropdown);
        angryspin.setPrompt("music");
        angryspin.setAdapter(adapter6);
        angryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                annoying = (String) angryspin.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String getP = "http://condi.swu.ac.kr/schkr/getProfile.php?memberid="+memid;
        getProfile(getP);


        btn_submit = (Button)rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height = Double.parseDouble(editHeight.getText().toString());
                weight = Double.parseDouble(editWeight.getText().toString());


                try {
                    setProfile(memid, height, weight, favlevel, happy, peace, boring, sad, annoying);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });




        return rootView;
    }

    public void showProfile() {
        try {
            JSONObject jsonObj = new JSONObject(myPJSON);
            pschkr = jsonObj.getJSONArray(TAG_RESULTS);


            for (int i = 0; i < pschkr.length(); i++) {
                JSONObject c = pschkr.getJSONObject(i);

                int mid = c.getInt(db_memberid);
                double mheight = c.getDouble(db_height);
                double mweight = c.getDouble(db_weight);
                int mfavlevel = c.getInt(db_favlevel);
                String mhappy = c.getString(db_happy);
                String mpeace = c.getString(db_peace);
                String mboring = c.getString(db_boring);
                String msad = c.getString(db_sad);
                String mannoying = c.getString(db_annoying);

                editHeight.setText(mheight+"");
                editWeight.setText(mweight+"");

                fav_level.setPrompt(""+mfavlevel);

                happyspin.setPrompt(mhappy);
                calmspin.setPrompt(mpeace);
                boredspin.setPrompt(mboring);
                sadspin.setPrompt(msad);
                angryspin.setPrompt(mannoying);

            }// end of for()




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getProfile(String url) {

        class GetProfileJSON extends AsyncTask<String, Void, String> {
            ProgressDialog progDialog = new ProgressDialog(getActivity());

            @Override
            public void onPreExecute() {

                super.onPreExecute();
                //로딩 넣기

                progDialog.setMessage("Loading...");
                progDialog.setIndeterminate(false);
                progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progDialog.setCancelable(true);
                progDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    Log.d("MyTag", "겟프로필 진입");
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onPostExecute(String result) {
                //로딩 없애기
                myPJSON = result;
                showProfile();
                progDialog.dismiss();
            }
        }
        GetProfileJSON g = new GetProfileJSON();
        g.execute(url);
    }

    private void setProfile(int memid, double height, double weight, int favlevel, String happy, String peace, String boring, String sad, String annoying) throws UnsupportedEncodingException {
       // String textSuffix = "?memberid="+memid+"&height="+height+"&weight="+weight+"&favlevel="+favlevel+"&happy="+happy+"&peace="+peace+"&boring="+boring+"&sad="+sad+"&annoying="+annoying;
       // String urlSuffix = java.net.URLEncoder.encode(new String(textSuffix.getBytes("UTF-8")));
        String urlSuffix = "?memberid="+memid+"&height="+height+"&weight="+weight+"&favlevel="+favlevel+"&happy="+happy+"&peace="+peace+"&sad="+sad+"&boring="+boring+"&annoying="+annoying;
        class SettingPro extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                if (s.equals("success")) {
                    Toast.makeText(getActivity(), "수정 완료", Toast.LENGTH_SHORT).show();
                } else if (s.equals("fill all")) {
                    Toast.makeText(getActivity(), "모두 다 채우세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "몰라 에러야 걍 망햇음", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                Log.d("MyTag",s+"두인백그라운드 진입");
                try {
                    Log.d("MyTag",s+"두인백그라운드 try진입");

                    URL url = new URL(SETPROFILE_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }

            }

        }

        SettingPro se = new SettingPro();
        se.execute(urlSuffix);


    }

}
