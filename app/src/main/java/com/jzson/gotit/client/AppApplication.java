package com.jzson.gotit.client;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.jzson.gotit.client.model.Answer;
import com.jzson.gotit.client.model.CheckIn;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.Question;

import java.util.List;

public class AppApplication extends Application {

    private static AppApplication context;
    private Fragment fragment;
    private Person person;
    private Person currentPerson;
    private Integer checkIn = null;
    private List<Question> questionList;
    private int followerId;
    private List<Answer> answerList;

    @Override
    public void onCreate() {
        super.onCreate();

        AppApplication.context = this;
    }

    public static AppApplication getContext() {
        return (AppApplication) AppApplication.context;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCheckIn(Integer checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getCheckIn() {
        return checkIn;
    }

    public int getCurrentUserId() {
        return currentPerson.getId();
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setCurrentUser(Person person) {
        currentPerson = person;
    }

    public Person getCurrentUser() {
        return currentPerson;
    }
}

