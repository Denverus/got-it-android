package org.coursera.capstone.gotit.client.provider;

import org.coursera.capstone.gotit.client.model.Person;

import java.util.List;

/**
 * Created by Denis on 10/20/2015.
 */
public class PersonTable extends Table<Person> {

    public List<Person> getTeens() {
        return getListByCriteria(new BooleanCriteria<Person>() {
            @Override
            public boolean getCriteriaValue(Person person) {
                return person.isHasDiabetes();
            }
        });
    }
}
