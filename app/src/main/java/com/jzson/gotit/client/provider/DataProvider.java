package com.jzson.gotit.client.provider;

import android.media.Image;

import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.model.Answer;
import com.jzson.gotit.client.model.CheckIn;
import com.jzson.gotit.client.model.DataItemSettings;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.model.ShareSettings;
import com.jzson.gotit.client.model.Subscription;
import com.jzson.gotit.client.model.UserFeed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class DataProvider {
    private static final DataProvider INSTANCE = new DataProvider();

    public static int FOLLOWER_STATUS_NOT_FOLLOWED = 0;
    public static int FOLLOWER_STATUS_REQUESTED = 1;
    public static int FOLLOWER_STATUS_FOLLOWED = 2;


    private PersonTable personTable = new PersonTable();

    private FeedbackTable feedbackTable = new FeedbackTable();

    private Table<Question> questionTable = new Table<>();

    private Table<Answer> answerTable = new Table<>();

    private Table<CheckIn> checkInTable = new Table<>();

    private Table<Notification> notifications = new Table<>();

    private SubscriptionTable subscriptions = new SubscriptionTable();

    private Table<ShareSettings> shareSettingsTable = new Table<>();

    public DataProvider() {
        initializeData();
    }

    private void initializeData() {
        personTable.add(new Person("Emma Wilson", "user1", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Lavery Maiss", "user2", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Lillie Watts", "user3", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Michel Rodrigez", "user4", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Caren Wilosn", "user5", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Mike Waters", "user6", "", Utils.getRandomBirthDate(), false, null, null));

        questionTable.add(new Question("What was your blood sugar level at meal time?", "Blood sugar level", Answer.TYPE_INT));
        questionTable.add(new Question("What did you eat at meal time?", "Meal", Answer.TYPE_STRING));
        questionTable.add(new Question("Did you administer insulin?", "Insulin administer", Answer.TYPE_BOOLEAN));


        /*feedbackTable.add(new CheckIn(0, createQuestions(10d, "Meat", false)));
        feedbackTable.add(new CheckIn(1, createQuestions(5d, "Bread", true)));
        feedbackTable.add(new CheckIn(2, createQuestions(3d, "Soup", false)));
        feedbackTable.add(new CheckIn(3, createQuestions(12d, "Sandwich", true)));
        feedbackTable.add(new CheckIn(4, createQuestions(2d, "Burger", true)));*/

        /*notifications.add(new Notification(Notification.SUBSCRIBE_REQUESTED, 5, 0));
        notifications.add(new Notification(Notification.SUBSCRIBE_REQUESTED, 5, 1));
        notifications.add(new Notification(Notification.SUBSCRIBE_ACCEPTED, 2, 5));
        notifications.add(new Notification(Notification.SUBSCRIBE_ACCEPTED, 3, 5));
        notifications.add(new Notification(Notification.SUBSCRIBE_REJECTED, 4, 5));*/

        //subscriptions.add(new Subscription(5, 2));
        //subscriptions.add(new Subscription(5, 3));
    }

    public static DataProvider getInstance() {
        return INSTANCE;
    }

    public Person getPersonById(int id) {
        return personTable.getById(id);
    }

    public List<Person> getPersons() {
        return personTable.getList();
    }

    public List<Person> getTeens() {
        return personTable.getTeens();
    }

    public List<CheckIn> getFeedback() {
        return feedbackTable.getList();
    }

    public List<UserFeed> getUserFeeds(int personId) {
        List<UserFeed> userFeeds = new ArrayList<>();

        List<Subscription> list = subscriptions.getSubscriptionByPersonId(personId);
        for (Subscription subscription : list) {
            final int teenId = subscription.getSubscribedTo();
            Person person = personTable.getById(teenId);
            List<CheckIn> checkInList = checkInTable.getListByCriteria(new Table.BooleanCriteria<CheckIn>() {
                @Override
                public boolean getCriteriaValue(CheckIn value) {
                    return value.getPersonId() == teenId;
                }
            });
            for (CheckIn checkIn : checkInList) {
                UserFeed userFeed = new UserFeed(person, checkIn);
                userFeeds.add(userFeed);
            }
        }

        return userFeeds;
    }

    public List<Notification> getNotifications() {
        return notifications.getList();
    }


    public List<Notification> getNotificationsByUserId(final int id) {
        return notifications.getListByCriteria(new Table.BooleanCriteria<Notification>() {
            @Override
            public boolean getCriteriaValue(Notification value) {
                return value.getToPersonId() == id;
            }
        });
    }

    public List<CheckIn> getCheckInListByUserId(final int userId) {
        List<CheckIn> checkInList = checkInTable.getListByCriteria(new Table.BooleanCriteria<CheckIn>() {
            @Override
            public boolean getCriteriaValue(CheckIn value) {
                return value.getPersonId() == userId;
            }
        });
        for (CheckIn checkIn : checkInList) {
            final int checkInId = checkIn.getId();
            List<Answer> answerList = answerTable.getListByCriteria(new Table.BooleanCriteria<Answer>() {
                @Override
                public boolean getCriteriaValue(Answer value) {
                    return value.getCheckInId() == checkInId;
                }
            });
            checkIn.setAnswers(answerList);
        }
        return checkInList;
    }

    public void addFeedback(CheckIn checkIn) {
        feedbackTable.add(checkIn);
    }

    public void sendSubscribeRequest(int teenId, int followerId) {
        notifications.add(new Notification(Notification.SUBSCRIBE_REQUESTED, followerId, teenId));
    }

    public void acceptSubscribeRequest(int notificationId) {
        Notification notification = notifications.getById(notificationId);
        if (notification.getCode() == Notification.SUBSCRIBE_REQUESTED) {
            Subscription subscription = new Subscription(notification.getFromPersonId(), notification.getToPersonId());
            subscriptions.add(subscription);

            Notification followerNotification = new Notification(Notification.SUBSCRIBE_ACCEPTED, notification.getToPersonId(), notification.getFromPersonId());
            notifications.add(followerNotification);
        }
        notifications.delete(notificationId);
    }

    public void rejectSubscribeRequest(int notificationId) {
        Notification notification = notifications.getById(notificationId);
        if (notification.getCode() == Notification.SUBSCRIBE_REQUESTED) {
            Subscription subscription = new Subscription(notification.getFromPersonId(), notification.getToPersonId());
            subscriptions.add(subscription);

            Notification followerNotification = new Notification(Notification.SUBSCRIBE_REJECTED, notification.getToPersonId(), notification.getFromPersonId());
            notifications.add(followerNotification);
        }
        notifications.delete(notificationId);
    }

    public int checkFollowerStatus(final int followerId, final int teenId) {
        List<Subscription> subscriptionList = subscriptions.getListByCriteria(new Table.BooleanCriteria<Subscription>() {
            @Override
            public boolean getCriteriaValue(Subscription value) {
                return value.getPersonId() == followerId && value.getSubscribedTo() == teenId;
            }
        });

        if (!subscriptionList.isEmpty()) {
            return DataProvider.FOLLOWER_STATUS_FOLLOWED;
        } else {
            List<Notification> notificationList = notifications.getListByCriteria(new Table.BooleanCriteria<Notification>() {
                @Override
                public boolean getCriteriaValue(Notification value) {
                    return value.getFromPersonId() == followerId && value.getToPersonId() == teenId && value.getCode() == Notification.SUBSCRIBE_REQUESTED;
                }
            });
            if (notificationList.isEmpty()) {
                return DataProvider.FOLLOWER_STATUS_NOT_FOLLOWED;
            } else {
                return DataProvider.FOLLOWER_STATUS_REQUESTED;
            }
        }
    }

    public List<Person> getFollowerList(final int userId) {
        List<Subscription> subscriptionList = subscriptions.getListByCriteria(new Table.BooleanCriteria<Subscription>() {
            @Override
            public boolean getCriteriaValue(Subscription value) {
                return value.getSubscribedTo() == userId;
            }
        });
        List<Person> persons = new ArrayList<>(subscriptionList.size());
        for (Subscription subscription : subscriptionList) {
            Person person = personTable.getById(subscription.getPersonId());
            persons.add(person);
        }
        return persons;
    }

    public void cancelSubscription(final int teenId, final int followerId) {
        List<Subscription> subscriptions = this.subscriptions.getListByCriteria(new Table.BooleanCriteria<Subscription>() {
            @Override
            public boolean getCriteriaValue(Subscription value) {
                return value.getPersonId() == followerId && value.getSubscribedTo() == teenId;
            }
        });
        this.subscriptions.deleteAll(subscriptions);
        Notification notification = new Notification(Notification.SUBSCRIBTION_CANCELED, teenId, followerId);
        notifications.add(notification);
    }

    public void deleteNotification(int id) {
        notifications.delete(id);
    }

    public void registerUser(String fullName, Date dateBirth, String login, String password, boolean hasDiabetes, String medicalRecordNumber, Image photo) {
        Person person = new Person(fullName, login, password, dateBirth, hasDiabetes, medicalRecordNumber, photo);
        personTable.add(person);
    }

    public List<Answer> getUserAnswerList(final int checkIn) {
        List<Answer> answers = answerTable.getListByCriteria(new Table.BooleanCriteria<Answer>() {
            @Override
            public boolean getCriteriaValue(Answer value) {
                return value.getCheckInId() == checkIn;
            }
        });

        for (Answer answer : answers) {
            Question question = questionTable.getById(answer.getQuestionId());
            answer.setQuestion(question.getQuestion());
        }

        return answers;
    }

    public void saveAnswer(int userId, List<Answer> answerList) {
        CheckIn checkIn = new CheckIn();
        checkIn.setPersonId(userId);
        int checkInId = checkInTable.add(checkIn);

        for (Answer answer : answerList) {
            answer.setCheckInId(checkInId);
            answerTable.add(answer);
        }
    }

    public List<Question> getCheckInQuestions() {
        return questionTable.getList();
    }

    public List<FollowerSettings> loadFollowerSettings(final int userId) {
        List<FollowerSettings> followerSettingList = new ArrayList<>();

        List<Person> list = personTable.getList();
        for (final Person person : list) {

            List<ShareSettings> userSharingSettings = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                @Override
                public boolean getCriteriaValue(ShareSettings value) {
                    return value.getUserId() == userId && value.getFollowerId() == person.getId();
                }
            });

            FollowerSettings followerSettings = new FollowerSettings();
            followerSettings.setName(person.getName());
            followerSettings.setFollowerId(person.getId());

            if (userSharingSettings.size() >= questionTable.getList().size()) {
                followerSettings.setEnableSharing(false);
            } else {
                followerSettings.setEnableSharing(true);
            }

            followerSettingList.add(followerSettings);
        }

        return followerSettingList;
    }

    public void enableFollowerSharing(final int userId, final int followerId, boolean doShare) {
        if (doShare) {
            List<ShareSettings> shareList = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                @Override
                public boolean getCriteriaValue(ShareSettings value) {
                    return value.getUserId() == userId && value.getFollowerId() == followerId;
                }
            });
            shareSettingsTable.deleteAll(shareList);
        } else {
            List<Question> questions = questionTable.getList();
            for (Question question : questions) {
                ShareSettings shareSettings = new ShareSettings();
                shareSettings.setFollowerId(followerId);
                shareSettings.setUserId(userId);
                shareSettings.setAllowedQuestionId(question.getId());
                shareSettingsTable.add(shareSettings);
            }
        }
    }

    public List<DataItemSettings> loadSingleFollowerSettings(final int userId, final int followerId) {
        List<DataItemSettings> resultsList = new ArrayList<>();

        for (final Question question : questionTable.getList()) {
            List<ShareSettings> list = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                @Override
                public boolean getCriteriaValue(ShareSettings value) {
                    return value.getUserId() == userId && value.getFollowerId() == followerId && value.getAllowedQuestionId()==question.getId();
                }
            });

            DataItemSettings followerSettings = new DataItemSettings();
            followerSettings.setData(question.getShortName());
            followerSettings.setQuestionId(followerSettings.getQuestionId());
            followerSettings.setEnableShare(list.isEmpty());
            resultsList.add(followerSettings);
        }

        return resultsList;
    }

    public void saveSingleFollowerSettings(final int userId, final int followerId, final int questionId, final boolean doShare) {
        if (doShare) {
            List<ShareSettings> shareList = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                @Override
                public boolean getCriteriaValue(ShareSettings value) {
                    return value.getUserId() == userId && value.getFollowerId() == followerId && value.getAllowedQuestionId()==questionId;
                }
            });
            shareSettingsTable.deleteAll(shareList);
        } else {
            ShareSettings shareSettings = new ShareSettings();
            shareSettings.setFollowerId(followerId);
            shareSettings.setUserId(userId);
            shareSettings.setAllowedQuestionId(questionId);
            shareSettingsTable.add(shareSettings);
        }
    }
}
