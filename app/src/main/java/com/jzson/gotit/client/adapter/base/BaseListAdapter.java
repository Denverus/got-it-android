package com.jzson.gotit.client.adapter.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzson.gotit.client.model.BaseModel;

import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public abstract class BaseListAdapter<T extends BaseModel, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> implements View.OnClickListener {

    public interface DataUpdateListener {
        void onDataUpdated();
    }

    private List<T> modelList;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private DataUpdateListener dataUpdateListener;

    public void setData(List<T> modelList){
        this.modelList = modelList;
    }

    protected abstract void onItemClick(Context context, T model);

    protected abstract int getItemResourceId();

    protected abstract H createViewHolder(View v);

    protected abstract List<T> onRefresh();

    protected T getModel(int index) {
        return modelList.get(index);
    }

    public BaseListAdapter(Context context) {
        setContext(context);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public H onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(getItemResourceId(), viewGroup, false);
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

    public void refreshData() {
        List<T> data = onRefresh();
        setData(data);
        dataUpdateListener.onDataUpdated();
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        this.dataUpdateListener = dataUpdateListener;
    }
}