package com.jzson.gotit.client.adapter.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.EditFeedbackFragment;
import com.jzson.gotit.client.model.BaseModel;
import com.jzson.gotit.client.model.Feedback;

import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public abstract class BaseListAdapter<T extends BaseModel, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> implements View.OnClickListener {

    private List<T> modelList;
    private RecyclerView mRecyclerView;

    public void setData(List<T> modelList){
        this.modelList = modelList;
    }

    protected abstract void onItemClick(Context context, T model);

    protected abstract int getResource();

    protected abstract H createViewHolder(View v);

    @Override
    public H onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(getResource(), viewGroup, false);
        v.setOnClickListener(this);
        return createViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildAdapterPosition(view);
        T model = modelList.get(itemPosition);
        Context context = mRecyclerView.getContext();

        onItemClick(context, model);
    }
}