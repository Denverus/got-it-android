package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.AnswerListAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

/**
 * Created by Denis on 10/19/2015.
 */
public class AnswerListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new AnswerListAdapter(context);
    }
}
