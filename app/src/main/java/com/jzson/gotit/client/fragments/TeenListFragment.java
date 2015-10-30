package com.jzson.gotit.client.fragments;

import android.content.Context;

import com.jzson.gotit.client.adapter.TeenListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;

public class TeenListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new TeenListAdapter(context);
    }
}
