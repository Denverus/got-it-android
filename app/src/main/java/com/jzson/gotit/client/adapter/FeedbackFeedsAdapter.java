package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.Feedback;

/**
 * Created by Denis on 10/19/2015.
 */
public class FeedbackFeedsAdapter extends BaseListAdapter<Feedback, FeedbackFeedsAdapter.FeedbackViewHolder> {

    @Override
    protected void onItemClick(Context context, Feedback model) {

    }

    @Override
    protected int getResource() {
        return R.layout.feedback_item;
    }

    @Override
    protected FeedbackViewHolder createViewHolder(View v) {
        return new FeedbackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {

    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView created;
        TextView summary;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            created = (TextView)itemView.findViewById(R.id.time);
            summary = (TextView)itemView.findViewById(R.id.summary);
        }
    }
}
