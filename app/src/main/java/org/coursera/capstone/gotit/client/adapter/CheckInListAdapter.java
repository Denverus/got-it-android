package org.coursera.capstone.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.Utils;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.model.CheckIn;
import org.coursera.capstone.gotit.client.provider.ServiceApi;

import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class CheckInListAdapter extends BaseListAdapter<CheckIn, CheckInListAdapter.FeedbackViewHolder> {

    public CheckInListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, CheckIn model) {
        NavUtils.showAnswerListFragment(context, model.getAnswers());
    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.personal_feedback_item;
    }

    @Override
    protected FeedbackViewHolder createViewHolder(View v) {
        return new FeedbackViewHolder(v);
    }

    @Override
    protected List<CheckIn> onRefresh(ServiceApi svc) {
        return svc.getCheckInListByUserId(AppApplication.getContext().getCurrentUserId());
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder feedbackViewHolder, int i) {
        feedbackViewHolder.created.setText(Utils.dateToString(getModel(i).getCreated()));
        feedbackViewHolder.summary.setText("Answers: " + getModel(i).getAnswers().size());
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView created;
        TextView summary;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            created = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.time);
            summary = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.summary);
        }
    }
}