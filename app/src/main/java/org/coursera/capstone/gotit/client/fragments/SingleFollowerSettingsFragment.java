package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.SingleFollowerSettingsAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

/**
 * Created by Denis on 10/30/2015.
 */
public class SingleFollowerSettingsFragment extends ListFragment {
    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new SingleFollowerSettingsAdapter(context);
    }
}
