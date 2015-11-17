package org.coursera.capstone.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.model.Answer;
import org.coursera.capstone.gotit.client.provider.ServiceApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Denis on 10/19/2015.
 */
public class AnswerListAdapter extends BaseListAdapter<Answer, AnswerListAdapter.AnswerViewHolder> {

    public AnswerListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, Answer model) {
    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.item_answer;
    }

    @Override
    protected AnswerViewHolder createViewHolder(View v) {
        return new AnswerViewHolder(v);
    }

    @Override
    protected List<Answer> onRefresh(ServiceApi svc) {
        return Arrays.asList(AppApplication.getContext().getAnswerList());
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.question.setText(getModel(position).getQuestion());
        holder.answer.setText(getModel(position).getStringAnswer());
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView question;
        TextView answer;

        AnswerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            question = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.question);
            answer = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.answer);
        }
    }
}
