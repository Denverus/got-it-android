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
import org.coursera.capstone.gotit.client.model.UserFeed;
import org.coursera.capstone.gotit.client.provider.ServiceApi;

import java.util.List;

/**
 * Created by Denis on 10/19/2015.
 */
public class FeedListAdapter extends BaseListAdapter<UserFeed, FeedListAdapter.FeedbackViewHolder> {

    public FeedListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, UserFeed model) {
        NavUtils.showAnswerListFragment(context, model.getCheckIn().getAnswers());
    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.feedback_item;
    }

    @Override
    protected FeedbackViewHolder createViewHolder(View v) {
        return new FeedbackViewHolder(v);
    }

    @Override
    protected List<UserFeed> onRefresh(ServiceApi svc) {
        return svc.getUserFeeds(AppApplication.getContext().getCurrentUserId());
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {
        holder.personName.setText(getModel(position).getPerson().getName());
        holder.created.setText(Utils.dateToString(getModel(position).getCheckIn().getCreated()));
        holder.summary.setText(getModel(position).getCheckIn().getSummary());
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView created;
        TextView summary;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            personName = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.person_name);
            created = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.time);
            summary = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.summary);
        }
    }
}
