package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.EditFeedbackFragment;
import com.jzson.gotit.client.fragments.TeenProfileFragment;
import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.Person;

import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.FeedbackViewHolder> implements View.OnClickListener {

    private List<Feedback> feedbackList;
    private RecyclerView mRecyclerView;

    public FeedbackListAdapter(List<Feedback> feedbackList){
        this.feedbackList = feedbackList;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.personal_feedback_item, viewGroup, false);
        v.setOnClickListener(this);
        FeedbackViewHolder pvh = new FeedbackViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder feedbackViewHolder, int i) {
        feedbackViewHolder.created.setText(feedbackList.get(i).getCreated().toString());
        feedbackViewHolder.summary.setText("Answers: "+feedbackList.get(i).getQuestions().size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        Feedback feedback = feedbackList.get(itemPosition);
        Context context = mRecyclerView.getContext();

        Intent intent = new Intent(context, MainActivity.class);
        AppApplication.getContext().setFeedback(feedback);
        AppApplication.getContext().setFragment(new EditFeedbackFragment());
        context.startActivity(intent);
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