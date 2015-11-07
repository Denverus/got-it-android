package com.jzson.gotit.client.fragments;

import android.content.Context;

import com.jzson.gotit.client.adapter.FollowerListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;

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
