package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.FollowerListAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

/**
 * Created by Denis on 10/23/2015.
 */
public class FollowerListFragment extends ListFragment {
    @Override
    protected BaseListAdapter createAdapter(Context context) {
        FollowerListAdapter adapter = new FollowerListAdapter(context);
        return adapter;
    }
}
