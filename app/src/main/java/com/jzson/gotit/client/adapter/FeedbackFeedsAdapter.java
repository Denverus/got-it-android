package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.fragments.EditFeedbackFragment;
import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.UserFeed;

/**
 * Created by Denis on 10/19/2015.
 */
public class FeedbackFeedsAdapter extends BaseListAdapter<UserFeed, FeedbackFeedsAdapter.FeedbackViewHolder> {

    public FeedbackFeedsAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, UserFeed model) {
        Intent intent = new Intent(context, MainActivity.class);
        AppApplication.getContext().setFeedback(model.getFeedback());
        AppApplication.getContext().setFragment(new EditFeedbackFragment());
        context.startActivity(intent);
    }

    @Override
    protected int getItemResourceId() {
        return R.layout.feedback_item;
    }

    @Override
    protected FeedbackViewHolder createViewHolder(View v) {
        return new FeedbackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {
        holder.personName.setText(getModel(position).getPerson().getName());
        holder.created.setText(getModel(position).getFeedback().getCreated().toString());
        holder.summary.setText(getModel(position).getFeedback().getSummary());
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView created;
        TextView summary;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            created = (TextView)itemView.findViewById(R.id.time);
            summary = (TextView)itemView.findViewById(R.id.summary);
        }
    }
}
