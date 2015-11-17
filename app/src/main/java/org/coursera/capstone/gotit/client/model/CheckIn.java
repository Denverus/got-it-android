package org.coursera.capstone.gotit.client.model;

import org.apache.commons.lang3.text.StrBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 10/18/2015.
 */
public class CheckIn extends BaseModel {

    private Long created;
    private int personId;
    private Answer[] answerList;

    public CheckIn(Long date) {
        if (date == null)
            created = new Date().getTime();
        else
            created = date;
    }

    public CheckIn(int personId, Answer[] answerList) {
        created = new Date().getTime();
        this.personId = personId;
        this.answerList = answerList;
    }
    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Answer[] getAnswers() {
        return answerList;
    }

    public void setAnswers(Answer[] questions) {
        this.answerList = questions;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        for (Answer answer : answerList) {
            if (answer.getAnswerType() == Answer.TYPE_INT) {
                sb.append("Sugar level [");
                sb.append(answer.getStringAnswer());
                sb.append("] ");
            } else if (answer.getAnswerType() == Answer.TYPE_STRING) {
                sb.append("Meal [");
                sb.append(answer.getStringAnswer());
                sb.append("] ");
            } else if (answer.getAnswerType() == Answer.TYPE_BOOLEAN) {
                sb.append("Insulin [");
                sb.append(answer.getStringAnswer());
                sb.append("] ");
            }
        }
        return sb.toString();
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                "created=" + created +
                ", personId=" + personId +
                ", answerList=" + answerList +
                '}';
    }
}
