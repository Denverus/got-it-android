package com.jzson.gotit.client.model;

/**
 * Created by dtrotckii on 10/20/2015.
 */
public class UserFeed extends BaseModel {
    private Person person;
    private CheckIn checkIn;

    public UserFeed() {
    }

    public UserFeed(Person person, CheckIn checkIn) {
        this.person = person;
        this.checkIn = checkIn;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }
}
