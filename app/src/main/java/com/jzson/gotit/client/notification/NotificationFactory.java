package com.jzson.gotit.client.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.jzson.gotit.client.activities.MainActivity;

public class NotificationFactory {

    //https://gist.github.com/BrandonSmith/6679223

    public static void create(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(context)
                .setContentTitle("Got It")
                .setContentText("It's time for daily Check In")
                //.setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
