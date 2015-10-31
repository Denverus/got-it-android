package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.provider.DataProvider;

import java.util.List;

/**
 * Created by Denis on 10/30/2015.
 */
public class SettingsFollowerListAdapter extends BaseListAdapter<FollowerSettings, SettingsFollowerListAdapter.SettingsFollowerViewHolder> {

    public SettingsFollowerListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, FollowerSettings model) {

    }

    @Override
    protected int getItemResourceId() {
        return R.layout.settings_follower_list_item;
    }

    @Override
    protected SettingsFollowerViewHolder createViewHolder(View v) {
        return new SettingsFollowerViewHolder(v);
    }

    @Override
    protected List<FollowerSettings> onRefresh() {
        int userId = AppApplication.getContext().getUserId();
        return DataProvider.getInstance().loadFollowerSettings(userId);
    }

    @Override
    public void onBindViewHolder(SettingsFollowerViewHolder holder, int position) {
        FollowerSettings model = getModel(position);
        holder.personName.setText(model.getName());
    }

    public static class SettingsFollowerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;

        SettingsFollowerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
