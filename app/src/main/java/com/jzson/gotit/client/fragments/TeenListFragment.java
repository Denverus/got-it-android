package com.jzson.gotit.client.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.PersonListAdapter;
import com.jzson.gotit.client.adapter.TeenListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;
import com.jzson.gotit.client.provider.DataProvider;

public class TeenListFragment extends ListFragment {

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new TeenListAdapter(context);
    }
}
