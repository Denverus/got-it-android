package org.coursera.capstone.gotit.client.model;

/**
 * Created by dtrotckii on 10/19/2015.
 */
public class Answer extends BaseModel {

    public static final int TYPE_STRING = 1;
    public static final int TYPE_BOOLEAN = 2;
    public static final int TYPE_INT = 3;

    private int questionId;

    private String question;

    private int checkInId;

    private Boolean answerAsBoolean;
    private Integer answerAsInteger;
    private String answer;
    private int answerType;

    public Answer(int questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;

        answerType = TYPE_STRING;
    }

    public Answer(int questionId, Boolean answer) {
        this.questionId = questionId;
        this.answerAsBoolean = answer;
        answerType = TYPE_BOOLEAN;
    }

    public Answer(int questionId, Integer answer) {
        this.questionId = questionId;
        this.answerAsInteger = answer;
        answerType = TYPE_INT;
    }

    public Answer() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getCheckInId() {
        return checkInId;
    }
    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public Boolean getAnswerAsBoolean() {
        return answerAsBoolean;
    }

    public void setAnswerAsBoolean(Boolean answerAsBoolean) {
        this.answerAsBoolean = answerAsBoolean;
    }

    public Integer getAnswerAsInteger() {
        return answerAsInteger;
    }

    public void setAnswerAsInteger(Integer answerAsInteger) {
        this.answerAsInteger = answerAsInteger;
    }

    public String getStringAnswer() {
        if (answerType == TYPE_STRING) {
            return answer;
        } else if (answerType == TYPE_INT) {
            return answerAsInteger.toString();
        } else if (answerType == TYPE_BOOLEAN) {
            return answerAsBoolean ? "Yes" : "No";
        }
        return "";
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}