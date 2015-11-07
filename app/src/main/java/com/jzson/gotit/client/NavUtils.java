package com.jzson.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.AnswerListFragment;
import com.jzson.gotit.client.fragments.FeedListFragment;
import com.jzson.gotit.client.fragments.CheckInListFragment;
import com.jzson.gotit.client.fragments.FollowerListFragment;
import com.jzson.gotit.client.fragments.CreateCheckInFragment;
import com.jzson.gotit.client.fragments.NotificationListFragment;
import com.jzson.gotit.client.fragments.AlertSettingsFragment;
import com.jzson.gotit.client.fragments.SettingsFragment;
import com.jzson.gotit.client.fragments.SingleFollowerSettingsFragment;
import com.jzson.gotit.client.fragments.ShareSettingsFragment;
import com.jzson.gotit.client.fragments.TeenListFragment;
import com.jzson.gotit.client.model.Answer;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.provider.InternalProvider;
import com.jzson.gotit.client.provider.ProviderFactory;
import com.jzson.gotit.client.provider.ServiceApi;

import java.util.List;

/**
 * Created by Denis on 10/12/2015.
 */
public class NavUtils {

    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        NavUtils.showTeensActivity(context);
    }

    public static void showTeensActivity(Context context) {
        showFragmentInMainActivity(context, new TeenListFragment());
    }

    public static void showEditFeedback(Context context) {
    }

    public static void showCreateCheckIn(Context context) {

        ServiceApi service = ProviderFactory.getOrShowLogin(context);

        if (service != null) {
            List<Question> questionList = service.getCheckInQuestions();

            AppApplication.getContext().setQuestionList(questionList);

            showFragmentInMainActivity(context, new CreateCheckInFragment());
        }
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

    public static void showAnswerListFragment(Context context, List<Answer> answerList) {
        AppApplication.getContext().setAnswerList(answerList);
        showFragmentInMainActivity(context, new AnswerListFragment());
    }

    public static void showSettings(Context context) {
        showFragmentInMainActivity(context, new SettingsFragment());
    }

    public static void showShareSettings(Context context) {
        showFragmentInMainActivity(context, new ShareSettingsFragment());
    }

    public static void showNotificationSettings(Context context) {
        showFragmentInMainActivity(context, new AlertSettingsFragment());
    }

    public static void showSingleFollowerSettings(Context context, FollowerSettings model) {
        AppApplication.getContext().setFollowerId(model.getFollowerId());
        showFragmentInMainActivity(context, new SingleFollowerSettingsFragment());
    }
}
