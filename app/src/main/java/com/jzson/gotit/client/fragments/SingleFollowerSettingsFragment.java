package com.jzson.gotit.client.fragments;

import android.content.Context;

import com.jzson.gotit.client.adapter.SettingsFollowerListAdapter;
import com.jzson.gotit.client.adapter.SingleFollowerSettingsAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;

/**
 * Created by Denis on 10/30/2015.
 */
public class SingleFollowerSettingsFragment extends ListFragment {
    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new SingleFollowerSettingsAdapter(context);
    }
}
