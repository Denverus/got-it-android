package com.jzson.gotit.client.provider;

import com.jzson.gotit.client.model.CheckIn;

import java.util.List;

/**
 * Created by Denis on 10/20/2015.
 */
public class FeedbackTable extends Table<CheckIn> {

    public List<CheckIn> getFeedbackListByUserId(final int id) {
        return getListByCriteria(new BooleanCriteria<CheckIn>() {
            @Override
            public boolean getCriteriaValue(CheckIn value) {
                return value.getPersonId() == id;
            }
        });
    }
}
