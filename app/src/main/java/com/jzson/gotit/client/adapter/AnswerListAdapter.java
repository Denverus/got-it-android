package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.Answer;
import com.jzson.gotit.client.provider.DataProvider;

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
        return R.layout.item_answer;
    }

    @Override
    protected AnswerViewHolder createViewHolder(View v) {
        return new AnswerViewHolder(v);
    }

    @Override
    protected List<Answer> onRefresh() {
        return DataProvider.getInstance().getUserAnswerList(AppApplication.getContext().getCheckIn());
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
            cv = (CardView)itemView.findViewById(R.id.cv);
            question = (TextView)itemView.findViewById(R.id.question);
            answer = (TextView)itemView.findViewById(R.id.answer);
        }
    }
}
