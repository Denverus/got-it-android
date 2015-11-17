package org.coursera.capstone.gotit.client.model;

import java.util.Date;

/**
 * Created by Denis on 10/20/2015.
 */
public class Notification extends BaseModel {

    public static final int SUBSCRIBE_REQUESTED = 1;
    public static final int SUBSCRIBE_ACCEPTED = 2;
    public static final int SUBSCRIBE_REJECTED = 3;
    public static final int SUBSCRIBTION_CANCELED = 4;

    private int code;
    private int fromPersonId;
    private int toPersonId;
    private Long created;

    public Notification(int id) {
        super(id);
    }

    public Notification(int code, int fromPersonId, int toPersonId) {
        this.created = new Date().getTime();
        this.code = code;
        this.fromPersonId = fromPersonId;
        this.toPersonId = toPersonId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFromPersonId() {
        return fromPersonId;
    }

    public void setFromPersonId(int fromPersonId) {
        this.fromPersonId = fromPersonId;
    }

    public int getToPersonId() {
        return toPersonId;
    }

    public void setToPersonId(int toPersonId) {
        this.toPersonId = toPersonId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
