package com.jzson.gotit.client.provider;

import com.jzson.gotit.client.model.Feedback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 10/20/2015.
 */
public class FeedbackTable extends Table<Feedback> {

    public List<Feedback> getFeedbackListByUserId(final int id) {
        return getListByCriteria(new BooleanCriteria<Feedback>() {
            @Override
            public boolean getCriteriaValue(Feedback value) {
                return value.getPersonId() == id;
            }
        });
    }
}
