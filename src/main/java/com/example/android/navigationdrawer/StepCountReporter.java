/**
 * Copyright (C) 2014 Samsung Electronics Co., Ltd. All rights reserved.
 *
 * Mobile Communication Division,
 * Digital Media & Communications Business, Samsung Electronics Co., Ltd.
 *
 * This software and its documentation are confidential and proprietary
 * information of Samsung Electronics Co., Ltd.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Samsung Electronics.
 *
 * Samsung Electronics makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 */

package com.example.android.navigationdrawer;

import android.database.Cursor;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataResolver.Filter;
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadRequest;
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadResult;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;

public class StepCountReporter {
    private final HealthDataStore mStore;
    public static final String APP_TAG = "Masil";
    public static final double CALX = 0.03;
    public static long lastCheckedTime = 0L;

    public StepCountReporter(HealthDataStore store) {
        mStore = store;
    }

    public void start() {
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(mStore, HealthConstants.StepCount.HEALTH_DATA_TYPE, mObserver);
        lastCheckedTime = System.currentTimeMillis();
        readTodayStepCount();
    }

    // Read the today's step count on demand
    private void readTodayStepCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // Set time range from start time of today to the current time
        long startTime = lastCheckedTime;
        long endTime = System.currentTimeMillis();
        Filter filter = Filter.and(Filter.greaterThanEquals(HealthConstants.StepCount.START_TIME, startTime),
                                   Filter.lessThanEquals(HealthConstants.StepCount.START_TIME, endTime));

        HealthDataResolver.ReadRequest request = new ReadRequest.Builder()
                                                        .setDataType(HealthConstants.StepCount.HEALTH_DATA_TYPE)
                                                        .setProperties(new String[] {HealthConstants.StepCount.CALORIE,HealthConstants.StepCount.DISTANCE,HealthConstants.StepCount.COUNT})
                                                        .setFilter(filter)
                                                        .build();

        try {
            resolver.read(request).setResultListener(mListener);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
            Log.e(APP_TAG, "Getting step count fails.");
        }
    }

    private long getStartTimeOfToday() {
        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    private final HealthResultHolder.ResultListener<ReadResult> mListener = new HealthResultHolder.ResultListener<ReadResult>() {
        @Override
        public void onResult(ReadResult result) {
            double mdistance = 0; // 미터로 반환 되네......
            double distance = 0;
            double calorie = 0;
            int count = 0 ;
            Cursor c = null;

            try {
                c = result.getResultCursor();
                if (c != null) {
                    while (c.moveToNext()) {
                        calorie += c.getInt(c.getColumnIndex(HealthConstants.StepCount.CALORIE));
                        mdistance += c.getDouble(c.getColumnIndex(HealthConstants.StepCount.DISTANCE));
                        count += c.getInt(c.getColumnIndex(HealthConstants.StepCount.COUNT));

                        Log.d("에스헬스","미터로는 "+mdistance);
                        distance = mdistance/1000;
                        Log.d("에스헬스","이거는 칼로리고 "+String.valueOf(calorie)+"\n이거는 거리인데"+String.format("%.2f", distance));
                    }
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }

            WalkingFrag.getInstance().drawStepNDistance(String.valueOf((int)calorie),String.format("%.2f", distance),String.valueOf(count));
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // Update the step count when a change event is received
        @Override
        public void onChange(String dataTypeName) {
            Log.d(APP_TAG, "Observer receives a data changed event");
            readTodayStepCount();
        }
    };

}
