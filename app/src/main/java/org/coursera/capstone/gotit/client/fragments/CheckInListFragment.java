package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.CheckInListAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

public class CheckInListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new CheckInListAdapter(context);
    }
}
