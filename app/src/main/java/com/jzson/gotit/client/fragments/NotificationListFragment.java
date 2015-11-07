package com.jzson.gotit.client.fragments;

import android.content.Context;

import com.jzson.gotit.client.adapter.NotificationListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;

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
