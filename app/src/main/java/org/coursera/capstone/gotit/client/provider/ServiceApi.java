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

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ServiceApi {
    public static final int FOLLOWER_STATUS_NOT_FOLLOWED = 0;
    public static final int FOLLOWER_STATUS_REQUESTED = 1;
    public static final int FOLLOWER_STATUS_FOLLOWED = 2;

    public static final String TITLE_PARAMETER = "title";

    public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";

    public static final String PERSON_SVC_PATH = "/person";
    public static final String TEEN_SVC_PATH = "/teens";
    public static final String FOLLOWER_SVC_PATH = "/follower";
    public static final String FEED_SVC_PATH = "/feed";
    public static final String FEEDBACK_SVC_PATH = "/feedback";
    public static final String NOTIFICATION_SVC_PATH = "/notification";
    public static final String CHECKIN_SVC_PATH = "/checkin";
    public static final String REQUEST_SVC_PATH = "/request";
    public static final String SUBSCRIPTION_SVC_PATH = "/subscription";
    public static final String QUESTION_SVC_PATH = "/question";
    public static final String SETTINGS_SVC_PATH = "/settings";

    public static final String LIST_SVC_PATH = "/list";
    public static final String GENERAL_SVC_PATH = "/general";
    public static final String SHARING_SVC_PATH = "/sharing";
    public static final String FOLLOWER_SETTINGS_SVC_PATH = "/followersettings";

    @GET(PERSON_SVC_PATH + "id/{id}")
    public Person getPersonById(@Path("id") int id);

    @GET(PERSON_SVC_PATH + "/username/{name}")
    public Person getPersonByUsername(@Path("name") String username);

    @GET(TEEN_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<Person> getTeens(@Path("id") int userId);

    @GET(FEED_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<UserFeed> getUserFeeds(@Path("id") int followerId);

    @GET(NOTIFICATION_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<Notification> getNotificationsByUserId(@Path("id") int id);

    @GET(CHECKIN_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<CheckIn> getCheckInListByUserId(@Path("id") int userId);

    @POST(REQUEST_SVC_PATH + "/send/{teenid}/{id}")
    public Integer sendSubscribeRequest(@Path("teenid")int teenId, @Path("id") int followerId);

    @POST(REQUEST_SVC_PATH + "/accept/{id}")
    public Boolean acceptSubscribeRequest(@Path("id")int notificationId);

    @POST(REQUEST_SVC_PATH + "/reject/{id}")
    public Boolean rejectSubscribeRequest(@Path("id") int notificationId);

    @GET(FOLLOWER_SVC_PATH + "/status/{id}/{teenid}")
    public Integer checkFollowerStatus(@Path("id") int followerId, @Path("teenid") int teenId);

    @GET(FOLLOWER_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<Person> getFollowerList(@Path("id") int userId);

    @POST(SUBSCRIPTION_SVC_PATH + "/cancel/{teenid}/{id}")
    public Boolean cancelSubscription(@Path("teenid") int teenId, @Path("id") int followerId);

    @DELETE(NOTIFICATION_SVC_PATH + "/{id}")
    public Notification deleteNotification(@Path("id") int id);

    @POST(PERSON_SVC_PATH + "/{person}")
    public Integer registerUser(@Path("person") Person person);

    @POST(CHECKIN_SVC_PATH + "/{date}/{id}")
    public Integer saveAnswer(@Path("date")Long date, @Path("id")int userId, @Body List<Answer> answerList);

    @GET(QUESTION_SVC_PATH + LIST_SVC_PATH)
    public List<Question> getCheckInQuestions();

    @GET(SETTINGS_SVC_PATH + FOLLOWER_SETTINGS_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<FollowerSettings> loadFollowerSettings(@Path("id") int userId);

    @POST(SETTINGS_SVC_PATH + FOLLOWER_SETTINGS_SVC_PATH + "/enable/{teenid}/{id}/{share}")
    public Boolean enableFollowerSharing(@Path("teenid") int userId, @Path("id") int followerId, @Path("share")boolean doShare);

    @GET(SETTINGS_SVC_PATH + FOLLOWER_SETTINGS_SVC_PATH + "/loadsingle/{teenid}/{id}")
    public List<DataItemSettings> loadSingleFollowerSettings(@Path("teenid") int userId, @Path("id") int followerId);

    @POST(SETTINGS_SVC_PATH + FOLLOWER_SETTINGS_SVC_PATH + "/savesingle/{teenid}/{id}/{questionid}")
    public Boolean saveSingleFollowerSettings(@Path("teenid") int userId, @Path("id") int followerId, @Path("questionid") int questionId, @Body boolean doShare);

    @GET(SETTINGS_SVC_PATH + SHARING_SVC_PATH + "/{id}")
    public Boolean isSharingEnabled(@Path("id") int userId);

    @POST(SETTINGS_SVC_PATH + SHARING_SVC_PATH + "/{id}/{enable}")
    public Boolean setSharingEnabled(@Path("id") int userId, @Path("enable") boolean enableSharing);
/*
    @GET(SETTINGS_SVC_PATH + GENERAL_SVC_PATH + "/{id}/{key}")
    public String loadGeneralSettings(@Path("id") int userId, @Path("key") String settingsKey);
*/
    @GET(SETTINGS_SVC_PATH + GENERAL_SVC_PATH + LIST_SVC_PATH + "/{id}/{alert1}/{alert2}/{alert3}")
    public List<GeneralSettings> loadAlertsSettings(@Path("id") int userId, @Path("alert1") String alert1, @Path("alert2") String alert2, @Path("alert3") String alert3);

    @POST(SETTINGS_SVC_PATH + GENERAL_SVC_PATH + "/{id}/{key}/{value}")
    public Boolean saveGeneralSettings(@Path("id") int userId, @Path("key") String settingsKey, @Path("value")String settingsValue);

    @GET(FEEDBACK_SVC_PATH + LIST_SVC_PATH + "/{id}")
    public List<Feedback> getFeedbackList(@Path("id") int userId);
}