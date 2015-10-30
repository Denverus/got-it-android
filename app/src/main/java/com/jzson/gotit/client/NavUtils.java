package com.jzson.gotit.client;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.AnswerListFragment;
import com.jzson.gotit.client.fragments.FeedListFragment;
import com.jzson.gotit.client.fragments.CheckInListFragment;
import com.jzson.gotit.client.fragments.FollowerListFragment;
import com.jzson.gotit.client.fragments.CreateCheckInFragment;
import com.jzson.gotit.client.fragments.NotificationListFragment;
import com.jzson.gotit.client.fragments.TeenListFragment;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.provider.DataProvider;

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

    public static void showCreateCheckIn(Context context) {
        List<Question> questionList = DataProvider.getInstance().getCheckInQuestions();

        AppApplication.getContext().setQuestionList(questionList);

        showFragmentInMainActivity(context, new CreateCheckInFragment());
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

    public static void showAnswerListFragment(Context context, int checkIn) {
        AppApplication.getContext().setCheckIn(checkIn);
        showFragmentInMainActivity(context, new AnswerListFragment());
    }
}
