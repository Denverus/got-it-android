package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.CheckIn;
import com.jzson.gotit.client.provider.DataProvider;

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
        NavUtils.showAnswerListFragment(context, model.getId());
    }

    @Override
    protected int getItemResourceId() {
        return R.layout.personal_feedback_item;
    }

    @Override
    protected FeedbackViewHolder createViewHolder(View v) {
        return new FeedbackViewHolder(v);
    }

    @Override
    protected List<CheckIn> onRefresh() {
        return DataProvider.getInstance().getCheckInListByUserId(AppApplication.getContext().getUserId());
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
            cv = (CardView)itemView.findViewById(R.id.cv);
            created = (TextView)itemView.findViewById(R.id.time);
            summary = (TextView)itemView.findViewById(R.id.summary);
        }
    }
}