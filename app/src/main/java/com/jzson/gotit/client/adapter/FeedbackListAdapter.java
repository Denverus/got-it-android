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

    public FeedbackListAdapter(List<Feedback> persons){
        this.feedbackList = feedbackList;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback_item, viewGroup, false);
        v.setOnClickListener(this);
        FeedbackViewHolder pvh = new FeedbackViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder feedbackViewHolder, int i) {
        //feedbackViewHolder.personName.setText(feedbackList.get(i).getName());
        //feedbackViewHolder.personAge.setText(feedbackList.get(i).getAge());
        //feedbackViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
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
        AppApplication.getContext().setFragment(new TeenProfileFragment());
        context.startActivity(intent);
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        //TextEdit personName;
        //TextView personAge;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

}