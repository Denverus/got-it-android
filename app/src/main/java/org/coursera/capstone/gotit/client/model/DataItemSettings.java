package org.coursera.capstone.gotit.client.model;

/**
 * Created by Denis on 10/30/2015.
 */
public class DataItemSettings extends BaseModel {

    private String data;

    private boolean enableShare;

    private int questionId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isEnableShare() {
        return enableShare;
    }

    public void setEnableShare(boolean enableShare) {
        this.enableShare = enableShare;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
