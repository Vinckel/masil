package com.example.android.navigationdrawer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        NotificationCompat.InboxStyle ib = new NotificationCompat.InboxStyle();

        ib.addLine("걸음 수가 부족해요! 지금 산책 갈까요?");
        //ib.addLine("지금 산책 나가보실래요?");

        Log.d("MyTag", String.valueOf(System.currentTimeMillis()));

        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setTicker("마실마실")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("마실마실")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setStyle(ib)
                .setAutoCancel(true)
                .setNumber(1);
        notificationmanager.notify(111, mBuilder.build());

    }
}
