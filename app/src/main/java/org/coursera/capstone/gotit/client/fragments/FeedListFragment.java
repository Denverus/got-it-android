package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.FeedListAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

/**
 * Created by Denis on 10/19/2015.
 */
public class FeedListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        FeedListAdapter feedListAdapter = new FeedListAdapter(context);
        return feedListAdapter;
    }
}
