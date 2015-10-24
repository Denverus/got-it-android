package com.jzson.gotit.client.provider;

import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.model.Subscription;
import com.jzson.gotit.client.model.UserFeed;

import java.util.ArrayList;
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

    private Table<Question> questions = new Table<>();

    private Table<Notification> notifications = new Table<>();

    private SubscriptionTable subscriptions = new SubscriptionTable();

    public DataProvider() {
        initializeData();
    }

    private void initializeData(){
        personTable.add(new Person("Emma Wilson", "13 years old", Person.TEEN));
        personTable.add(new Person("Lavery Maiss", "15 years old", Person.TEEN));
        personTable.add(new Person("Lillie Watts", "16 years old", Person.TEEN));
        personTable.add(new Person("Michel Rodrigez", "17 years old", Person.TEEN));
        personTable.add(new Person("Caren Wilosn", "12 years old", Person.TEEN));
        personTable.add(new Person("Mike Waters", "45 years old", Person.FOLLOWER));

        feedbackTable.add(new Feedback(0, createQuestions(10d, "Meat", false)));
        feedbackTable.add(new Feedback(1, createQuestions(5d, "Bread", true)));
        feedbackTable.add(new Feedback(2, createQuestions(3d, "Soup", false)));
        feedbackTable.add(new Feedback(3, createQuestions(12d, "Sandwich", true)));
        feedbackTable.add(new Feedback(4, createQuestions(2d, "Burger", true)));

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

    public List<Feedback> getFeedback() {
        return feedbackTable.getList();
    }

    private List<Question> createQuestions(Double sugarLevel, String meal, Boolean adminInsulin) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What was your blood sugar level at meal time?", sugarLevel, Question.QUESTION_SUGAR_LEVEL));
        questions.add(new Question("What did you eat at meal time?", meal, Question.QUESTION_MEAL));
        questions.add(new Question("Did you administer insulin?", adminInsulin, Question.QUESTION_INSULIN));
        return questions;
    }

    public List<UserFeed> getUserFeeds(int personId) {
        List<UserFeed> userFeeds = new ArrayList<>();

        List<Subscription> list = subscriptions.getSubscriptionByPersonId(personId);
        for (Subscription subscription : list) {
            int teenId = subscription.getSubscribedTo();
            Person person = personTable.getById(teenId);
            List<Feedback> feedbackList = feedbackTable.getFeedbackListByUserId(teenId);
            for (Feedback feedback: feedbackList) {
                UserFeed userFeed = new UserFeed(person, feedback);
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

    public List<Feedback> getFeedbackById(final int userId) {
        return feedbackTable.getListByCriteria(new Table.BooleanCriteria<Feedback>() {
            @Override
            public boolean getCriteriaValue(Feedback value) {
                return value.getPersonId() == userId;
            }
        });
    }

    public void addFeedback(Feedback feedback) {
        feedbackTable.add(feedback);
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
}
