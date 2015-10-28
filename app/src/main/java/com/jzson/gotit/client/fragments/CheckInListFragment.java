package com.jzson.gotit.client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.CheckInListAdapter;
import com.jzson.gotit.client.provider.DataProvider;

public class CheckInListFragment extends Fragment {

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

        mAdapter = new CheckInListAdapter(DataProvider.getInstance().getFeedbackById(AppApplication.getContext().getUserId()));

        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }
}
