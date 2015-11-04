package com.jzson.gotit.client.model;

/**
 * Created by Denis on 11/3/2015.
 */
public class GeneralSettings extends BaseModel {

    public static final String ENABLE_SHARING = "enable sharing";

    private String key;

    private String value;

    public int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public GeneralSettings(int userId, String key, String value) {
        this.key = key;
        this.value = value;
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
