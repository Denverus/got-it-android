package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;

import org.coursera.capstone.gotit.client.adapter.NotificationListAdapter;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.fragments.base.ListFragment;

/**
 * Created by Denis on 10/20/2015.
 */
public class NotificationListFragment extends ListFragment {
    @Override
    protected BaseListAdapter createAdapter(Context context) {
        NotificationListAdapter notificationListAdapter = new NotificationListAdapter(context);
        return notificationListAdapter;
    }
}
