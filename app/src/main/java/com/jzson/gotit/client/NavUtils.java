package com.jzson.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.FeedListFragment;
import com.jzson.gotit.client.fragments.CheckInListFragment;
import com.jzson.gotit.client.fragments.FollowerListFragment;
import com.jzson.gotit.client.fragments.NotificationListFragment;
import com.jzson.gotit.client.fragments.TeenListFragment;

/**
 * Created by Denis on 10/12/2015.
 */
public class NavUtils {
    public static void showTeensActivity(Context context) {
        showFragmentInMainActivity(context, new TeenListFragment());
    }

    public static void showEditFeedback(Context context) {
    }

    public static void showCheckInList(Context context) {
        showFragmentInMainActivity(context, new CheckInListFragment());
    }

    public static void showFeedList(Context context) {
        showFragmentInMainActivity(context, new FeedListFragment());
    }

    public static void showFollowerList(Context context) {
        showFragmentInMainActivity(context, new FollowerListFragment());
    }

    private static void showFragmentInMainActivity(Context context, Fragment fragment) {
        AppApplication.getContext().setFragment(fragment);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showNotificationList(Context context) {
        showFragmentInMainActivity(context, new NotificationListFragment());
    }
}
