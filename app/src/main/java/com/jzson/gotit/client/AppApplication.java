package com.jzson.gotit.client;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.Person;

public class AppApplication extends Application {

    private static AppApplication context;
    private Fragment fragment;
    private Person person;
    private Feedback feedback;

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

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Feedback getFeedback() {
        return feedback;
    }
}

