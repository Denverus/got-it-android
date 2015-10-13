package com.jzson.gotit.client.model;

/**
 * Created by Denis on 10/11/2015.
 */
public class Person {
    private String name;
    private String age;
    private int photoId;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
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
}