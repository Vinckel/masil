package com.example.android.navigationdrawer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastD extends BroadcastReceiver {
    public BroadcastD() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, NavigationDrawerActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(context);

        Log.d("MyTag", String.valueOf(System.currentTimeMillis()));

        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setTicker("마실마실")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("마실마실")
                .setContentText("오늘은 권장 걸음 수에 도달하지 못했어요! 산책으로 권장 걸음 수 채워 보시는 게 어떠세요?")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setNumber(1);
        notificationmanager.notify(111, mBuilder.build());

    }
}
