/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class NavigationDrawerActivity extends AppCompatActivity implements MenuAdapter.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mMenuList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private Context mContext;

    static String myPJSON;
    static JSONArray pschkr = null;

    static final int memberid = 1;

    static String myRJSON;
    static JSONArray rschkr = null;

   // private static final String TAG_RESULTS = "result";
    private static final String db_roadid = "roadid";

    ApplicationData appdata;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;
        appdata = (ApplicationData) getApplicationContext();


        new AlarmHATT(getApplicationContext()).Alarm();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        //toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.txt_toolbar);


        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        mMenuList = (RecyclerView) findViewById(R.id.menu_list);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mMenuList.setHasFixedSize(true);

        // set up the drawer's list view with items and click listener
        mMenuList.setAdapter(new MenuAdapter(mPlanetTitles,this));
        // enable ActionBar app icon to behave as ae nav drawer

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
          // getSupportActionBar().setTitle(mTitle);
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (savedInstanceState == null) {

            MasilFragment frag = new MasilFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.addToBackStack(null);
            ft.commit();
        }

        //FirebaseMessaging.getInstance().subscribeToTopic("walk");
        //FirebaseInstanceId.getInstance().getToken();

        String getP = "http://condi.swu.ac.kr/schkr/getBookmark.php?memberid="+memberid;
        appdata.getBookmarkList(getP);

        String getProfile = "http://condi.swu.ac.kr/schkr/getProfile.php?memberid="+memberid;
        getProfile(getProfile);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menu.clear();
        getMenuInflater().inflate(R.menu.menu_home,menu);

        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
       // menu.findItem(R.id.btn_home).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        int id = item.getItemId();

      if(!mDrawerToggle.onOptionsItemSelected(item)){

            switch (id){
                case R.id.menu_home:
                    MasilFragment frag = new MasilFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.addToBackStack(null);
                    ft.commit();
                break;
                default:
                    return super.onOptionsItemSelected(item);

            }

        }
         return super.onOptionsItemSelected(item);
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

                appdata.setMusic(mhappy,mpeace,mboring,msad,mannoying);


            }// end of for()




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getProfile(String url) {

        class GetProfileJSON extends AsyncTask<String, Void, String> {


            @Override
            public void onPreExecute() {

                super.onPreExecute();
                //로딩 넣기

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

            }
        }
        GetProfileJSON g = new GetProfileJSON();
        g.execute(url);
    }

    /* The click listener for RecyclerView in the navigation drawer */
   @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments

        Fragment frag = null;


        switch (position)
        {
            case 0:
                frag = new ProfileFrag();

                break;
            case 1:
                frag = new DiaryListFrag();

                break;
            case 2:
                frag = new RoadListBookmark();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, frag);
        ft.addToBackStack(null);
        ft.commit();

        // update selected item title, then close the drawer
        getSupportActionBar().setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawer);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
        //getActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.maintop));
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;

        }
        public void Alarm() {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(NavigationDrawerActivity.this, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(NavigationDrawerActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 02, 55, 0);//1시 27분

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }


    }



}
