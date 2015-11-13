package org.coursera.capstone.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import org.coursera.capstone.gotit.client.activities.MainActivity;
import org.coursera.capstone.gotit.client.fragments.AnswerListFragment;
import org.coursera.capstone.gotit.client.fragments.FeedListFragment;
import org.coursera.capstone.gotit.client.fragments.CheckInListFragment;
import org.coursera.capstone.gotit.client.fragments.FeedbackListFragment;
import org.coursera.capstone.gotit.client.fragments.FollowerListFragment;
import org.coursera.capstone.gotit.client.fragments.CreateCheckInFragment;
import org.coursera.capstone.gotit.client.fragments.NotificationListFragment;
import org.coursera.capstone.gotit.client.fragments.AlertSettingsFragment;
import org.coursera.capstone.gotit.client.fragments.SettingsFragment;
import org.coursera.capstone.gotit.client.fragments.SingleFollowerSettingsFragment;
import org.coursera.capstone.gotit.client.fragments.ShareSettingsFragment;
import org.coursera.capstone.gotit.client.fragments.TeenListFragment;
import org.coursera.capstone.gotit.client.fragments.TeenProfileFragment;
import org.coursera.capstone.gotit.client.model.Answer;
import org.coursera.capstone.gotit.client.model.FollowerSettings;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.model.Question;
import org.coursera.capstone.gotit.client.provider.ProviderFactory;
import org.coursera.capstone.gotit.client.provider.ServiceApi;

import java.util.List;

/**
 * Created by Denis on 10/12/2015.
 */
public class NavUtils {

    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        AppApplication.getContext().setFragment(new TeenListFragment());
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

    public static void showTeenProfile(Context context, Person person) {
        AppApplication.getContext().setPerson(person);
        showFragmentInMainActivity(context, new TeenProfileFragment());
    }

    public static void showFeedbackList(Context context) {
        showFragmentInMainActivity(context, new FeedbackListFragment());
    }
}
