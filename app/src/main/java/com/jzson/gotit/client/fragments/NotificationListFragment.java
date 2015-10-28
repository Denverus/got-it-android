package com.jzson.gotit.client.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.adapter.NotificationListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;
import com.jzson.gotit.client.provider.DataProvider;

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
