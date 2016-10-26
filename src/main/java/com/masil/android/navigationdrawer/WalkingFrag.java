package com.masil.android.navigationdrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.navigationdrawer.R;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by heeye on 2016-09-21.
 */
public class WalkingFrag extends Fragment {
    String myJSON;
    JSONArray schkr = null;
    static double[][] mpolypoint;
    public static final String APP_TAG = "Masil";

    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    private static WalkingFrag mInstance = null;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private StepCountReporter mReporter;

    static String startfeel, wktime, wklength, wkcount, calorie;

    private static final String TAG_RESULTS = "result";
    private static final String db_id = "id";
    private static final String db_num = "num";
    private static final String db_xpoint = "xpoint";
    private static final String db_ypoint = "ypoint";

    private static final String ARG_PARAM1 = "startfeel";
    private static final String ARG_PARAM2 = "wktime";
    private static final String ARG_PARAM3 = "wklength";
    private static final String ARG_PARAM4 = "wkcount";
    private static final String ARG_PARAM5 = "calorie";

    MapView mMapView;
    MapPolyline mPolyline;
    private double lat, lon;

    Button btn_finish;

    static TextView walkingtimer; //타이머
    static long mBaseTime; //기준 타임
    static long mPauseTime; //일시정지 타임


    public static WalkingFrag newInstance(String param1, String param2, String param3, String param4, String param5){
        WalkingFrag frag = new WalkingFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2,param2);
        args.putString(ARG_PARAM2,param3);
        args.putString(ARG_PARAM2,param4);
        args.putString(ARG_PARAM2,param5);
        frag.setArguments(args);
        return frag;
    }

    public static WalkingFrag getInstance() {
        return mInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mStore.disconnectService();
        mTimer.removeMessages(0);
        super.onDestroy();
    }

    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {

        @Override
        public void onConnected() {
            Log.d(APP_TAG, "Health data service is connected.");
            HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
            mReporter = new StepCountReporter(mStore);

            try {
                // Check whether the permissions that this application needs are acquired
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    // Request the permission for reading step counts if it is not acquired
                    pmsManager.requestPermissions(mKeySet).setResultListener(mPermissionListener);
                } else {
                    // Get the current step count and display it
                    mReporter.start();
                }
            } catch (Exception e) {
                Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
                Log.e(APP_TAG, "Permission setting fails.");
            }
        }

        @Override
        public void onConnectionFailed(HealthConnectionErrorResult error) {
            Log.d(APP_TAG, "Health data service is not available.");
            showConnectionFailureDialog(error);
        }

        @Override
        public void onDisconnected() {
            Log.d(APP_TAG, "Health data service is disconnected.");
        }
    };

    private void showConnectionFailureDialog(HealthConnectionErrorResult error) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        mConnError = error;
        String message = "Connection with S Health is not available";

        if (mConnError.hasResolution()) {
            switch(error.getErrorCode()) {
                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                    message = "Please install S Health";
                    break;
                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                    message = "Please upgrade S Health";
                    break;
                case HealthConnectionErrorResult.PLATFORM_DISABLED:
                    message = "Please enable S Health";
                    break;
                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                    message = "Please agree with S Health policy";
                    break;
                default:
                    message = "Please make S Health available";
                    break;
            }
        }

        alert.setMessage(message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (mConnError.hasResolution()) {
                    mConnError.resolve(getActivity());
                }
            }
        });

        if (error.hasResolution()) {
            alert.setNegativeButton("Cancel", null);
        }

        alert.show();
    }

    private final HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> mPermissionListener =
            new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {

            @Override
            public void onResult(HealthPermissionManager.PermissionResult result) {
                Log.d(APP_TAG, "Permission callback is received.");
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();

                if (resultMap.containsValue(Boolean.FALSE)) {
                    drawStepCount("");
                    showPermissionAlarmDialog();
                } else {
                    // Get the current step count and display it
                    mReporter.start();
                }
            }
        };

    private void showPermissionAlarmDialog() {
//        if (isFinishing()) {
//            return;
//        }

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Notice");
        alert.setMessage("All permissions should be acquired");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    public void drawStepCount(String count){

        TextView stepCountTv = (TextView)getActivity().findViewById(R.id.calvaltext);

        // Display the today step count so far
        stepCountTv.setText(count + " kcal");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.walking_frag, container, false);
        walkingtimer = (TextView)rootView.findViewById(R.id.walkingtimer);

        mInstance = this;
        mKeySet = new HashSet<HealthPermissionManager.PermissionKey>();
        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ));
        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a HealthDataStore instance and set its listener
        mStore = new HealthDataStore(getContext(), mConnectionListener);
        // Request the connection to the health data store
        mStore.connectService();

        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Show user permission UI for allowing user to change options
            pmsManager.requestPermissions(mKeySet).setResultListener(mPermissionListener);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
            Log.e(APP_TAG, "Permission setting fails.");
        }




        mMapView = new MapView(getActivity());
        mPolyline = new MapPolyline();
        mpolypoint = new double[2][12];

        getPoint("http://condi.swu.ac.kr/schkr/receive_point.php");

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            lat = lastKnownLocation.getLatitude();
            lon = lastKnownLocation.getLongitude();
        }
        else {Toast.makeText(getActivity(),"noooooooooooooo",Toast.LENGTH_LONG).show();}

        mMapView.setDaumMapApiKey("a3df1310d2d475a4cca02b3a521dc8ab" );
        ViewGroup mapViewContainer = (ViewGroup)rootView.findViewById(R.id.map_view);

        mMapView.setMapViewEventListener(new MapView.MapViewEventListener() {
            @Override
            public void onMapViewInitialized(MapView mapView) {

                Log.d("123","initial 들어옴");
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving);
                mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat,lon),true);
                mMapView.setDefaultCurrentLocationMarker();
                mMapView.setShowCurrentLocationMarker(true);

                mMapView.setCurrentLocationEventListener(new MapView.CurrentLocationEventListener() {
                    @Override
                    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
                        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
                        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude), true);

                    }

                    @Override
                    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

                    }

                    @Override
                    public void onCurrentLocationUpdateFailed(MapView mapView) {

                    }

                    @Override
                    public void onCurrentLocationUpdateCancelled(MapView mapView) {

                    }
                });





              //  mMapView.addPolyline(mPolyline);


            }

            @Override
            public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

            }

            @Override
            public void onMapViewZoomLevelChanged(MapView mapView, int i) {

            }

            @Override
            public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

            }

            @Override
            public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

            }

            @Override
            public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

            }

            @Override
            public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

            }

            @Override
            public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

            }

            @Override
            public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

            }
        });

        mapViewContainer.addView(mMapView);

        FeelingDialog mDialog = new FeelingDialog();
        mDialog.show(getFragmentManager(),"MYTAG");





        btn_finish = (Button)rootView.findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTimer.removeMessages(0);
                mPauseTime = SystemClock.elapsedRealtime(); //산책 마치기 버튼 누르면 일시정지...


                FinishDialog mFDialog = new FinishDialog();
                mFDialog.show(getFragmentManager(),"MYTAG");
            }
        });


        return rootView;
    }//onCreateView WalkingClass

    public static class FeelingDialog extends DialogFragment  {

        Button btn_pre, btn_next;
        ImageView startfeel_bg;

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
            View view = mLayoutInflater.inflate(R.layout.startfeel_dia,null);
            mBuilder.setView(view);


            final Spinner fspinner = (Spinner)view.findViewById(R.id.feelingspinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.feeling, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fspinner.setPrompt("condition");
            fspinner.setAdapter(adapter);
            fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String str = (String) fspinner.getSelectedItem();
                    startfeel = str;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getActivity(), "값을 선택해", Toast.LENGTH_SHORT).show();


                }
            }); //feelingspinner

            btn_pre = (Button)view.findViewById(R.id.btn_pre);
            btn_pre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String select_condition=null;
                    String select_time=null;

                    onStop();
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
            btn_next = (Button)view.findViewById(R.id.btn_next);
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //다음 다이얼로그
                    onStop();
                    MusicDialog mMusicDialog = new MusicDialog();
                    mMusicDialog.show(getFragmentManager(),"MYTAG");

                }
            });
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
                        return onTouch(view, event);
                    } else {
                        return false;
                    }
                }
            });//이거 안먹음

            return mBuilder.create();
        }

    }//FeelingDialog

    public static class MusicDialog extends DialogFragment {

        Button btn_music, btn_notmusic;

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
            View view = mLayoutInflater.inflate(R.layout.music_dia,null);
            mBuilder.setView(view);

            btn_music = (Button)view.findViewById(R.id.btn_music);

            btn_music.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStop();

                }
            });//음악 링크 열어주기

            btn_notmusic = (Button)view.findViewById(R.id.btn_notmusic);

            btn_notmusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStop();
                    //산책 카운트 시작하면 되지 않을까

                }
            }); // 음악 안듣고 산책


            return mBuilder.create();
        }//onCreateDialog() MusicDia
    }//MusicDialog

    public static class FinishDialog extends DialogFragment {

        Button btn_finish_no, btn_finish_yes;

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
            View view = mLayoutInflater.inflate(R.layout.finish_dia,null);
            mBuilder.setView(view);

            btn_finish_no = (Button)view.findViewById(R.id.btn_finish_no);
            btn_finish_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //계속할래요
                    //타이머 다시 시작 ㄱㄱ
                    long now = SystemClock.elapsedRealtime();
                    mBaseTime = mBaseTime + (now - mPauseTime);
                    mTimer.sendEmptyMessage(0);

                    onStop();
                }
            });

            btn_finish_yes = (Button)view.findViewById(R.id.btn_finish_yes);
            btn_finish_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 마칠래요 선택시

                   //산책 멈추고.....
                    mTimer.removeMessages(0);

                    //번들 예시로 암거나 일단 넣는다
                    wktime = "aaa";
                    wklength = "bbb";
                    wkcount = "ccc";
                    calorie = "ddd";

                    EditDiaryFrag frag = new EditDiaryFrag();

                    Bundle args = new Bundle();
                    args.putString(ARG_PARAM1,startfeel);
                    args.putString(ARG_PARAM2,wktime);
                    args.putString(ARG_PARAM3,wklength);
                    args.putString(ARG_PARAM4,wkcount);
                    args.putString(ARG_PARAM5,calorie);

                    frag.setArguments(args);


                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.commit();

                    onStop();

                }
            });

            return mBuilder.create();
        }//onCreateDialog Finish
    }

    protected void setPoints() {
        Log.d("setPoint", "들어옴");
        try {
            Log.d("123", "진입성공");
            JSONObject jsonObj = new JSONObject(myJSON);
            schkr = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < schkr.length(); i++) {
                JSONObject c = schkr.getJSONObject(i);

                int j = c.getInt(db_id);
                int b = c.getInt(db_num);
                double x = c.getDouble(db_xpoint);
                double y = c.getDouble(db_ypoint);

                mpolypoint[0][i] = x;
                String xx = String.valueOf(x);
                Log.e("xx", xx);
                mpolypoint[1][i] = y;
                String yy = String.valueOf(y);
                Log.e("yy",yy);

                mPolyline.addPoint(MapPoint.mapPointWithGeoCoord(mpolypoint[0][i],mpolypoint[1][i]));

            }

            mMapView.addPolyline(mPolyline);
            mPolyline.setLineColor(Color.BLUE);
            mPolyline.setTag(1000);

            MapPointBounds mapPointBounds = new MapPointBounds(mPolyline.getMapPoints());
            int padding = 100; // px
            mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_actionbar);
            TextView txtTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);
            txtTitle.setText("북한산 마실 둘레길");

            mBaseTime = SystemClock.elapsedRealtime();
            mTimer.sendEmptyMessage(0);

            //아 산책로 네임 받아야댐


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getPoint(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                }catch(Exception e){
                    return null;
                }
            }
            @Override
            public void onPostExecute(String result){
                myJSON=result;
                setPoints();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    //타이머 재기 위한 핸들러
    static Handler mTimer = new Handler(){

        public void handleMessage(android.os.Message msg){

            walkingtimer.setText(getEllapse());
            mTimer.sendEmptyMessage(0);

        }
    };

    static String getEllapse(){
        long now = SystemClock.elapsedRealtime();
        long ell = now - mBaseTime; //현재시간 - 기준시간

        String sEll = String.format("%02d:%02d:%02d", ell/1000/60/60,ell/1000/60,(ell/1000)%60);
        return sEll;
    }


}