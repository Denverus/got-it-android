package com.jzson.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.activities.TeenListActivity;
import com.jzson.gotit.client.fragments.EditFeedbackFragment;
import com.jzson.gotit.client.fragments.FeedbackListFragment;

/**
 * Created by Denis on 10/12/2015.
 */
public class NavUtils {
    public static void showTeensActivity(Context context) {
        Intent intent = new Intent(context, TeenListActivity.class);
        context.startActivity(intent);
    }

    public static void showFeedbacksActivity(Context context) {
        //Intent intent = new Intent(context, Feed.class);
        //context.startActivity(intent);
    }

    public static void showEditFeedback(Context context) {
    }

    public static void showFeedbackList(Context context) {
        AppApplication.getContext().setFragment(new FeedbackListFragment());
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
