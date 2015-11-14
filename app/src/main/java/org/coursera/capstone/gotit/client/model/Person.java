package org.coursera.capstone.gotit.client.model;

import android.media.Image;

import java.util.Date;

/**
 * Created by Denis on 10/11/2015.
 */
public class Person extends BaseModel {

    private String name;
    private Image photo;
    private String login;
    private String password;
    private Long dateBirth;
    private boolean hasDiabetes;
    private String medicalRecordNumber;

    public Person(String name, String login, String password, Long dateBirth, boolean hasDiabetes, String medicalRecordNumber, Image photo) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.dateBirth = dateBirth;
        this.hasDiabetes = hasDiabetes;
        this.medicalRecordNumber = medicalRecordNumber;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Long dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isHasDiabetes() {
        return hasDiabetes;
    }

    public void setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
    }

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }
}