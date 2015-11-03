package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.DataItemSettings;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.provider.DataProvider;

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
    protected List<DataItemSettings> onRefresh() {
        int userId = AppApplication.getContext().getUserId();
        int followerId = AppApplication.getContext().getFollowerId();
        return DataProvider.getInstance().loadSingleFollowerSettings(userId, followerId);
    }

    @Override
    public void onBindViewHolder(SettingsFollowerViewHolder holder, int position) {
        final DataItemSettings model = getModel(position);
        holder.name.setText(model.getData());
        holder.aSwitch.setChecked(model.isEnableShare());

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int userId = AppApplication.getContext().getUserId();
                int followerId = AppApplication.getContext().getFollowerId();
                DataProvider.getInstance().saveSingleFollowerSettings(userId, followerId, model.getQuestionId(), isChecked);
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
