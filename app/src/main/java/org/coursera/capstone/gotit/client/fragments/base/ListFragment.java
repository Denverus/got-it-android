package org.coursera.capstone.gotit.client.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;

public abstract class ListFragment extends Fragment implements BaseListAdapter.DataUpdateListener {

    private RecyclerView mRecyclerView;
    private BaseListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView emptyView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    protected abstract BaseListAdapter createAdapter(Context context);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(org.coursera.capstone.gotit.client.R.layout.fragment_base_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.rv);

        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);

        mAdapter = createAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        emptyView = (TextView)rootView.findViewById(org.coursera.capstone.gotit.client.R.id.empty_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mAdapter.refreshData();
                        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        mAdapter.setDataUpdateListener(this);
        mAdapter.refreshData();

        return rootView;
    }

    @Override
    public void onDataUpdated() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter.getItemCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
