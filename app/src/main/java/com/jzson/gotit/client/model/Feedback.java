package com.jzson.gotit.client.model;

/**
 * Created by Denis on 10/18/2015.
 */
public class Feedback {

    private double sugarLevel;
    private String whatYouEat;
    private boolean administerInsulin;

    public Feedback() {
    }

    public Feedback(double sugarLevel, String whatYouEat, boolean administerInsulin) {
        this.sugarLevel = sugarLevel;
        this.whatYouEat = whatYouEat;
        this.administerInsulin = administerInsulin;
    }

    public double getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(double sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getWhatYouEat() {
        return whatYouEat;
    }

    public void setWhatYouEat(String whatYouEat) {
        this.whatYouEat = whatYouEat;
    }

    public boolean getAdministerInsulin() {
        return administerInsulin;
    }

    public void setAdministerInsulin(boolean administerInsulin) {
        this.administerInsulin = administerInsulin;
    }
}
