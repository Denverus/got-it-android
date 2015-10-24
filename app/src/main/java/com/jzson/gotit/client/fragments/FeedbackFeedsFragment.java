package com.jzson.gotit.client.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.adapter.FeedbackFeedsAdapter;
import com.jzson.gotit.client.fragments.base.ListFragment;
import com.jzson.gotit.client.provider.DataProvider;

/**
 * Created by Denis on 10/19/2015.
 */
public class FeedbackFeedsFragment extends ListFragment {

    @Override
    protected RecyclerView.Adapter createAdapter(Context context) {
        FeedbackFeedsAdapter feedbackFeedsAdapter = new FeedbackFeedsAdapter(context);
        feedbackFeedsAdapter.setData(DataProvider.getInstance().getUserFeeds(AppApplication.getContext().getUserId()));
        return feedbackFeedsAdapter;
    }
}
