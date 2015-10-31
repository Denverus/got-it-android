package com.jzson.gotit.client.model;

/**
 * Created by dtrotckii on 10/30/2015.
 */
public class ShareSettings extends BaseModel{

    private int userId;

    private int followerId;

    private int allowedQuestionId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getAllowedQuestionId() {
        return allowedQuestionId;
    }

    public void setAllowedQuestionId(int allowedQuestionId) {
        this.allowedQuestionId = allowedQuestionId;
    }
}
