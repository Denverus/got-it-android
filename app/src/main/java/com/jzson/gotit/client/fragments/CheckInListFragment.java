package com.jzson.gotit.client.fragments;

import android.content.Context;

import com.jzson.gotit.client.adapter.CheckInListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;

public class CheckInListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new CheckInListAdapter(context);
    }
}
