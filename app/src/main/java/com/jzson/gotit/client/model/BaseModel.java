package com.jzson.gotit.client.model;

/**
 * Created by Denis on 10/19/2015.
 */
public abstract class BaseModel {

    private int id;

    public BaseModel() {
    }

    public BaseModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
