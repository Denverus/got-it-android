package com.jzson.gotit.client.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 10/18/2015.
 */
public class CheckIn extends BaseModel {

    private Date created;
    private List<Answer> answerList = Collections.EMPTY_LIST;
    private int personId;

    public CheckIn() {
        created = new Date();
    }

    public CheckIn(int personId, List<Answer> answerList) {
        created = new Date();
        this.personId = personId;
        this.answerList = answerList;
    }
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Answer> getAnswers() {
        return answerList;
    }

    public void setAnswers(List<Answer> questions) {
        this.answerList = questions;
    }

    public String getSummary() {
        return "Answers "+ answerList.size();
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
