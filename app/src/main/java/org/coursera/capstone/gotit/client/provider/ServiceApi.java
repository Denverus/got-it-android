package org.coursera.capstone.gotit.client.provider;

import android.media.Image;

import org.coursera.capstone.gotit.client.model.Answer;
import org.coursera.capstone.gotit.client.model.CheckIn;
import org.coursera.capstone.gotit.client.model.DataItemSettings;
import org.coursera.capstone.gotit.client.model.Feedback;
import org.coursera.capstone.gotit.client.model.FollowerSettings;
import org.coursera.capstone.gotit.client.model.GeneralSettings;
import org.coursera.capstone.gotit.client.model.Notification;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.model.Question;
import org.coursera.capstone.gotit.client.model.UserFeed;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface ServiceApi {
    public static final int FOLLOWER_STATUS_NOT_FOLLOWED = 0;
    public static final int FOLLOWER_STATUS_REQUESTED = 1;
    public static final int FOLLOWER_STATUS_FOLLOWED = 2;

    public static final String TITLE_PARAMETER = "title";

    public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";

    public static final String PERSON_SVC_PATH = "/person";
    public static final String TEEN_SVC_PATH = "/teens";
    public static final String FEED_SVC_PATH = "/feed";
    public static final String FEEDBACK_SVC_PATH = "/feedback";

    @GET(PERSON_SVC_PATH + "id/{id}")
    public Person getPersonById(@Path("id") int id);

    @GET(PERSON_SVC_PATH + "username/{name}")
    public Person getPersonByUsername(@Path("name") String username);

    @GET(TEEN_SVC_PATH + "list/{id}")
    public List<Person> getTeens(@Path("id") int userId);

    public List<UserFeed> getUserFeeds(final int followerId);

    public List<Notification> getNotificationsByUserId(final int id);

    public List<CheckIn> getCheckInListByUserId(final int userId);

    public void sendSubscribeRequest(int teenId, int followerId);

    public void acceptSubscribeRequest(int notificationId);

    public void rejectSubscribeRequest(int notificationId);

    public int checkFollowerStatus(final int followerId, final int teenId);

    public List<Person> getFollowerList(final int userId);

    public void cancelSubscription(final int teenId, final int followerId);

    public void deleteNotification(int id);

    public void registerUser(String fullName, Long dateBirth, String login, String password, boolean hasDiabetes, String medicalRecordNumber, Image photo);

    public void saveAnswer(Date date, int userId, List<Answer> answerList);

    public List<Question> getCheckInQuestions();

    public List<FollowerSettings> loadFollowerSettings(final int userId);

    public void enableFollowerSharing(final int userId, final int followerId, boolean doShare);

    public List<DataItemSettings> loadSingleFollowerSettings(final int userId, final int followerId);

    public void saveSingleFollowerSettings(final int userId, final int followerId, final int questionId, final boolean doShare);

    public boolean isSharingEnabled(final int userId);

    public void setSharingEnabled(final int userId, final boolean enableSharing);

    public String loadGeneralSettings(final int userId, final String settingsKey);

    public List<GeneralSettings> loadGeneralSettingsList(final int userId, final String[] settingsKeyList);

    public void saveGeneralSettings(final int userId, final String settingsKey, String settingsValue);

    List<Feedback> getFeedbackList(int userId);
}
