package com.jzson.gotit.client.model;

import java.util.List;

/**
 * Created by Denis on 10/30/2015.
 */
public class FollowerSettings extends BaseModel {

    private String name;

    private int followerId;

    private List<DataItemSettings> dataShareSettings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataItemSettings> getDataShareSettings() {
        return dataShareSettings;
    }

    public void setDataShareSettings(List<DataItemSettings> dataShareSettings) {
        this.dataShareSettings = dataShareSettings;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }
}
