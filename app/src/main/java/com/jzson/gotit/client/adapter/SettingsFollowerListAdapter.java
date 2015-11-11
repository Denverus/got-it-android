package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.provider.ServiceApi;
import com.jzson.gotit.client.provider.ServiceCall;

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
        AppApplication.getContext().setFollowerId(model.getFollowerId());
        NavUtils.showSingleFollowerSettings(context, model);
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
    protected List<FollowerSettings> onRefresh(ServiceApi svc) {
        int userId = AppApplication.getContext().getCurrentUserId();
        return svc.loadFollowerSettings(userId);
    }

    @Override
    public void onBindViewHolder(SettingsFollowerViewHolder holder, int position) {
        final FollowerSettings model = getModel(position);
        holder.personName.setText(model.getName());
        holder.aSwitch.setChecked(model.isEnableSharing());

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int userId = AppApplication.getContext().getCurrentUserId();
                enableFollowerSharing(isChecked, userId, model);
            }
        });
    }

    private void enableFollowerSharing(final boolean isChecked, final int userId, final FollowerSettings model) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.enableFollowerSharing(userId, model.getFollowerId(), isChecked);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {

            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Can't change settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class SettingsFollowerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        Switch aSwitch;

        SettingsFollowerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.name);
            aSwitch = (Switch) itemView.findViewById(R.id.switch1);
        }
    }
}
