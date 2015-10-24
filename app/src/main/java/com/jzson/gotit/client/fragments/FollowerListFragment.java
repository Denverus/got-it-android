package com.jzson.gotit.client.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.adapter.FollowerListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;
import com.jzson.gotit.client.provider.DataProvider;

/**
 * Created by Denis on 10/23/2015.
 */
public class FollowerListFragment extends ListFragment {
    @Override
    protected RecyclerView.Adapter createAdapter(Context context) {
        FollowerListAdapter adapter = new FollowerListAdapter(context);
        adapter.setData(DataProvider.getInstance().getFollowerList(AppApplication.getContext().getUserId()));
        return adapter;
    }
}
