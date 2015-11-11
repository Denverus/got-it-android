package org.coursera.capstone.gotit.client.adapter;

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

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.Utils;
import org.coursera.capstone.gotit.client.adapter.base.BaseListAdapter;
import org.coursera.capstone.gotit.client.model.Notification;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

import java.util.List;

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
                            deleteNotification(model.getId());
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
            Toast.makeText(context, org.coursera.capstone.gotit.client.R.string.can_remove_this_notification, Toast.LENGTH_LONG).show();
        }
    }

    private void deleteNotification(final int notificationId) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.deleteNotification(notificationId);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                NavUtils.showNotificationList(getContext());
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Can't delete notification", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected int getItemResourceId() {
        return org.coursera.capstone.gotit.client.R.layout.notification_item;
    }

    @Override
    protected NotificationViewHolder createViewHolder(View v) {
        return new NotificationViewHolder(v);
    }

    @Override
    protected List<Notification> onRefresh(ServiceApi svc) {
        return svc.getNotificationsByUserId(AppApplication.getContext().getCurrentUserId());
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, final int position) {
        final Notification notification = getModel(position);
        loadPersonAndInitViewHolder(holder, notification);
    }

    private void loadPersonAndInitViewHolder(final NotificationViewHolder holder, final Notification notification) {
        CallableTask.invoke(getContext(), new ServiceCall<Person>() {
            @Override
            public Person call(ServiceApi srv) throws Exception {
                return srv.getPersonById(notification.getFromPersonId());
            }
        }, new TaskCallback<Person>() {
            @Override
            public void success(Person person) {
                initViewHolder(holder, notification, person);
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Can't load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewHolder(final NotificationViewHolder holder, final Notification notification, Person person) {
        String summary = "";

        switch (notification.getCode()) {
            case Notification.SUBSCRIBE_REQUESTED: {
                summary = getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.notification_request, person.getName());
                holder.yesButton.setVisibility(View.VISIBLE);
                holder.noButton.setVisibility(View.VISIBLE);
                break;
            }
            case Notification.SUBSCRIBE_ACCEPTED: {
                summary = getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.notification_accept, person.getName());
                holder.yesButton.setVisibility(View.GONE);
                holder.noButton.setVisibility(View.GONE);
                break;
            }
            case Notification.SUBSCRIBE_REJECTED: {
                summary = getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.notification_reject, person.getName());
                holder.yesButton.setVisibility(View.GONE);
                holder.noButton.setVisibility(View.GONE);
                break;
            }
            case Notification.SUBSCRIBTION_CANCELED: {
                summary = getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.notification_canceled, person.getName());
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
                acceptSubscribeRequest(notification.getId());
            }
        });
        holder.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.noButton.setEnabled(false);
                rejectSubscribeRequest(notification.getId());
            }
        });    }

    private void rejectSubscribeRequest(final int notificationId) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.rejectSubscribeRequest(notificationId);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                NavUtils.showNotificationList(getContext());
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void acceptSubscribeRequest(final int notificationId) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.acceptSubscribeRequest(notificationId);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                NavUtils.showNotificationList(getContext());
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
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
            cv = (CardView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.cv);
            requestImage = (ImageView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.request_code_image);
            created = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.time);
            summary = (TextView)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.summary);
            yesButton = (Button)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.buttonYes);
            noButton = (Button)itemView.findViewById(org.coursera.capstone.gotit.client.R.id.buttonNo);
        }
    }
}
