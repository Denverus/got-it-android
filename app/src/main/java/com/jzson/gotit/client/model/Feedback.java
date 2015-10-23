package com.jzson.gotit.client.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 10/18/2015.
 */
public class Feedback extends BaseModel {

    private Date created;
    private List<Question> questions;
    private int personId;

    public Feedback() {
        created = new Date();
    }

    public Feedback(int personId, List<Question> questions) {
        created = new Date();
        this.personId = personId;
        this.questions = questions;
    }
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getAnswer(int questionType) {
        for (Question question : questions) {
            if (question.getQuestionType() == questionType) {
                return question.getStringAnswer();
            }
        }
        return "";
    }

    public void setAnswer(int questionType, String value) {
        for (Question question : questions) {
            if (question.getQuestionType() == questionType) {
                question.setAnswer(value);
            }
        }
    }
    public String getSummary() {
        return "Questions "+questions.size();
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
