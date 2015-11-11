package org.coursera.capstone.gotit.client.model;

/**
 * Created by Denis on 10/30/2015.
 */
public class FollowerSettings extends BaseModel {

    private String name;

    private int followerId;

    private boolean enableSharing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public boolean isEnableSharing() {
        return enableSharing;
    }

    public void setEnableSharing(boolean enableSharing) {
        this.enableSharing = enableSharing;
    }
}
