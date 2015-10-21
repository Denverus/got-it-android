package com.jzson.gotit.client.model;

/**
 * Created by dtrotckii on 10/20/2015.
 */
public class UserFeed extends BaseModel {
    private Person person;
    private Feedback feedback;

    public UserFeed() {
    }

    public UserFeed(Person person, Feedback feedback) {
        this.person = person;
        this.feedback = feedback;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
