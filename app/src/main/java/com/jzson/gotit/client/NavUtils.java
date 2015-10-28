package com.jzson.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.FeedListFragment;
import com.jzson.gotit.client.fragments.CheckInListFragment;
import com.jzson.gotit.client.fragments.FollowerListFragment;
import com.jzson.gotit.client.fragments.NewCheckInFragment;
import com.jzson.gotit.client.fragments.NotificationListFragment;
import com.jzson.gotit.client.fragments.TeenListFragment;
import com.jzson.gotit.client.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 10/12/2015.
 */
public class NavUtils {
    public static void showTeensActivity(Context context) {
        showFragmentInMainActivity(context, new TeenListFragment());
    }

    public static void showEditFeedback(Context context) {
    }

    public static void showEditCheckIn(Context context) {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question("Question1", "", 0));
        questionList.add(new Question("Question2", "", 0));
        questionList.add(new Question("Question3", "", 0));

        AppApplication.getContext().setQuestionList(questionList);

        showFragmentInMainActivity(context, new NewCheckInFragment());
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
        MainActivity mainActivity = (MainActivity)context;
        //Intent intent = new Intent(context, MainActivity.class);
        //context.startActivity(intent);
        mainActivity.setFragment(fragment);
    }

    public static void showNotificationList(Context context) {
        showFragmentInMainActivity(context, new NotificationListFragment());
    }
}
