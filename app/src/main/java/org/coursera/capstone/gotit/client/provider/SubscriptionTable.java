package org.coursera.capstone.gotit.client.provider;

import org.coursera.capstone.gotit.client.model.Subscription;

import java.util.List;

/**
 * Created by Denis on 10/20/2015.
 */
public class SubscriptionTable extends Table<Subscription> {

    public List<Subscription> getSubscriptionByPersonId(final int id) {
        return getListByCriteria(new BooleanCriteria<Subscription>() {
            @Override
            public boolean getCriteriaValue(Subscription value) {
                return value.getPersonId() == id;
            }
        });
    }
}
