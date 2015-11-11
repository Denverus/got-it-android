package org.coursera.capstone.gotit.client.model;

/**
 * Created by Denis on 10/20/2015.
 */
public class Subscription extends BaseModel {

    private int personId;
    private int subscribedTo;

    public Subscription(int id) {
        super(id);
    }

    public Subscription(int personId, int subscribedTo) {
        this.personId = personId;
        this.subscribedTo = subscribedTo;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getSubscribedTo() {
        return subscribedTo;
    }

    public void setSubscribedTo(int subscribedTo) {
        this.subscribedTo = subscribedTo;
    }
}
