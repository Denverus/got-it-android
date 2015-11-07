package com.jzson.gotit.client.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.model.BaseModel;
import com.jzson.gotit.client.provider.ServiceApi;
import com.jzson.gotit.client.provider.ServiceCall;

import java.util.List;
import java.util.concurrent.Callable;

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

    protected abstract List<T> onRefresh(ServiceApi svc );

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
        CallableTask.invoke(getContext(), new ServiceCall<List<T>>() {
            @Override
            public List<T> call(ServiceApi srv) throws Exception {
                return onRefresh(srv);
            }
        }, new TaskCallback<List<T>>() {
            @Override
            public void success(List<T> data) {
                setData(data);
                dataUpdateListener.onDataUpdated();
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        this.dataUpdateListener = dataUpdateListener;
    }
}