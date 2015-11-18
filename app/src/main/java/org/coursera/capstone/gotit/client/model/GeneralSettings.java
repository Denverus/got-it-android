package org.coursera.capstone.gotit.client.model;

/**
 * Created by Denis on 11/3/2015.
 */
public class GeneralSettings extends BaseModel {

    public static final String ENABLE_SHARING = "enable sharing";
    public static final String ALERT_1 = "alert_1";
    public static final String ALERT_2 = "alert_2";
    public static final String ALERT_3 = "alert_3";

    private String key;

    private String value;

    public int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public GeneralSettings() {
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

    @Override
    public String toString() {
        return "GeneralSettings{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", userId=" + userId +
                '}';
    }
}
