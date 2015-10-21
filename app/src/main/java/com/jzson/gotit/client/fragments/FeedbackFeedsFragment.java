package com.jzson.gotit.client.fragments;

import android.support.v7.widget.RecyclerView;

import com.jzson.gotit.client.adapter.FeedbackFeedsAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;
import com.jzson.gotit.client.provider.DataProvider;

/**
 * Created by Denis on 10/19/2015.
 */
public class FeedbackFeedsFragment extends ListFragment {

    @Override
    protected RecyclerView.Adapter createAdapter() {
        FeedbackFeedsAdapter feedbackFeedsAdapter = new FeedbackFeedsAdapter();
        feedbackFeedsAdapter.setData(DataProvider.getInstance().getUserFeeds());
        return feedbackFeedsAdapter;
    }
}
