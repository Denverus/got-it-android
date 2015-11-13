package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.FeedListAdapter;
import org.coursera.capstone.gotit.client.adapter.FeedbackListAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

public class FeedbackListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        FeedbackListAdapter feedbackListAdapter = new FeedbackListAdapter(context);
        return feedbackListAdapter;
    }
}
