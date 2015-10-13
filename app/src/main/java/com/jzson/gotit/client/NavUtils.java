package com.jzson.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.activities.TeenListActivity;

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
}
