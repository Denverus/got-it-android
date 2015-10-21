package com.jzson.gotit.client.model;

/**
 * Created by Denis on 10/11/2015.
 */
public class Person extends BaseModel {

    public static final int TEEN = 1;
    public static final int FOLLOWER = 2;

    private String name;
    private String age;
    private int photoId;
    private int type;

    public Person(String name, String age, int type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public Person(String name, String age, int photoId, int type) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}