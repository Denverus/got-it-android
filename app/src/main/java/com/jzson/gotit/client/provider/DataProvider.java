package com.jzson.gotit.client.provider;

import android.hardware.usb.UsbRequest;

import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.model.Subscription;
import com.jzson.gotit.client.model.UserFeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Denis on 10/11/2015.
 */
public class DataProvider {
    private static final DataProvider INSTANCE = new DataProvider();

    private PersonTable persons = new PersonTable();

    private FeedbackTable feedback = new FeedbackTable();

    private Table<Question> questions = new Table<>();

    private Table<Notification> notifications = new Table<>();

    private SubscriptionTable subscriptions = new SubscriptionTable();

    public DataProvider() {
        initializeData();
    }

    private void initializeData(){
        persons.add(new Person("Emma Wilson", "13 years old", Person.TEEN));
        persons.add(new Person("Lavery Maiss", "15 years old", Person.TEEN));
        persons.add(new Person("Lillie Watts", "16 years old", Person.TEEN));
        persons.add(new Person("Michel Rodrigez", "17 years old", Person.TEEN));
        persons.add(new Person("Caren Wilosn", "12 years old", Person.TEEN));
        persons.add(new Person("Mike Waters", "45 years old", Person.FOLLOWER));

        feedback.add(new Feedback(0, createQuestions(10d, "Meat", false)));
        feedback.add(new Feedback(1, createQuestions(5d, "Bread", true)));
        feedback.add(new Feedback(2, createQuestions(3d, "Soup", false)));
        feedback.add(new Feedback(3, createQuestions(12d, "Sandwich", true)));
        feedback.add(new Feedback(4, createQuestions(2d, "Burger", true)));

        notifications.add(new Notification(Notification.SUBSCRIBE_REQUESTED, 5, 0));
        notifications.add(new Notification(Notification.SUBSCRIBE_REQUESTED, 5, 1));
        notifications.add(new Notification(Notification.SUBSCRIBE_ACCEPTED, 2, 5));
        notifications.add(new Notification(Notification.SUBSCRIBE_ACCEPTED, 3, 5));
        notifications.add(new Notification(Notification.SUBSCRIBE_REJECTED, 4, 5));

        subscriptions.add(new Subscription(5, 2));
        subscriptions.add(new Subscription(5, 3));
    }

    public static DataProvider getInstance() {
        return INSTANCE;
    }

    public Person getPersonById(int id) {
        return persons.getById(id);
    }

    public List<Person> getPersons() {
        return persons.getList();
    }

    public List<Person> getTeens() {
        return persons.getTeens();
    }

    public List<Feedback> getFeedback() {
        return feedback.getList();
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
            Person person = persons.getById(teenId);
            List<Feedback> feedbackList = feedback.getFeedbackListByUserId(teenId);
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
}
