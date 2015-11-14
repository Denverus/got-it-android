package org.coursera.capstone.gotit.client.model;

import org.apache.commons.lang3.text.StrBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 10/18/2015.
 */
public class CheckIn extends BaseModel {

    private Date created;
    private int personId;
    private List<Answer> answerList = Collections.EMPTY_LIST;

    public CheckIn(Date date) {
        if (date == null)
            created = new Date();
        else
            created = date;
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
