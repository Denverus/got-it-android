package com.jzson.gotit.client.model;

/**
 * Created by dtrotckii on 10/19/2015.
 */
public class Question extends BaseModel {

    private static final int TYPE_STRING = 1;
    private static final int TYPE_BOOLEAN = 2;
    private static final int TYPE_DOUBLE = 3;

    public static final int QUESTION_SUGAR_LEVEL = 1;
    public static final int QUESTION_MEAL = 2;
    public static final int QUESTION_INSULIN = 3;

    private String question;
    private String answer;
    private Boolean answerAsBoolean;
    private Double answerAsDouble;
    private int answerType;
    private int questionType;

    public Question(String question, String answer, int questionType) {
        this.question = question;
        this.answer = answer;

        answerType = TYPE_STRING;
        this.questionType = questionType;
    }

    public Question(String question, Boolean answer, int questionType) {
        this.question = question;
        this.answerAsBoolean = answer;
        answerType = TYPE_BOOLEAN;
        this.questionType = questionType;
    }

    public Question(String question, Double answer, int questionType) {
        this.question = question;
        this.answerAsDouble = answer;
        answerType = TYPE_DOUBLE;
        this.questionType = questionType;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public Double getAnswerAsDouble() {
        return answerAsDouble;
    }

    public void setAnswerAsDouble(Double answerAsDouble) {
        this.answerAsDouble = answerAsDouble;
    }

    public String getStringAnswer() {
        if (answerType == TYPE_STRING) {
            return answer;
        } else if (answerType == TYPE_DOUBLE) {
            return answerAsDouble.toString();
        } else if (answerType == TYPE_BOOLEAN) {
            return answerAsBoolean.toString();
        }
        return "";
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }
}
