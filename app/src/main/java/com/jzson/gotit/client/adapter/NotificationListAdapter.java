package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzson.gotit.client.R;
import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.Notification;

/**
 * Created by Denis on 10/20/2015.
 */
public class NotificationListAdapter extends BaseListAdapter<Notification, NotificationListAdapter.NotificationViewHolder> {

    @Override
    protected void onItemClick(Context context, Notification model) {

    }

    @Override
    protected int getResource() {
        return R.layout.notification_item;
    }

    @Override
    protected NotificationViewHolder createViewHolder(View v) {
        return new NotificationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = getModel(position);
        holder.summary.setText(notification.getSummary());
        holder.created.setText(Utils.dateToString(notification.getCreated()));
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView requestImage;
        TextView created;
        TextView summary;

        NotificationViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            requestImage = (ImageView)itemView.findViewById(R.id.request_code_image);
            created = (TextView)itemView.findViewById(R.id.time);
            summary = (TextView)itemView.findViewById(R.id.summary);
        }
    }
}
