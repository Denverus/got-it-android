package com.jzson.gotit.client.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.Utils;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.provider.DataProvider;

/**
 * Created by Denis on 10/20/2015.
 */
public class NotificationListAdapter extends BaseListAdapter<Notification, NotificationListAdapter.NotificationViewHolder> {

    public NotificationListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onItemClick(Context context, final Notification model) {
        if (model.getCode() != Notification.SUBSCRIBE_REQUESTED) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete notification")
                    .setMessage("Are you sure you want to delete this notification?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DataProvider.getInstance().deleteNotification(model.getId());
                            NavUtils.showNotificationList(getContext());
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            Toast.makeText(context, R.string.can_remove_this_notification, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected int getItemResourceId() {
        return R.layout.notification_item;
    }

    @Override
    protected NotificationViewHolder createViewHolder(View v) {
        return new NotificationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, final int position) {
        final Notification notification = getModel(position);
        Person person = DataProvider.getInstance().getPersonById(notification.getFromPersonId());
        String summary = "";

        switch (notification.getCode()) {
            case Notification.SUBSCRIBE_REQUESTED: {
                summary = getContext().getResources().getString(R.string.notification_request, person.getName());
                holder.yesButton.setVisibility(View.VISIBLE);
                holder.noButton.setVisibility(View.VISIBLE);
                break;
            }
            case Notification.SUBSCRIBE_ACCEPTED: {
                summary = getContext().getResources().getString(R.string.notification_accept, person.getName());
                holder.yesButton.setVisibility(View.GONE);
                holder.noButton.setVisibility(View.GONE);
                break;
            }
            case Notification.SUBSCRIBE_REJECTED: {
                summary = getContext().getResources().getString(R.string.notification_reject, person.getName());
                holder.yesButton.setVisibility(View.GONE);
                holder.noButton.setVisibility(View.GONE);
                break;
            }
            case Notification.SUBSCRIBTION_CANCELED: {
                summary = getContext().getResources().getString(R.string.notification_canceled, person.getName());
                holder.yesButton.setVisibility(View.GONE);
                holder.noButton.setVisibility(View.GONE);
                break;
            }
        }

        holder.summary.setText(summary);
        holder.created.setText(Utils.dateToString(notification.getCreated()));
        holder.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.yesButton.setEnabled(false);
                DataProvider.getInstance().acceptSubscribeRequest(notification.getId());
                NavUtils.showNotificationList(getContext());
            }
        });
        holder.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.noButton.setEnabled(false);
                DataProvider.getInstance().rejectSubscribeRequest(notification.getId());
                NavUtils.showNotificationList(getContext());
            }
        });
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView requestImage;
        TextView created;
        TextView summary;
        Button yesButton;
        Button noButton;

        NotificationViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            requestImage = (ImageView)itemView.findViewById(R.id.request_code_image);
            created = (TextView)itemView.findViewById(R.id.time);
            summary = (TextView)itemView.findViewById(R.id.summary);
            yesButton = (Button)itemView.findViewById(R.id.buttonYes);
            noButton = (Button)itemView.findViewById(R.id.buttonNo);
        }
    }
}
