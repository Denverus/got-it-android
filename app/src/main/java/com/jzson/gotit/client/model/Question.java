package com.jzson.gotit.client.model;

/**
 * Created by dtrotckii on 10/19/2015.
 */
public class Question extends BaseModel {

    private String question;
    private String shortName;
    private int answerType;

    public Question(String question, String shortName, int answerType) {
        this.question = question;
        this.shortName = shortName;
        this.answerType = answerType;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
