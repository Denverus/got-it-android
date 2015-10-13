package com.jzson.gotit.client.provider;

import com.jzson.gotit.client.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class PersonProvider {
    private static final PersonProvider INSTANCE = new PersonProvider();

    private List<Person> persons;

    public PersonProvider() {
        initializeData();
    }

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
    }

    public static PersonProvider getInstance() {
        return INSTANCE;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
