package com.jzson.gotit.client.provider;

import android.media.Image;

import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.model.Answer;
import com.jzson.gotit.client.model.CheckIn;
import com.jzson.gotit.client.model.DataItemSettings;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.model.GeneralSettings;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.model.ShareSettings;
import com.jzson.gotit.client.model.Subscription;
import com.jzson.gotit.client.model.UserFeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class InternalProvider implements ServiceApi {

    private PersonTable personTable = new PersonTable();

    private FeedbackTable feedbackTable = new FeedbackTable();

    private Table<Question> questionTable = new Table<>();

    private Table<Answer> answerTable = new Table<>();

    private Table<CheckIn> checkInTable = new Table<>();

    private Table<Notification> notifications = new Table<>();

    private SubscriptionTable subscriptions = new SubscriptionTable();

    private Table<ShareSettings> shareSettingsTable = new Table<>();

    private Table<GeneralSettings> generalSettingsTable = new Table<>();

    public InternalProvider() {
        initializeData();
    }

    private void initializeData() {
        personTable.addOnAddTrigger(new Table.AddTrigger<Person>() {
            @Override
            public void onAdd(Person model) {
                generalSettingsTable.add(new GeneralSettings(model.getId(), GeneralSettings.ENABLE_SHARING, "true"));
                generalSettingsTable.add(new GeneralSettings(model.getId(), GeneralSettings.ALERT_1, "9:00"));
                generalSettingsTable.add(new GeneralSettings(model.getId(), GeneralSettings.ALERT_2, "12:00"));
                generalSettingsTable.add(new GeneralSettings(model.getId(), GeneralSettings.ALERT_3, "18:00"));
            }
        });

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

    public Person getPersonById(int id) {
        return personTable.getById(id);
    }

    @Override
    public Person getPersonByUsername(final String username) {
        List<Person> persons = personTable.getListByCriteria(new Table.BooleanCriteria<Person>() {
            @Override
            public boolean getCriteriaValue(Person value) {
                return value.getLogin().equals(username);
            }
        });

        return persons.isEmpty() ? null : persons.get(0);
    }

    public List<Person> getTeens(final int userId) {
        return personTable.getListByCriteria(new Table.BooleanCriteria<Person>() {
            @Override
            public boolean getCriteriaValue(Person value) {
                return value.getId() != userId && value.isHasDiabetes();
            }
        });
    }

    public List<CheckIn> getFeedback() {
        return feedbackTable.getList();
    }

    public List<UserFeed> getUserFeeds(final int followerId) {
        List<UserFeed> userFeeds = new ArrayList<>();

        List<Subscription> list = subscriptions.getSubscriptionByPersonId(followerId);
        for (Subscription subscription : list) {
            final int teenId = subscription.getSubscribedTo();
            final Person teen = personTable.getById(teenId);

            // Check sharing settings
            List<GeneralSettings> enableSharing = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
                @Override
                public boolean getCriteriaValue(GeneralSettings value) {
                    return value.getUserId() == teenId && value.getKey() == GeneralSettings.ENABLE_SHARING;
                }
            });

            if (!enableSharing.isEmpty()) {
                // Teen doesn't allow sharing his info
                continue;
            }

            List<CheckIn> checkInList = checkInTable.getListByCriteria(new Table.BooleanCriteria<CheckIn>() {
                @Override
                public boolean getCriteriaValue(CheckIn value) {
                    return value.getPersonId() == teenId;
                }
            });
            for (CheckIn checkIn : checkInList) {
                List<ShareSettings> shareSettings = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                    @Override
                    public boolean getCriteriaValue(ShareSettings value) {
                        return value.getUserId() == teen.getId() && value.getFollowerId() == followerId;
                    }
                });

                if (shareSettings.isEmpty()) {
                    UserFeed userFeed = new UserFeed(teen, checkIn);
                    userFeeds.add(userFeed);
                } else {
                    List<Answer> allowedAnswers = new ArrayList<>();
                    List<Answer> answers = checkIn.getAnswers();

                    for (Answer answer : answers) {
                        boolean allowSharing = true;
                        for (ShareSettings settings : shareSettings) {
                            if (settings.getAllowedQuestionId() == answer.getQuestionId()) {
                                allowSharing = false;
                            }
                        }

                        Question question = questionTable.getById(answer.getQuestionId());
                        answer.setQuestion(question.getQuestion());

                        if (allowSharing) {
                            allowedAnswers.add(answer);
                        }
                    }

                    CheckIn sharedCheckIn = new CheckIn();
                    sharedCheckIn.setId(checkIn.getId());
                    sharedCheckIn.setPersonId(checkIn.getPersonId());
                    sharedCheckIn.setCreated(checkIn.getCreated());
                    sharedCheckIn.setAnswers(allowedAnswers);

                    UserFeed userFeed = new UserFeed(teen, sharedCheckIn);
                    userFeeds.add(userFeed);
                }
            }
        }

        return userFeeds;
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
            return InternalProvider.FOLLOWER_STATUS_FOLLOWED;
        } else {
            List<Notification> notificationList = notifications.getListByCriteria(new Table.BooleanCriteria<Notification>() {
                @Override
                public boolean getCriteriaValue(Notification value) {
                    return value.getFromPersonId() == followerId && value.getToPersonId() == teenId && value.getCode() == Notification.SUBSCRIBE_REQUESTED;
                }
            });
            if (notificationList.isEmpty()) {
                return InternalProvider.FOLLOWER_STATUS_NOT_FOLLOWED;
            } else {
                return InternalProvider.FOLLOWER_STATUS_REQUESTED;
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
                    return value.getUserId() == userId && value.getFollowerId() == person.getId() && value.getFollowerId() != userId;
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
                    return value.getUserId() == userId && value.getFollowerId() == followerId && value.getAllowedQuestionId() == question.getId();
                }
            });

            DataItemSettings followerSettings = new DataItemSettings();
            followerSettings.setData(question.getShortName());
            followerSettings.setQuestionId(question.getId());
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
                    return value.getUserId() == userId && value.getFollowerId() == followerId && value.getAllowedQuestionId() == questionId;
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

    public boolean isSharingEnabled(final int userId) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey() == GeneralSettings.ENABLE_SHARING;
            }
        });

        return list.isEmpty() ? false : Boolean.parseBoolean(list.get(0).getValue());
    }

    public void setSharingEnabled(final int userId, final boolean enableSharing) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey() == GeneralSettings.ENABLE_SHARING;
            }
        });

        list.get(0).setValue(Boolean.toString(enableSharing));
    }

    public String loadGeneralSettings(final int userId, final String settingsKey) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey() == settingsKey;
            }
        });

        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0).getValue();
        }
    }

    public List<GeneralSettings> loadGeneralSettingsList(final int userId, final String[] settingsKeyList) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                for (String key : settingsKeyList) {
                    if (value.getUserId() == userId && value.getKey().equals(key)) {
                        return true;
                    }
                }
                return false;
            }
        });

        Collections.sort(list, new Comparator<GeneralSettings>() {
            @Override
            public int compare(GeneralSettings lhs, GeneralSettings rhs) {
                return lhs.getKey().compareTo(rhs.getKey());
            }
        });

        return list;
    }

    public void saveGeneralSettings(final int userId, final String settingsKey, String settingsValue) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey().equals(settingsKey);
            }
        });

        list.get(0).setValue(settingsValue);
    }
}
