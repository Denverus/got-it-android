package org.coursera.capstone.gotit.client.provider;

import android.media.Image;
import android.widget.CheckBox;

import org.coursera.capstone.gotit.client.Utils;
import org.coursera.capstone.gotit.client.model.Answer;
import org.coursera.capstone.gotit.client.model.CheckIn;
import org.coursera.capstone.gotit.client.model.DataItemSettings;
import org.coursera.capstone.gotit.client.model.Feedback;
import org.coursera.capstone.gotit.client.model.FollowerSettings;
import org.coursera.capstone.gotit.client.model.GeneralSettings;
import org.coursera.capstone.gotit.client.model.GraphData;
import org.coursera.capstone.gotit.client.model.Notification;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.model.Question;
import org.coursera.capstone.gotit.client.model.ShareSettings;
import org.coursera.capstone.gotit.client.model.Subscription;
import org.coursera.capstone.gotit.client.model.UserFeed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit.http.Path;

public class InternalProvider implements ServiceApi {

    private static final String[] MEALS = {"sandwich, sugar-free tea", "burger, salad, diet cola", "steak, french fries"};

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
        personTable.add(new Person("Michael Rodrigez", "user4", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Caren Wilson", "user5", "", Utils.getRandomBirthDate(), true, "11232", null));
        personTable.add(new Person("Mike Waters", "user6", "", Utils.getRandomBirthDate(), false, null, null));

        questionTable.add(new Question("What was your blood sugar level at meal time?", "Blood sugar level", Answer.TYPE_INT));
        questionTable.add(new Question("What did you eat at meal time?", "Meal", Answer.TYPE_STRING));
        questionTable.add(new Question("Did you administer insulin?", "Insulin administer", Answer.TYPE_BOOLEAN));

        sendSubscribeRequest(1, 0);
        acceptSubscribeRequest(0);

        sendSubscribeRequest(2, 0);
        acceptSubscribeRequest(2);

        sendSubscribeRequest(3, 0);
        acceptSubscribeRequest(3);

        for (Person person : personTable.getTeens()) {
            for (int i = 0; i < 10; i++) {
                createTestCheckInData(person.getId());
            }
        }


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

    private void createTestCheckInData(int personId) {
        List<Answer> answers = new ArrayList<>();

        Random rnd = new Random();

        answers.add(new Answer(0, rnd.nextInt(200)));
        answers.add(new Answer(1, MEALS[rnd.nextInt(MEALS.length)]));
        answers.add(new Answer(2, rnd.nextBoolean()));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 10, rnd.nextInt(29)+1, rnd.nextInt(23), rnd.nextInt(60));

        saveAnswer(calendar.getTime().getTime(), personId, answers);
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

            if (enableSharing.isEmpty() || !Boolean.parseBoolean(enableSharing.get(0).getValue())) {
                // Teen doesn't allow sharing his info
                continue;
            }

            List<CheckIn> checkInList = checkInTable.getListByCriteria(new Table.BooleanCriteria<CheckIn>() {
                @Override
                public boolean getCriteriaValue(CheckIn value) {
                    return value.getPersonId() == teenId;
                }
            });
            for (final CheckIn checkIn : checkInList) {
                List<ShareSettings> shareSettings = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                    @Override
                    public boolean getCriteriaValue(ShareSettings value) {
                        return value.getUserId() == teen.getId() && value.getFollowerId() == followerId;
                    }
                });

                if (shareSettings.isEmpty()) {
                    List<Answer> answerList = answerTable.getListByCriteria(new Table.BooleanCriteria<Answer>() {
                        @Override
                        public boolean getCriteriaValue(Answer value) {
                            return value.getCheckInId() == checkIn.getId();
                        }
                    });

                    updateAnswersByQuestions(answerList);

                    checkIn.setAnswers(answerList.toArray(new Answer[answerList.size()]));

                    UserFeed userFeed = new UserFeed(teen, checkIn);
                    userFeeds.add(userFeed);
                } else {
                    List<Answer> allowedAnswers = new ArrayList<>();
                    List<Answer> answers = Arrays.asList(checkIn.getAnswers());

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

                    CheckIn sharedCheckIn = new CheckIn(checkIn.getCreated());
                    sharedCheckIn.setId(checkIn.getId());
                    sharedCheckIn.setPersonId(checkIn.getPersonId());
                    sharedCheckIn.setCreated(checkIn.getCreated());
                    sharedCheckIn.setAnswers(allowedAnswers.toArray(new Answer[allowedAnswers.size()]));

                    UserFeed userFeed = new UserFeed(teen, sharedCheckIn);
                    userFeeds.add(userFeed);
                }
            }
        }

        Collections.sort(userFeeds, new Comparator<UserFeed>() {
            @Override
            public int compare(UserFeed lhs, UserFeed rhs) {
                if (lhs.getCheckIn().getCreated() > rhs.getCheckIn().getCreated()) {
                    return 1;
                } else if (lhs.getCheckIn().getCreated() < rhs.getCheckIn().getCreated()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

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

            for (Answer answer : answerList) {
                Question question = questionTable.getById(answer.getQuestionId());
                answer.setQuestion(question.getQuestion());
            }

            checkIn.setAnswers(answerList.toArray(new Answer[answerList.size()]));
        }
        return checkInList;
    }

    public void addFeedback(CheckIn checkIn) {
        feedbackTable.add(checkIn);
    }

    public Integer sendSubscribeRequest(int teenId, int followerId) {
        return notifications.add(new Notification(Notification.SUBSCRIBE_REQUESTED, followerId, teenId));
    }

    public Boolean acceptSubscribeRequest(int notificationId) {
        Notification notification = notifications.getById(notificationId);
        if (notification.getCode() == Notification.SUBSCRIBE_REQUESTED) {
            Subscription subscription = new Subscription(notification.getFromPersonId(), notification.getToPersonId());
            subscriptions.add(subscription);

            Notification followerNotification = new Notification(Notification.SUBSCRIBE_ACCEPTED, notification.getToPersonId(), notification.getFromPersonId());
            notifications.add(followerNotification);
        } else {
            return false;
        }
        return notifications.delete(notificationId) != null;
    }

    public Boolean rejectSubscribeRequest(int notificationId) {
        Notification notification = notifications.getById(notificationId);
        if (notification.getCode() == Notification.SUBSCRIBE_REQUESTED) {
            Subscription subscription = new Subscription(notification.getFromPersonId(), notification.getToPersonId());
            subscriptions.add(subscription);

            // Allow sharing to person in settings
            enableFollowerSharing(notification.getToPersonId(), notification.getFromPersonId(), true);

            Notification followerNotification = new Notification(Notification.SUBSCRIBE_REJECTED, notification.getToPersonId(), notification.getFromPersonId());
            notifications.add(followerNotification);
        } else {
            return false;
        }
        return notifications.delete(notificationId) != null;
    }

    public Integer checkFollowerStatus(final int followerId, final int teenId) {
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

    public Boolean cancelSubscription(final int teenId, final int followerId) {
        List<Subscription> subscriptions = this.subscriptions.getListByCriteria(new Table.BooleanCriteria<Subscription>() {
            @Override
            public boolean getCriteriaValue(Subscription value) {
                return value.getPersonId() == followerId && value.getSubscribedTo() == teenId;
            }
        });
        this.subscriptions.deleteAll(subscriptions);
        Notification notification = new Notification(Notification.SUBSCRIBTION_CANCELED, teenId, followerId);
        notifications.add(notification);
        return true;
    }

    public Notification deleteNotification(int id) {
        return notifications.delete(id);
    }

    public Integer registerUser(Person person) {
        return personTable.add(person);
    }

    public Integer saveAnswer(Long date, int userId, List<Answer> answerList) {
        CheckIn checkIn = new CheckIn(date);
        checkIn.setPersonId(userId);
        int checkInId = checkInTable.add(checkIn);

        for (Answer answer : answerList) {
            answer.setCheckInId(checkInId);
            answerTable.add(answer);
        }
        return checkInId;
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

    public Boolean enableFollowerSharing(final int userId, final int followerId, boolean doShare) {
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

        return true;
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

    public Boolean saveSingleFollowerSettings(final int userId, final int followerId, final int questionId, final boolean doShare) {
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
        return true;
    }

    public Boolean isSharingEnabled(final int userId) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey() == GeneralSettings.ENABLE_SHARING;
            }
        });

        return list.isEmpty() ? false : Boolean.parseBoolean(list.get(0).getValue());
    }

    public Boolean setSharingEnabled(final int userId, final boolean enableSharing) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey() == GeneralSettings.ENABLE_SHARING;
            }
        });

        if (list.isEmpty()) {
            return false;
        }

        list.get(0).setValue(Boolean.toString(enableSharing));
        return true;
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

    @Override
    public List<GeneralSettings> loadAlertsSettings(final int userId, String alert1, String alert2, String alert3) {
        final String[] settingsKeyList = new String[] {alert1, alert2, alert3};
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

    public Boolean saveGeneralSettings(final int userId, final String settingsKey, String settingsValue) {
        List<GeneralSettings> list = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
            @Override
            public boolean getCriteriaValue(GeneralSettings value) {
                return value.getUserId() == userId && value.getKey().equals(settingsKey);
            }
        });

        if (list.isEmpty()) {
            return false;
        }

        list.get(0).setValue(settingsValue);
        return false;
    }

    private List<CheckIn> getSecuredCheckIns(final int followerId, int personId) {
        List<CheckIn> resultCheckIn = new ArrayList<>();

        List<Subscription> list = subscriptions.getSubscriptionByPersonId(followerId);
        for (Subscription subscription : list) {
            final int teenId = subscription.getSubscribedTo();

            if (teenId != personId) {
                continue;
            }

            final Person teen = personTable.getById(teenId);

            // Check sharing settings
            List<GeneralSettings> enableSharing = generalSettingsTable.getListByCriteria(new Table.BooleanCriteria<GeneralSettings>() {
                @Override
                public boolean getCriteriaValue(GeneralSettings value) {
                    return value.getUserId() == teenId && value.getKey() == GeneralSettings.ENABLE_SHARING;
                }
            });

            if (enableSharing.isEmpty() || !Boolean.parseBoolean(enableSharing.get(0).getValue())) {
                // Teen doesn't allow sharing his info
                continue;
            }

            List<CheckIn> checkInList = checkInTable.getListByCriteria(new Table.BooleanCriteria<CheckIn>() {
                @Override
                public boolean getCriteriaValue(CheckIn value) {
                    return value.getPersonId() == teenId;
                }
            });
            for (final CheckIn checkIn : checkInList) {
                List<ShareSettings> shareSettings = shareSettingsTable.getListByCriteria(new Table.BooleanCriteria<ShareSettings>() {
                    @Override
                    public boolean getCriteriaValue(ShareSettings value) {
                        return value.getUserId() == teen.getId() && value.getFollowerId() == followerId;
                    }
                });

                CheckIn sharedCheckIn = null;
                if (shareSettings.isEmpty()) {
                    List<Answer> answerList = answerTable.getListByCriteria(new Table.BooleanCriteria<Answer>() {
                        @Override
                        public boolean getCriteriaValue(Answer value) {
                            return value.getCheckInId() == checkIn.getId();
                        }
                    });

                    updateAnswersByQuestions(answerList);

                    checkIn.setAnswers(answerList.toArray(new Answer[answerList.size()]));

                    sharedCheckIn = checkIn;
                } else {
                    List<Answer> allowedAnswers = new ArrayList<>();
                    List<Answer> answers = Arrays.asList(checkIn.getAnswers());

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

                    sharedCheckIn = new CheckIn(checkIn.getCreated());
                    sharedCheckIn.setId(checkIn.getId());
                    sharedCheckIn.setPersonId(checkIn.getPersonId());
                    sharedCheckIn.setCreated(checkIn.getCreated());
                    sharedCheckIn.setAnswers(allowedAnswers.toArray(new Answer[allowedAnswers.size()]));
                }

                resultCheckIn.add(sharedCheckIn);
            }
        }

        return resultCheckIn;
    }

    @Override
    public List<Feedback> getFeedbackList(int userId) {
        List<Feedback> result = new ArrayList<>();

        for (Person teen : personTable.getTeens()) {
            final List<CheckIn> checkIns = getSecuredCheckIns(userId, teen.getId());
            List<GraphData> graphData = new ArrayList<>();

            for (CheckIn checkIn : checkIns) {
                for (Answer answer : checkIn.getAnswers()) {
                    if (answer.getAnswerType() == Answer.TYPE_INT) {
                        graphData.add(new GraphData(checkIn.getCreated(), answer.getAnswerAsInteger()));
                    }
                }
            }
            if (!graphData.isEmpty()) {
                Collections.sort(graphData, new Comparator<GraphData>() {
                    @Override
                    public int compare(GraphData lhs, GraphData rhs) {
                        if (lhs.getDate() > rhs.getDate()) {
                            return 1;
                        } else if (lhs.getDate() < rhs.getDate()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });

                Feedback feedback = new Feedback();
                feedback.setPerson(teen);
                feedback.setGraphData(graphData);
                result.add(feedback);
            }
        }
        return result;
    }

    private void updateAnswersByQuestions(List<Answer> answers) {
        for (Answer answer : answers) {
            Question question = questionTable.getById(answer.getQuestionId());
            answer.setQuestion(question.getQuestion());
        }
    }
}