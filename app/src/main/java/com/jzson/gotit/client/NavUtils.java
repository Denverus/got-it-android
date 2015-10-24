package com.jzson.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.activities.TeenListActivity;
import com.jzson.gotit.client.fragments.EditFeedbackFragment;
import com.jzson.gotit.client.fragments.FeedbackFeedsFragment;
import com.jzson.gotit.client.fragments.FeedbackListFragment;
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

    public static void showFeedbackList(Context context) {
        showFragmentInMainActivity(context, new FeedbackListFragment());
    }

    public static void showFeedsList(Context context) {
        showFragmentInMainActivity(context, new FeedbackFeedsFragment());
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
