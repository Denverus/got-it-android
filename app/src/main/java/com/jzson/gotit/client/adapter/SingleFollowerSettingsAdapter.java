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
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.DataItemSettings;
import com.jzson.gotit.client.provider.ServiceApi;
import com.jzson.gotit.client.provider.ServiceCall;

import java.util.List;

/**
 * Created by Denis on 10/30/2015.
 */
public class SingleFollowerSettingsAdapter extends BaseListAdapter<DataItemSettings, SingleFollowerSettingsAdapter.SettingsFollowerViewHolder> {

    public SingleFollowerSettingsAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, DataItemSettings model) {
    }

    @Override
    protected int getItemResourceId() {
        return R.layout.single_follower_settings_item;
    }

    @Override
    protected SettingsFollowerViewHolder createViewHolder(View v) {
        return new SettingsFollowerViewHolder(v);
    }

    @Override
    protected List<DataItemSettings> onRefresh(ServiceApi svc) {
        int userId = AppApplication.getContext().getCurrentUserId();
        int followerId = AppApplication.getContext().getFollowerId();
        return svc.loadSingleFollowerSettings(userId, followerId);
    }

    @Override
    public void onBindViewHolder(SettingsFollowerViewHolder holder, int position) {
        final DataItemSettings model = getModel(position);
        holder.name.setText(model.getData());
        holder.aSwitch.setChecked(model.isEnableShare());

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int userId = AppApplication.getContext().getCurrentUserId();
                int followerId = AppApplication.getContext().getFollowerId();
                saveSingleFollowerSettings(userId, followerId, model.getQuestionId(), isChecked);
            }
        });
    }

    private void saveSingleFollowerSettings(final int userId, final int followerId, final int questionId, final boolean isChecked) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.saveSingleFollowerSettings(userId, followerId, questionId, isChecked);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Error during saving", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class SettingsFollowerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        Switch aSwitch;

        SettingsFollowerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            aSwitch = (Switch) itemView.findViewById(R.id.switch1);
        }
    }
}
