package com.jzson.gotit.client.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.FeedbackListAdapter;
import com.jzson.gotit.client.adapter.PersonListAdapter;
import com.jzson.gotit.client.provider.DataProvider;

public class TeenListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teen_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);

        mAdapter = new PersonListAdapter(DataProvider.getInstance().getTeens());
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }
}
